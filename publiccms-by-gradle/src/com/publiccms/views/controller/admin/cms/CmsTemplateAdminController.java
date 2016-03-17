package com.publiccms.views.controller.admin.cms;

import static com.publiccms.logic.component.SiteComponent.getFullFileName;
import static com.publiccms.logic.component.TemplateComponent.INCLUDE_DIRECTORY;
import static com.publiccms.logic.service.cms.CmsPageDataService.PAGE_TYPE_STATIC;
import static com.publiccms.logic.service.cms.CmsPageDataService.PAGE_TYPE_DYNAMIC;
import static com.sanluan.common.tools.RequestUtils.getIpAddress;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.publiccms.common.base.AbstractController;
import com.publiccms.entities.log.LogOperate;
import com.publiccms.entities.sys.SysSite;
import com.publiccms.logic.component.FileComponent;
import com.publiccms.logic.component.FileComponent.FileInfo;
import com.publiccms.logic.component.MetadataComponent;
import com.publiccms.logic.component.TemplateCacheComponent;
import com.publiccms.logic.component.TemplateComponent;
import com.publiccms.logic.service.log.LogLoginService;
import com.publiccms.logic.service.sys.SysDeptPageService;
import com.publiccms.views.pojo.CmsPageMetadata;

import freemarker.template.TemplateException;

/**
 * 
 * CmsTemplateAdminController
 *
 */
@Controller
@RequestMapping("cmsTemplate")
public class CmsTemplateAdminController extends AbstractController {
    @Autowired
    private TemplateComponent templateComponent;
    @Autowired
    private TemplateCacheComponent templateCacheComponent;
    @Autowired
    private MetadataComponent metadataComponent;
    @Autowired
    private FileComponent fileComponent;
    @Autowired
    private SysDeptPageService sysDeptPageService;

    /**
     * @param path
     * @param type
     * @param content
     * @param request
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(SAVE)
    public String save(String path, String type, String content, HttpServletRequest request, HttpSession session, ModelMap model) {
        SysSite site = getSite(request);
        if (notEmpty(path)) {
            try {
                String filePath = siteComponent.getTemplateFilePath(site, type, path);
                File templateFile = new File(filePath);
                if (notEmpty(templateFile)) {
                    fileComponent.updateFile(templateFile, content);
                    if (PAGE_TYPE_DYNAMIC.equals(type)) {
                        templateCacheComponent.deleteCachedFile(filePath);
                    }
                    logOperateService.save(new LogOperate(site.getId(), getAdminFromSession(session).getId(),
                            LogLoginService.CHANNEL_WEB_MANAGER, "update." + type + ".template", getIpAddress(request),
                            getDate(), path));
                } else {
                    fileComponent.createFile(templateFile, content);
                    logOperateService.save(new LogOperate(site.getId(), getAdminFromSession(session).getId(),
                            LogLoginService.CHANNEL_WEB_MANAGER, "save." + type + ".template", getIpAddress(request), getDate(),
                            path));
                }
                templateComponent.clear();
                if (PAGE_TYPE_STATIC.equals(type)) {
                    publish(site, path);
                }
            } catch (IOException | TemplateException e) {
                virifyCustom("template.save", true, model);
                log.error(e.getMessage());
                return TEMPLATE_ERROR;
            }
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
    @RequestMapping(DELETE)
    public String delete(String path, String type, HttpServletRequest request, HttpSession session, ModelMap model) {
        if (notEmpty(path)) {
            SysSite site = getSite(request);
            String filePath = siteComponent.getTemplateFilePath(site, type, path);
            if (virifyCustom("notExist.template", !fileComponent.deleteFile(filePath), model)) {
                return TEMPLATE_ERROR;
            }
            metadataComponent.deleteMetadata(filePath);
            sysDeptPageService.delete(null, type, path);
            templateComponent.clear();
            logOperateService.save(new LogOperate(site.getId(), getAdminFromSession(session).getId(),
                    LogLoginService.CHANNEL_WEB_MANAGER, "delete.template", getIpAddress(request), getDate(), path));
        }
        return TEMPLATE_DONE;
    }

    /**
     * @param path
     * @param metadata
     * @param content
     * @param request
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("saveMetadata")
    public String saveMetadata(String path, @ModelAttribute CmsPageMetadata metadata, String content, HttpServletRequest request,
            HttpSession session, ModelMap model) {
        if (notEmpty(path)) {
            SysSite site = getSite(request);
            String filePath = siteComponent.getTemplateFilePath(site, metadata.getType(), path);
            try {
                File templateFile = new File(filePath);
                if (empty(templateFile)) {
                    fileComponent.createFile(templateFile, content);
                }
                CmsPageMetadata oldmetadata = metadataComponent.getTemplateMetadata(filePath);
                if (notEmpty(oldmetadata)) {
                    metadata.setExtendDataList(oldmetadata.getExtendDataList());
                }
                metadataComponent.updateMetadata(filePath, metadata);
                if (PAGE_TYPE_STATIC.equals(metadata.getType())) {
                    publish(site, path);
                }
                logOperateService.save(new LogOperate(site.getId(), getAdminFromSession(session).getId(),
                        LogLoginService.CHANNEL_WEB_MANAGER, "update.template.meta", getIpAddress(request), getDate(), path));
            } catch (IOException | TemplateException e) {
                virifyCustom("metadata.save", true, model);
                log.error(e.getMessage());
                return TEMPLATE_ERROR;
            }
        }
        return TEMPLATE_DONE;
    }

    /**
     * @param path
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("publishPlace")
    public String publishPlace(String path, HttpServletRequest request, HttpSession session) {
        try {
            if (notEmpty(path)) {
                path = INCLUDE_DIRECTORY + path;
                SysSite site = getSite(request);
                templateComponent.staticPlace(site, path,
                        metadataComponent.getTemplateMetadata(siteComponent.getStaticTemplateFilePath(site, path)));
                logOperateService.save(new LogOperate(site.getId(), getAdminFromSession(session).getId(),
                        LogLoginService.CHANNEL_WEB_MANAGER, "static", getIpAddress(request), getDate(), path));
            }
            return TEMPLATE_DONE;
        } catch (IOException | TemplateException e) {
            log.error(e.getMessage());
            return TEMPLATE_ERROR;
        }
    }

    /**
     * @param path
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("publish")
    public String publish(String path, HttpServletRequest request, HttpSession session) {
        try {
            SysSite site = getSite(request);
            publish(site, path);
            return TEMPLATE_DONE;
        } catch (IOException | TemplateException e) {
            return TEMPLATE_ERROR;
        }
    }

    private void publish(SysSite site, String path) throws IOException, TemplateException {
        if (notEmpty(path)) {
            String placeListPath = INCLUDE_DIRECTORY + path + SEPARATOR;
            Map<String, CmsPageMetadata> metadataMap = metadataComponent.getMetadataMap(siteComponent.getStaticTemplateFilePath(
                    site, placeListPath));
            for (FileInfo fileInfo : fileComponent.getFileList(siteComponent.getStaticTemplateFilePath(site, placeListPath))) {
                String filePath = placeListPath + fileInfo.getFileName();
                CmsPageMetadata metadata = metadataMap.get(fileInfo.getFileName());
                if (empty(metadata)) {
                    metadata = new CmsPageMetadata();
                }
                templateComponent.staticPlace(site, filePath, metadata);
            }
            CmsPageMetadata metadata = metadataComponent.getTemplateMetadata(siteComponent.getStaticTemplateFilePath(site, path));
            if (notEmpty(metadata.getPublishPath())) {
                templateComponent.createStaticFile(site, getFullFileName(site, path), metadata.getPublishPath(), null, metadata,
                        null);
            }
        }
    }
}
