package com.publiccms.views.directive.cms;

// Generated 2015-7-10 16:36:23 by com.sanluan.common.source.SourceMaker

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.publiccms.entities.cms.CmsContentTag;
import com.publiccms.logic.service.cms.CmsContentTagService;
import com.publiccms.common.base.AbstractTemplateDirective;
import com.sanluan.common.handler.RenderHandler;

@Component
public class CmsContentTagDirective extends AbstractTemplateDirective {

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        Integer id = handler.getInteger("id");
        if (notEmpty(id)) {
            CmsContentTag entity = service.getEntity(id);
            handler.put("object", entity).render();
        } else {
            Integer[] ids = handler.getIntegerArray("ids");
            if (notEmpty(ids)) {
                List<CmsContentTag> entityList = service.getEntitys(ids);
                Map<String, CmsContentTag> map = new LinkedHashMap<String, CmsContentTag>();
                for (CmsContentTag entity : entityList) {
                    map.put(String.valueOf(entity.getId()), entity);
                }
                handler.put("map", map).render();
            }
        }
    }

    @Autowired
    private CmsContentTagService service;

}
