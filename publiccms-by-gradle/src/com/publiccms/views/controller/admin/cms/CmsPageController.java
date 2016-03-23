package com.publiccms.views.controller.admin.cms;

import static com.publiccms.common.tools.ExtendUtils.getExtendString;
import static com.publiccms.logic.component.SiteComponent.getFullFileName;
import static com.publiccms.logic.service.cms.CmsPageDataService.ITEM_TYPE_CUSTOM;
import static com.publiccms.logic.service.cms.CmsPageDataService.STATUS_NORMAL;
import static com.sanluan.common.tools.RequestUtils.getIpAddress;
import static org.apache.commons.lang3.StringUtils.join;

import javax.servlet.http.HttpServletRequest;
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
import com.publiccms.logic.component.TemplateCacheComponent;
import com.publiccms.logic.service.cms.CmsPageDataAttributeService;
import com.publiccms.logic.service.cms.CmsPageDataService;
import com.publiccms.logic.service.log.LogLoginService;
import com.publiccms.views.pojo.CmsPageDataParamters;
import com.publiccms.views.pojo.CmsPageMetadata;

/**
 * 
 * CmsPageController
 *
 */
@Controller
@RequestMapping("cmsPage")
public class CmsPageController extends AbstractController {
    @Autowired
    private CmsPageDataService service;
    @Autowired
    private CmsPageDataAttributeService attributeService;
    @Autowired
    private MetadataComponent metadataComponent;
    @Autowired
    private TemplateCacheComponent templateCacheComponent;

    /**
     * @param entity
     * @param pageDataParamters
     * @param request
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(CmsPageData entity, @ModelAttribute CmsPageDataParamters pageDataParamters, HttpServletRequest request,
            HttpSession session, ModelMap model) {
        SysSite site = getSite(request);
        int userId = getAdminFromSession(session).getId();
        if (notEmpty(entity.getPath())) {
            entity.setPath(entity.getPath().replace("//", SEPARATOR));
        }
        if (empty(entity.getItemType()) || empty(entity.getItemId())) {
            entity.setItemType(ITEM_TYPE_CUSTOM);
            entity.setItemId(null);
        }
        if (notEmpty(entity.getId())) {
            CmsPageData oldEntity = service.getEntity(entity.getId());
            if (empty(oldEntity) || virifyNotEquals("siteId", site.getId(), oldEntity.getSiteId(), model)) {
                return TEMPLATE_ERROR;
            }
            entity = service.update(entity.getId(), entity, new String[] { "id", "siteId", "status", "userId", "type", "clicks",
                    "path", "createDate", "disabled" });
            if (notEmpty(entity.getId())) {
                logOperateService.save(new LogOperate(site.getId(), userId, LogLoginService.CHANNEL_WEB_MANAGER,
                        "update.pagedata", getIpAddress(request), getDate(), entity.getPath()));
            }
        } else {
            entity.setUserId(userId);
            entity.setSiteId(site.getId());
            entity.setStatus(STATUS_NORMAL);
            service.save(entity);
            logOperateService.save(new LogOperate(site.getId(), userId, LogLoginService.CHANNEL_WEB_MANAGER, "save.pagedata",
                    getIpAddress(request), getDate(), entity.getPath()));
        }
        String filePath = siteComponent.getTemplateFilePath(site, entity.getType(), entity.getPath());
        String extentString = getExtendString(metadataComponent.getExtendDataMap(filePath, pageDataParamters.getExtendDataList()));
        attributeService.updateAttribute(entity.getId(), extentString);
        return TEMPLATE_DONE;
    }

    /**
     * @param ids
     * @param request
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("refresh")
    public String refresh(Integer[] ids, HttpServletRequest request, HttpSession session, ModelMap model) {
        if (notEmpty(ids)) {
            SysSite site = getSite(request);
            service.refresh(site.getId(), ids);
            logOperateService.save(new LogOperate(site.getId(), getAdminFromSession(session).getId(),
                    LogLoginService.CHANNEL_WEB_MANAGER, "refresh.pagedata", getIpAddress(request), getDate(), join(ids, ',')));
        }
        return TEMPLATE_DONE;
    }

    /**
     * @param ids
     * @param request
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("check")
    public String check(Integer[] ids, HttpServletRequest request, HttpSession session, ModelMap model) {
        if (notEmpty(ids)) {
            SysSite site = getSite(request);
            service.check(site.getId(), ids);
            logOperateService.save(new LogOperate(site.getId(), getAdminFromSession(session).getId(),
                    LogLoginService.CHANNEL_WEB_MANAGER, "check.content", getIpAddress(request), getDate(), join(ids, ',')));
        }
        return TEMPLATE_DONE;
    }

    /**
     * @param path
     * @param type
     * @param pageDataParamters
     * @param content
     * @param request
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("saveMetaData")
    public String saveMetadata(String path, String type, @ModelAttribute CmsPageDataParamters pageDataParamters, String content,
            HttpServletRequest request, HttpSession session, ModelMap model) {
        if (notEmpty(path)) {
            SysSite site = getSite(request);
            String filePath = siteComponent.getTemplateFilePath(site, type, path);
            CmsPageMetadata oldmetadata = metadataComponent.getTemplateMetadata(siteComponent.getStaticTemplateFilePath(site,
                    path));
            oldmetadata.setExtendDataList(pageDataParamters.getExtendDataList());
            metadataComponent.updateMetadata(filePath, oldmetadata);
            logOperateService.save(new LogOperate(site.getId(), getAdminFromSession(session).getId(),
                    LogLoginService.CHANNEL_WEB_MANAGER, "update.template.data", getIpAddress(request), getDate(), path));
        }
        return TEMPLATE_DONE;
    }

    /**
     * @param path
     * @param type
     * @param request
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("clear")
    public String clear(String path, String type, HttpServletRequest request, HttpSession session, ModelMap model) {
        if (notEmpty(path)) {
            SysSite site = getSite(request);
            service.delete(site.getId(), path, type);
            logOperateService.save(new LogOperate(site.getId(), getAdminFromSession(session).getId(),
                    LogLoginService.CHANNEL_WEB_MANAGER, "clear.pagedata", getIpAddress(request), getDate(), path));
        }
        return TEMPLATE_DONE;
    }

    /**
     * @param path
     * @param type
     * @param request
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("clearPageCache")
    public String clearPageCache(String path, HttpServletRequest request, HttpSession session, ModelMap model) {
        if (notEmpty(path)) {
            SysSite site = getSite(request);
            templateCacheComponent.deleteCachedFile(getFullFileName(site, path));
            logOperateService.save(new LogOperate(site.getId(), getAdminFromSession(session).getId(),
                    LogLoginService.CHANNEL_WEB_MANAGER, "clear.pageCache", getIpAddress(request), getDate(), path));
        }
        return TEMPLATE_DONE;
    }

    /**
     * @param ids
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("delete")
    public String delete(Integer[] ids, HttpServletRequest request, HttpSession session) {
        if (notEmpty(ids)) {
            SysSite site = getSite(request);
            service.delete(site.getId(), ids);
            logOperateService.save(new LogOperate(site.getId(), getAdminFromSession(session).getId(),
                    LogLoginService.CHANNEL_WEB_MANAGER, "delete.pagedata", getIpAddress(request), getDate(), join(ids, ',')));
        }
        return TEMPLATE_DONE;
    }
}
