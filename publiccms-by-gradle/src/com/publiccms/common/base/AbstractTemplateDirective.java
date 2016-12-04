package com.publiccms.common.base;

import static com.publiccms.logic.component.site.SiteComponent.CONTEXT_SITE;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import com.publiccms.entities.sys.SysApp;
import com.publiccms.entities.sys.SysSite;
import com.publiccms.entities.sys.SysUser;
import com.publiccms.entities.sys.SysUserToken;
import com.publiccms.logic.component.site.SiteComponent;
import com.publiccms.logic.service.sys.SysUserService;
import com.publiccms.logic.service.sys.SysUserTokenService;
import com.sanluan.common.directive.BaseTemplateDirective;
import com.sanluan.common.handler.HttpParameterHandler;
import com.sanluan.common.handler.RenderHandler;

/**
 * 
 * AbstractTemplateDirective 自定义模板指令基类
 *
 */
public abstract class AbstractTemplateDirective extends BaseTemplateDirective {
    public SysSite getSite(RenderHandler handler) throws Exception {
        return (SysSite) handler.getAttribute(CONTEXT_SITE);
    }

    @Override
    public void execute(HttpMessageConverter<Object> httpMessageConverter, MediaType mediaType, HttpServletRequest request,
            String callback, HttpServletResponse response) throws IOException, Exception {
        HttpParameterHandler handler = new HttpParameterHandler(httpMessageConverter, mediaType, request, callback, response);
        SysApp app = null;
        SysUser user = null;
        if (needAppToken() && empty(app = getApp(handler))) {
            handler.put("error", "needAppToken").render();
        } else if (needUserToken() && empty(user = getUser(handler))) {
            handler.put("error", "needLogin").render();
        } else {
            if (needAppToken()) {
                request.getParameterMap().put("appId", new String[] { String.valueOf(app.getId()) });
            }
            if (needUserToken()) {
                request.getParameterMap().put("userId", new String[] { String.valueOf(user.getId()) });
            }
            execute(handler);
            handler.render();
        }
    }

    protected SysUser getUser(RenderHandler handler) throws Exception {
        String authToken = handler.getString("authToken");
        Long authUserId = handler.getLong("authUserId");
        if (notEmpty(authToken) && notEmpty(authUserId)) {
            SysUserToken sysUserToken = sysUserTokenService.getEntity(authToken);
            if (notEmpty(sysUserToken) && sysUserToken.getUserId() == authUserId) {
                return sysUserService.getEntity(sysUserToken.getUserId());
            }
        }
        return null;
    }

    public boolean needAppToken() {
        return false;
    }

    public boolean needUserToken() {
        return false;
    }

    @Autowired
    private SysUserTokenService sysUserTokenService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    protected SiteComponent siteComponent;
}