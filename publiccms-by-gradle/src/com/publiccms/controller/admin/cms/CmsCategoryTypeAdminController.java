package com.publiccms.controller.admin.cms;

import static com.sanluan.common.tools.JsonUtils.getString;

// Generated 2016-2-26 15:57:04 by com.sanluan.common.source.SourceMaker

import static com.sanluan.common.tools.RequestUtils.getIpAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.publiccms.common.base.AbstractController;
import com.publiccms.entities.cms.CmsCategoryType;
import com.publiccms.entities.log.LogOperate;
import com.publiccms.entities.sys.SysExtend;
import com.publiccms.entities.sys.SysSite;
import com.publiccms.logic.service.cms.CmsCategoryService;
import com.publiccms.logic.service.cms.CmsCategoryTypeService;
import com.publiccms.logic.service.log.LogLoginService;
import com.publiccms.logic.service.sys.SysExtendFieldService;
import com.publiccms.logic.service.sys.SysExtendService;
import com.publiccms.views.pojo.CmsCategoryTypeParamters;

/**
 * 
 * CmsCategoryTypeController
 *
 */
@Controller
@RequestMapping("cmsCategoryType")
public class CmsCategoryTypeAdminController extends AbstractController {
    @Autowired
    private CmsCategoryTypeService service;
    @Autowired
    private CmsCategoryService categoryService;
    @Autowired
    private SysExtendService extendService;
    @Autowired
    private SysExtendFieldService extendFieldService;
    
    private String[] ignoreProperties = new String[] { "id", "siteId", "extendId" };

    /**
     * @param entity
     * @param categoryTypeParamters
     * @param request
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("save")
    public String save(CmsCategoryType entity, @ModelAttribute CmsCategoryTypeParamters categoryTypeParamters,
            HttpServletRequest request, HttpSession session, ModelMap model) {
        SysSite site = getSite(request);
        if (notEmpty(entity.getId())) {
            CmsCategoryType oldEntity = service.getEntity(entity.getId());
            if (empty(oldEntity) || verifyNotEquals("siteId", site.getId(), oldEntity.getSiteId(), model)) {
                return TEMPLATE_ERROR;
            }
            entity = service.update(entity.getId(), entity, ignoreProperties);
            if (notEmpty(entity)) {
                logOperateService.save(new LogOperate(site.getId(), getAdminFromSession(session).getId(),
                        LogLoginService.CHANNEL_WEB_MANAGER, "update.categoryType", getIpAddress(request), getDate(), getString(entity)));
            }
        } else {
            entity.setSiteId(site.getId());
            service.save(entity);
            logOperateService.save(new LogOperate(site.getId(), getAdminFromSession(session).getId(),
                    LogLoginService.CHANNEL_WEB_MANAGER, "save.categoryType", getIpAddress(request), getDate(), getString(entity)));
        }
        if (empty(extendService.getEntity(entity.getExtendId()))) {
            entity = service.updateExtendId(entity.getId(),
                    (Integer) extendService.save(new SysExtend("categoryType", entity.getId())));
        }
        extendFieldService.update(entity.getExtendId(), categoryTypeParamters.getCategoryExtends());// 修改或增加分类类型扩展字段
        return TEMPLATE_DONE;
    }

    /**
     * @param id
     * @param request
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("delete")
    public String delete(Integer id, HttpServletRequest request, HttpSession session, ModelMap model) {
        SysSite site = getSite(request);
        CmsCategoryType entity = service.getEntity(id);
        if (notEmpty(entity)) {
            if (verifyNotEquals("siteId", site.getId(), entity.getSiteId(), model)
                    || verifyNotGreaterThen("category", categoryService
                            .getPage(site.getId(), null, id, null, null, null, null,null, 1).getTotalCount(), 1, model)) {
                return TEMPLATE_ERROR;
            }
            service.delete(id);
            extendService.delete(entity.getExtendId());
            logOperateService.save(new LogOperate(site.getId(), getAdminFromSession(session).getId(),
                    LogLoginService.CHANNEL_WEB_MANAGER, "delete.categoryType", getIpAddress(request), getDate(), id.toString()));
        }
        return TEMPLATE_DONE;
    }
}