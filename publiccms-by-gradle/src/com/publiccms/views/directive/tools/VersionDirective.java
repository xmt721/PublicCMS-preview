package com.publiccms.views.directive.tools;

import java.io.IOException;

import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Component;

import com.publiccms.common.base.AbstractTemplateDirective;
import com.sanluan.common.handler.RenderHandler;

/**
 * 
 * VersionDirective 技术框架版本指令
 *
 */
@Component
public class VersionDirective extends AbstractTemplateDirective {

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        handler.put("springVersion", SpringVersion.getVersion());
        handler.put("hibernateVersion", org.hibernate.Version.getVersionString());
        handler.put("hibernateSearchVersion", org.hibernate.search.engine.Version.getVersionString());
        handler.put("luceneVersion", org.apache.lucene.util.Version.LATEST.toString());
        handler.render();
    }

}
