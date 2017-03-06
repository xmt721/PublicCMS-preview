package com.publiccms.views.directive.cms;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.publiccms.common.base.AbstractTemplateDirective;
import com.publiccms.entities.sys.SysSite;
import com.publiccms.logic.service.cms.CmsContentService;
import com.sanluan.common.handler.PageHandler;
import com.sanluan.common.handler.RenderHandler;
//根据扩展属性cms_content_attribute进行查询
@Component
public class CmsContentSearchByAttrDirective extends AbstractTemplateDirective {
	@Override
    public void execute(RenderHandler handler) throws IOException, Exception {
		SysSite site = getSite(handler);
        Integer[] status = new Integer[] { CmsContentService.STATUS_NORMAL };
        Boolean disabled = handler.getBoolean("disabled", false);
		//标签
		String tagIds=handler.getString("tagIds");
        Boolean hasImages = handler.getBoolean("hasImages");
        Boolean hasFiles = handler.getBoolean("hasFiles");
		//内容模型字段组成的查询条件
		String attrConStr=handler.getString("attrCon");
		ObjectMapper objectMapper=new ObjectMapper();
		List<Map> attrConList=attrConStr==null?null:objectMapper.readValue(attrConStr,List.class);
		
		PageHandler page=service.getPageByContentAttribute(site,status, disabled,hasImages,hasFiles,
				handler.getString("modelId") , 
				handler.getInteger("categoryId"),handler.getBoolean("containChild"),
				handler.getString("tagIds"), attrConList, 
				handler.getString("orderField"),handler.getString("orderType"),
				handler.getInteger("pageIndex", 1), handler.getInteger("count", 30));
		handler.put("page", page).render();
	}
	@Autowired
    private CmsContentService service;
}
