package com.publiccms.views.controller.web.page;

import static com.publiccms.common.tools.ExtendUtils.getExtendString;
import static com.sanluan.common.tools.RequestUtils.getIpAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.publiccms.common.base.AbstractController;
import com.publiccms.entities.cms.CmsPageData;
import com.publiccms.entities.log.LogOperate;
import com.publiccms.entities.sys.SysSite;
import com.publiccms.logic.component.MetadataComponent;
import com.publiccms.logic.service.cms.CmsPageDataAttributeService;
import com.publiccms.logic.service.cms.CmsPageDataService;
import com.publiccms.logic.service.log.LogLoginService;
import com.publiccms.views.pojo.CmsPageDataParamters;
import com.publiccms.views.pojo.CmsPageMetadata;

@Controller
@RequestMapping("page")
public class PageController extends AbstractController {
    @Autowired
    private CmsPageDataService service;
    @Autowired
    private CmsPageDataAttributeService attributeService;
    @Autowired
    private MetadataComponent metadataComponent;

    @RequestMapping(value = SAVE, method = RequestMethod.POST)
    @ResponseBody
    public MappingJacksonValue save(CmsPageData entity, @ModelAttribute CmsPageDataParamters pageDataParamters,
            HttpServletRequest request, HttpSession session, ModelMap model) {
        SysSite site = getSite(request);
        if (notEmpty(entity) && notEmpty(entity.getPath())) {
            entity.setPath(entity.getPath().replace("//", SEPARATOR));
        }
        CmsPageMetadata metadata = metadataComponent.getTemplateMetadata(siteComponent.getTemplateFilePath(site,
                entity.getType(), entity.getPath()));
        if (virifyCustom("contribute", empty(metadata) || !metadata.isAllowContribute() || !(metadata.getSize() > 0), model)) {
            return new MappingJacksonValue(model);
        }
        if (notEmpty(entity.getId())) {
            CmsPageData oldEntity = service.getEntity(entity.getId());
            if (empty(oldEntity) || virifyNotEquals("siteId", site.getId(), oldEntity.getSiteId(), model)) {
                return new MappingJacksonValue(model);
            }
            service.update(entity.getId(), entity, new String[] { ID, "siteId", "type", "path", "createDate", "disabled" });
            logOperateService.save(new LogOperate(site.getId(), getAdminFromSession(session).getId(),
                    LogLoginService.CHANNEL_WEB, "update.pagedata", getIpAddress(request), getDate(), entity.getPath()));
        } else {
            entity.setSiteId(site.getId());
            service.save(entity);
            logOperateService.save(new LogOperate(site.getId(), getAdminFromSession(session).getId(),
                    LogLoginService.CHANNEL_WEB, "save.pagedata", getIpAddress(request), getDate(), entity.getPath()));
        }
        String filePath = siteComponent.getTemplateFilePath(site, entity.getType(), entity.getPath());
        String extentString = getExtendString(metadataComponent.getExtendDataMap(filePath, pageDataParamters.getExtendDataList()));
        attributeService.updateAttribute(entity.getId(), extentString);
        return new MappingJacksonValue(model);
    }
}
