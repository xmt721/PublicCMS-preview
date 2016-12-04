package com.publiccms.controller.admin.cms;

import static com.publiccms.logic.service.log.LogLoginService.CHANNEL_WEB_MANAGER;
import static com.sanluan.common.tools.RequestUtils.getIpAddress;
import static org.apache.commons.lang3.StringUtils.join;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.publiccms.common.base.AbstractController;
import com.publiccms.entities.log.LogOperate;
import com.publiccms.entities.log.LogUpload;
import com.publiccms.entities.sys.SysSite;
import com.publiccms.logic.component.file.FileComponent;
import com.publiccms.logic.service.log.LogLoginService;
import com.publiccms.logic.service.log.LogUploadService;
import com.sanluan.common.tools.ZipUtils;

/**
 * 
 * CmsResourceAdminController
 *
 */
@Controller
@RequestMapping("cmsResource")
public class CmsResourceAdminController extends AbstractController {
    @Autowired
    private FileComponent fileComponent;
    @Autowired
    protected LogUploadService logUploadService;

    /**
     * @param file
     * @param path
     * @param request
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("doUpload")
    public String upload(MultipartFile file, String path, HttpServletRequest request, HttpSession session, ModelMap model) {
        if (notEmpty(file)) {
            try {
                SysSite site = getSite(request);
                path = path + SEPARATOR + file.getOriginalFilename();
                fileComponent.upload(file, siteComponent.getResourceFilePath(site, path));
                logUploadService.save(new LogUpload(site.getId(), getAdminFromSession(session).getId(), CHANNEL_WEB_MANAGER,
                        false, file.getSize(), getIpAddress(request), getDate(), path));
            } catch (IOException e) {
                verifyCustom("resource.save", true, model);
                log.error(e.getMessage());
                return TEMPLATE_ERROR;
            }
        }
        return TEMPLATE_DONE;
    }

    /**
     * @param paths
     * @param request
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("delete")
    public String delete(String[] paths, HttpServletRequest request, HttpSession session, ModelMap model) {
        if (notEmpty(paths)) {
            SysSite site = getSite(request);
            for (String path : paths) {
                String filePath = siteComponent.getResourceFilePath(site, path);
                if (verifyCustom("notExist.resource", !fileComponent.deleteFile(filePath), model)) {
                    return TEMPLATE_ERROR;
                }
            }
            logOperateService
                    .save(new LogOperate(site.getId(), getAdminFromSession(session).getId(), LogLoginService.CHANNEL_WEB_MANAGER,
                            "delete.web.resource", getIpAddress(request), getDate(), join(paths, ',')));
        }
        return TEMPLATE_DONE;
    }

    /**
     * @param path
     * @param request
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("zip")
    public String zip(String path, HttpServletRequest request, HttpSession session, ModelMap model) {
        if (notEmpty(path)) {
            SysSite site = getSite(request);
            String filePath = siteComponent.getResourceFilePath(site, path);
            File file = new File(filePath);
            if (notEmpty(file) && file.isDirectory()) {
                try {
                    ZipUtils.zip(filePath, filePath + ".zip");
                } catch (IOException e) {
                    verifyCustom("resource.zip", true, model);
                    log.error(e.getMessage(), e);
                }
            }
            logOperateService.save(new LogOperate(site.getId(), getAdminFromSession(session).getId(),
                    LogLoginService.CHANNEL_WEB_MANAGER, "zip.web.resource", getIpAddress(request), getDate(), path));
        }
        return TEMPLATE_DONE;
    }

    /**
     * @param path
     * @param request
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("unzip")
    public String unzip(String path, HttpServletRequest request, HttpSession session, ModelMap model) {
        unzip(path, false, request, session, model);
        return TEMPLATE_DONE;
    }

    /**
     * @param path
     * @param request
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("unzipHere")
    public String unzipHere(String path, HttpServletRequest request, HttpSession session, ModelMap model) {
        unzip(path, true, request, session, model);
        return TEMPLATE_DONE;
    }

    /**
     * @param path
     * @param here
     * @param request
     * @param session
     * @param model
     */
    private void unzip(String path, boolean here, HttpServletRequest request, HttpSession session, ModelMap model) {
        if (notEmpty(path) && path.toLowerCase().endsWith(".zip")) {
            SysSite site = getSite(request);
            String filePath = siteComponent.getResourceFilePath(site, path);
            File file = new File(filePath);
            if (notEmpty(file) && file.isFile()) {
                try {
                    if (here) {
                        ZipUtils.unzipHere(filePath);
                    } else {
                        ZipUtils.unzip(filePath, filePath.substring(0, filePath.length() - 4), true);
                    }
                } catch (IOException e) {
                    verifyCustom("resource.unzip", true, model);
                    log.error(e.getMessage(), e);
                }
            }
            logOperateService.save(new LogOperate(site.getId(), getAdminFromSession(session).getId(),
                    LogLoginService.CHANNEL_WEB_MANAGER, "unzip.web.resource", getIpAddress(request), getDate(), path));
        }
    }

    /**
     * @param path
     * @param fileName
     * @param request
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("createDirectory")
    public String createDirectory(String path, String fileName, HttpServletRequest request, HttpSession session, ModelMap model) {
        if (null!=path && notEmpty(fileName)) {
            SysSite site = getSite(request);
            path = path + SEPARATOR + fileName;
            String filePath = siteComponent.getResourceFilePath(site, path);
            File file = new File(filePath);
            file.mkdirs();
            logOperateService.save(new LogOperate(site.getId(), getAdminFromSession(session).getId(),
                    LogLoginService.CHANNEL_WEB_MANAGER, "createDirectory.web.resource", getIpAddress(request), getDate(), path));
        }
        return TEMPLATE_DONE;
    }
}
