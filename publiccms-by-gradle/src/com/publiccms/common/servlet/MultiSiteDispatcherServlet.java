package com.publiccms.common.servlet;

import static com.publiccms.logic.component.template.TemplateCacheComponent.CACHE_URL_PREFIX;
import static org.springframework.web.servlet.view.UrlBasedViewResolver.FORWARD_URL_PREFIX;
import static org.springframework.web.servlet.view.UrlBasedViewResolver.REDIRECT_URL_PREFIX;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import com.publiccms.common.constants.CmsVersion;
import com.publiccms.logic.component.site.SiteComponent;
import com.sanluan.common.servlet.ErrorToNotFoundDispatcherServlet;

/**
 * 
 * MultiSiteDispatcherServlet 多站点
 *
 */
public class MultiSiteDispatcherServlet extends ErrorToNotFoundDispatcherServlet {
    private SiteComponent siteComponent;
    private static final int CACHE_URL_PREFIX_LENGTH = CACHE_URL_PREFIX.length();
    private DefaultServletHttpRequestHandler installHandler;
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (CmsVersion.isInitialized()) {
            super.doService(request, response);
        } else {
            getInstallHandler().handleRequest(request, response);
        }
    }

    /**
     * @param webApplicationContext
     */
    public MultiSiteDispatcherServlet(WebApplicationContext webApplicationContext) {
        super(webApplicationContext);
    }

    @Override
    protected View resolveViewName(String viewName, Map<String, Object> model, Locale locale, HttpServletRequest request)
            throws Exception {
        String multiSiteViewName;
        if (viewName.startsWith(REDIRECT_URL_PREFIX) || viewName.startsWith(FORWARD_URL_PREFIX)) {
            multiSiteViewName = viewName;
        } else if (viewName.startsWith(CACHE_URL_PREFIX)) {
            multiSiteViewName = viewName.substring(CACHE_URL_PREFIX_LENGTH);
        } else {
            multiSiteViewName = getSiteComponent().getViewNamePreffix(request.getServerName()) + viewName;
        }
        return super.resolveViewName(multiSiteViewName, model, locale, request);
    }

    public SiteComponent getSiteComponent() {
        if (null == siteComponent) {
            siteComponent = getWebApplicationContext().getBean(SiteComponent.class);
        }
        return siteComponent;
    }

    public DefaultServletHttpRequestHandler getInstallHandler() {
        if (null == installHandler) {
            installHandler = new DefaultServletHttpRequestHandler();
            installHandler.setServletContext(getWebApplicationContext().getServletContext());
        }
        return installHandler;
    }
}
