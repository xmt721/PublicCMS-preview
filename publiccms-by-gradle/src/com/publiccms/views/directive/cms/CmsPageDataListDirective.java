package com.publiccms.views.directive.cms;

// Generated 2015-12-24 10:49:03 by com.sanluan.common.source.SourceMaker

import static com.publiccms.logic.service.cms.CmsPageDataService.PAGE_TYPE_STATIC;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.publiccms.common.base.AbstractTemplateDirective;
import com.publiccms.logic.service.cms.CmsPageDataService;
import com.sanluan.common.handler.PageHandler;
import com.sanluan.common.handler.RenderHandler;

@Component
public class CmsPageDataListDirective extends AbstractTemplateDirective {

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        Date endPublishDate = handler.getDate("endPublishDate");
        Boolean disabled = handler.getBoolean("disabled", false);
        Integer status = handler.getInteger("status");
        String path = handler.getString("path");
        String type = handler.getString("type");
        if (!handler.getBoolean("admin", false)) {
            Date now = getDate();
            if (empty(endPublishDate) || endPublishDate.after(now)) {
                endPublishDate = now;
            }
            disabled = false;
            status = CmsPageDataService.STATUS_NORMAL;
        }
        if (notEmpty(path)) {
            path = path.replace("//", SEPARATOR);
        }
        if (empty(type)) {
            type = PAGE_TYPE_STATIC;
        }
        PageHandler page = service.getPage(getSite(handler).getId(), handler.getInteger("userId"), path, type,
                handler.getString("itemType"), handler.getInteger("itemId"), handler.getDate("startPublishDate"),
                handler.getDate("endPublishDate"), status, disabled, handler.getString("orderField"),
                handler.getString("orderType"), handler.getInteger("pageIndex", 1), handler.getInteger("count", 30));
        handler.put("page", page).put("path", path).render();
    }

    @Autowired
    private CmsPageDataService service;

}