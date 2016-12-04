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
import com.publiccms.logic.component.site.SiteComponent;
import com.sanluan.common.directive.BaseTemplateDirective;
import com.sanluan.common.handler.HttpParameterHandler;
import com.sanluan.common.handler.RenderHandler;

/**
 * 
 * AbstractTemplateDirective 自定义模板指令基类
 *
 */
public abstract class AbstractTaskDirective extends BaseTemplateDirective {
    public SysSite getSite(RenderHandler handler) throws Exception {
        return (SysSite) handler.getAttribute(CONTEXT_SITE);
    }

    @Override
    public void execute(HttpMessageConverter<Object> httpMessageConverter, MediaType mediaType, HttpServletRequest request,
            String callback, HttpServletResponse response) throws IOException, Exception {
        HttpParameterHandler handler = new HttpParameterHandler(httpMessageConverter, mediaType, request, callback, response);
        SysApp app = null;
        if (empty(app = getApp(handler))) {
            handler.put("error", "needAppToken").render();
        } else {
            request.getParameterMap().put("appId", new String[] { String.valueOf(app.getId()) });
            execute(handler);
            handler.render();
        }
    }

    @Autowired
    protected SiteComponent siteComponent;
}