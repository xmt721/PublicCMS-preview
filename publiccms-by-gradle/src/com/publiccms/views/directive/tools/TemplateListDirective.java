package com.publiccms.views.directive.tools;

// Generated 2015-5-10 17:54:56 by com.sanluan.common.source.SourceMaker

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.publiccms.logic.component.FileComponent;
import com.publiccms.logic.service.sys.SysSiteService;
import com.publiccms.common.base.AbstractTemplateDirective;
import com.sanluan.common.handler.RenderHandler;

@Component
public class TemplateListDirective extends AbstractTemplateDirective {

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        String type = handler.getString("type");
        String path = handler.getString("path", SEPARATOR);
        handler.put("list", fileComponent.getFileList(siteComponent.getTemplateFilePath(getSite(handler), type, path))).render();
    }

    @Autowired
    private SysSiteService sysSiteService;
    @Autowired
    private FileComponent fileComponent;
}