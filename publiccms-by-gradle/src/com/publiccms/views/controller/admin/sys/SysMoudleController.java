package com.publiccms.views.controller.admin.sys;

import static com.sanluan.common.tools.RequestUtils.getIpAddress;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.publiccms.common.base.AbstractController;
import com.publiccms.entities.log.LogOperate;
import com.publiccms.entities.sys.SysMoudle;
import com.publiccms.entities.sys.SysRoleMoudle;
import com.publiccms.entities.sys.SysSite;
import com.publiccms.logic.service.log.LogLoginService;
import com.publiccms.logic.service.sys.SysMoudleService;
import com.publiccms.logic.service.sys.SysRoleAuthorizedService;
import com.publiccms.logic.service.sys.SysRoleMoudleService;

@Controller
@RequestMapping("sysMoudle")
public class SysMoudleController extends AbstractController {
    @Autowired
    private SysMoudleService service;
    @Autowired
    private SysRoleMoudleService roleMoudleService;
    @Autowired
    private SysRoleAuthorizedService roleAuthorizedService;

    @RequestMapping(SAVE)
    public String save(SysMoudle entity, HttpServletRequest request, HttpSession session) {
        SysSite site = getSite(request);
        if (notEmpty(entity.getId())) {
            service.update(entity.getId(), entity, new String[] { ID });
            if (notEmpty(entity.getId())) {
                logOperateService.save(new LogOperate(site.getId(), getAdminFromSession(session).getId(),
                        LogLoginService.CHANNEL_WEB_MANAGER, "update.moudle", getIpAddress(request), getDate(), entity.getId()
                                + ":" + entity.getName()));
            }
        } else {
            service.save(entity);
            logOperateService.save(new LogOperate(site.getId(), getAdminFromSession(session).getId(),
                    LogLoginService.CHANNEL_WEB_MANAGER, "save.moudle", getIpAddress(request), getDate(), entity.getId() + ":"
                            + entity.getName()));
        }
        return TEMPLATE_DONE;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(DELETE)
    public String delete(Integer id, HttpServletRequest request, HttpSession session) {
        SysMoudle entity = service.getEntity(id);
        if (notEmpty(entity)) {
            service.delete(id);
            List<SysRoleMoudle> roleMoudleList = (List<SysRoleMoudle>) roleMoudleService.getPage(null, id, null, null).getList();
            roleMoudleService.deleteByMoudleId(id);
            for (SysRoleMoudle roleMoudle : roleMoudleList) {
                Set<Integer> moudleIds = new HashSet<Integer>();
                for (SysRoleMoudle roleMoudle2 : (List<SysRoleMoudle>) roleMoudleService.getPage(roleMoudle.getRoleId(), null,
                        null, null).getList()) {
                    moudleIds.add(roleMoudle2.getMoudleId());
                }
                if (!moudleIds.isEmpty()) {
                    roleAuthorizedService.dealRoleMoudles(roleMoudle.getRoleId(),
                            service.getEntitys(moudleIds.toArray(new Integer[] {})));
                }
            }
            logOperateService.save(new LogOperate(getSite(request).getId(), getAdminFromSession(session).getId(),
                    LogLoginService.CHANNEL_WEB_MANAGER, "delete.moudle", getIpAddress(request), getDate(), id + ":"
                            + entity.getName()));
        }
        return TEMPLATE_DONE;
    }
}