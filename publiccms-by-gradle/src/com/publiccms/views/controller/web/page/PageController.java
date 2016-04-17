package com.publiccms.views.controller.web.page;

import static com.publiccms.common.tools.ExtendUtils.getExtendString;
import static com.publiccms.logic.component.TemplateComponent.INCLUDE_DIRECTORY;
import static com.sanluan.common.tools.RequestUtils.getIpAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.publiccms.common.base.AbstractController;
import com.publiccms.entities.cms.CmsPageData;
import com.publiccms.entities.log.LogOperate;
import com.publiccms.entities.sys.SysSite;
import com.publiccms.logic.component.MetadataComponent;
import com.publiccms.logic.component.StatisticsComponent;
import com.publiccms.logic.service.cms.CmsPageDataAttributeService;
import com.publiccms.logic.service.cms.CmsPageDataService;
import com.publiccms.logic.service.log.LogLoginService;
import com.publiccms.views.pojo.CmsPageDataParamters;
import com.publiccms.views.pojo.CmsPageDataStatistics;
import com.publiccms.views.pojo.CmsPageMetadata;

@Controller
@RequestMapping("page")
public class PageController extends AbstractController {
    @Autowired
    private CmsPageDataService service;
    @Autowired
    private StatisticsComponent statisticsComponent;
    @Autowired
    private CmsPageDataAttributeService attributeService;
    @Autowired
    private MetadataComponent metadataComponent;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(CmsPageData entity, String returnUrl, @ModelAttribute CmsPageDataParamters pageDataParamters,
            HttpServletRequest request, HttpSession session, ModelMap model) {
        if (notEmpty(entity) && notEmpty(entity.getPath())) {
            SysSite site = getSite(request);
            entity.setPath(entity.getPath().replace("//", SEPARATOR));
            String placePath = INCLUDE_DIRECTORY + entity.getPath();
            String filePath = siteComponent.getWebTemplateFilePath(site, placePath);
            CmsPageMetadata metadata = metadataComponent.getTemplateMetadata(filePath);
            if (empty(getUserFromSession(session))
                    || virifyCustom("contribute", empty(metadata) || !metadata.isAllowContribute() || !(metadata.getSize() > 0),
                            model)) {
                return REDIRECT + returnUrl;
            }
            if (notEmpty(entity.getId())) {
                CmsPageData oldEntity = service.getEntity(entity.getId());
                if (empty(oldEntity) || virifyNotEquals("siteId", site.getId(), oldEntity.getSiteId(), model)
                        || virifyNotEquals("siteId", getUserFromSession(session).getId(), oldEntity.getUserId(), model)) {
                    return REDIRECT + returnUrl;
                }
                entity = service.update(entity.getId(), entity, new String[] { "id", "siteId", "type", "path", "createDate",
                        "disabled" });
                logOperateService.save(new LogOperate(site.getId(), getUserFromSession(session).getId(),
                        LogLoginService.CHANNEL_WEB, "update.pagedata", getIpAddress(request), getDate(), entity.getPath()));
            } else {
                entity.setSiteId(site.getId());
                service.save(entity);
                logOperateService.save(new LogOperate(site.getId(), getUserFromSession(session).getId(),
                        LogLoginService.CHANNEL_WEB, "save.pagedata", getIpAddress(request), getDate(), entity.getPath()));
            }
            String extentString = getExtendString(metadataComponent.getExtendDataMap(filePath,
                    pageDataParamters.getExtendDataList()));
            attributeService.updateAttribute(entity.getId(), extentString);
        }
        return REDIRECT + returnUrl;
    }

    /**
     * 推荐位链接重定向并计数
     * 
     * @param id
     * @return
     */
    @RequestMapping("redirect")
    public void clicks(Integer id, HttpServletRequest request, HttpServletResponse response) {
        SysSite site = getSite(request);
        CmsPageDataStatistics pageDataStatistics = statisticsComponent.placeClicks(id);
        if (notEmpty(pageDataStatistics.getEntity()) && site.getId() == pageDataStatistics.getEntity().getSiteId()) {
            redirectPermanently(response, pageDataStatistics.getEntity().getUrl());
        } else {
            redirectPermanently(response, site.getSitePath());
        }
    }
}
