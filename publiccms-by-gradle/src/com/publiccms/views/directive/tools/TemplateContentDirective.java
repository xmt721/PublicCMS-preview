package com.publiccms.views.directive.tools;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.publiccms.common.base.AbstractTemplateDirective;
import com.publiccms.logic.component.FileComponent;
import com.sanluan.common.handler.RenderHandler;

@Component
public class TemplateContentDirective extends AbstractTemplateDirective {

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        String path = handler.getString("path");
        String type = handler.getString("type");
        if (notEmpty(path)) {
            handler.put("object", fileComponent.getFileContent(siteComponent.getTemplateFilePath(getSite(handler), type, path)))
                    .render();
        }
    }

    @Autowired
    private FileComponent fileComponent;
}
