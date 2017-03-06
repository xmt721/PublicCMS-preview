package com.publiccms.logic.dao.cms;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

// Generated 2015-5-8 16:50:23 by com.sanluan.common.source.SourceMaker

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.search.FullTextQuery;
import org.springframework.stereotype.Repository;

import com.publiccms.entities.cms.CmsContent;
import com.publiccms.entities.sys.SysSite;
import com.publiccms.logic.component.template.ModelComponent;
import com.publiccms.logic.service.cms.CmsContentService;
import com.publiccms.views.pojo.CmsContentAndModelData;
import com.publiccms.views.pojo.CmsModel;
import com.publiccms.views.pojo.ExtendData;
import com.publiccms.views.pojo.ExtendField;
import com.sanluan.common.base.BaseDao;
import com.sanluan.common.handler.FacetPageHandler;
import com.sanluan.common.handler.PageHandler;
import com.sanluan.common.handler.QueryHandler;
import com.sanluan.common.handler.SqlQueryHandler;

@Repository
public class CmsContentDao extends BaseDao<CmsContent> {
    private static final String[] textFields = new String[] { "title", "author", "editor", "description" };
    private static final String[] tagFields = new String[] { "tagIds" };
    private static final String[] facetFields = new String[] { "categoryId", "modelId" };

    public PageHandler query(Integer siteId, String text, String tagId, Integer pageIndex, Integer pageSize) {
        FullTextQuery query;
        if (notEmpty(tagId)) {
            query = getQuery(tagFields, tagId);
        } else {
            query = getQuery(textFields, text);
        }
        query.enableFullTextFilter("publishDate").setParameter("publishDate", getDate());
        query.enableFullTextFilter("siteId").setParameter("siteId", siteId);
        return getPage(query, pageIndex, pageSize);
    }

    public FacetPageHandler facetQuery(Integer siteId, String categoryId, String modelId, String text, String tagId,
            Integer pageIndex, Integer pageSize) {
        FullTextQuery query;
        if (notEmpty(tagId)) {
            query = getFacetQuery(tagFields, facetFields, tagId, 10);
        } else {
            query = getFacetQuery(textFields, facetFields, text, 10);
        }
        query.enableFullTextFilter("publishDate").setParameter("publishDate", getDate());
        query.enableFullTextFilter("siteId").setParameter("siteId", siteId);
        Map<String, String> valueMap = new HashMap<String, String>();
        if (notEmpty(categoryId)) {
            valueMap.put("categoryId", categoryId);
        }
        if (notEmpty(modelId)) {
            valueMap.put("modelId", modelId);
        }
        return getFacetPage(query, facetFields, valueMap, pageIndex, pageSize);
    }

    public int deleteByCategoryIds(int siteId, Integer[] categoryIds) {
        if (notEmpty(categoryIds)) {
            QueryHandler queryHandler = getQueryHandler("update CmsContent bean set bean.disabled = :disabled");
            queryHandler.condition("bean.siteId = :siteId").setParameter("siteId", siteId);
            queryHandler.condition("bean.categoryId in (:categoryIds)").setParameter("categoryIds", categoryIds)
                    .setParameter("disabled", true);
            return update(queryHandler);
        }
        return 0;
    }

    public void index(int siteId, Serializable[] ids) {
        for (CmsContent entity : getEntitys(ids)) {
            if (siteId == entity.getSiteId()) {
                index(entity);
            }
        }
    }

    public PageHandler getPage(Integer siteId, Integer[] status, Integer categoryId, Integer[] categoryIds, Boolean disabled,
            String[] modelId, Long parentId, Boolean emptyParent, Boolean onlyUrl, Boolean hasImages, Boolean hasFiles,
            String title, Long userId, Long checkUserId, Date startPublishDate, Date endPublishDate,String tagIds, String orderField,
            String orderType, Integer pageIndex, Integer pageSize) {
        QueryHandler queryHandler = getQueryHandler("from CmsContent bean");
        if (notEmpty(siteId)) {
            queryHandler.condition("bean.siteId = :siteId").setParameter("siteId", siteId);
        }
        if (notEmpty(status)) {
            queryHandler.condition("bean.status in (:status)").setParameter("status", status);
        }
        if (notEmpty(categoryIds)) {
            queryHandler.condition("bean.categoryId in (:categoryIds)").setParameter("categoryIds", categoryIds);
        } else if (notEmpty(categoryId)) {
            queryHandler.condition("bean.categoryId = :categoryId").setParameter("categoryId", categoryId);
        }
        if (notEmpty(disabled)) {
            queryHandler.condition("bean.disabled = :disabled").setParameter("disabled", disabled);
        }
        if (notEmpty(modelId)) {
            queryHandler.condition("bean.modelId in (:modelId)").setParameter("modelId", modelId);
        }
        if (notEmpty(parentId)) {
            queryHandler.condition("bean.parentId = :parentId").setParameter("parentId", parentId);
        } else {
            if (notEmpty(emptyParent) && emptyParent) {
                queryHandler.condition("bean.parentId is null");
            }
        }
        if (notEmpty(onlyUrl)) {
            queryHandler.condition("bean.onlyUrl = :onlyUrl").setParameter("onlyUrl", onlyUrl);
        }
        if (notEmpty(hasImages)) {
            queryHandler.condition("bean.hasImages = :hasImages").setParameter("hasImages", hasImages);
        }
        if (notEmpty(hasFiles)) {
            queryHandler.condition("bean.hasFiles = :hasFiles").setParameter("hasFiles", hasFiles);
        }
        if (notEmpty(title)) {
            queryHandler.condition("(bean.title like :title)").setParameter("title", like(title));
        }
        if (notEmpty(userId)) {
            queryHandler.condition("bean.userId = :userId").setParameter("userId", userId);
        }
        if (notEmpty(checkUserId)) {
            queryHandler.condition("bean.checkUserId = :checkUserId").setParameter("checkUserId", checkUserId);
        }
        if (notEmpty(startPublishDate)) {
            queryHandler.condition("bean.publishDate > :startPublishDate").setParameter("startPublishDate", startPublishDate);
        }
        if (notEmpty(endPublishDate)) {
            queryHandler.condition("bean.publishDate <= :endPublishDate").setParameter("endPublishDate", endPublishDate);
        }
        //根据标签搜索
        if(notEmpty(tagIds)){
            queryHandler.condition("bean.tagIds like :tagIds)").setParameter("tagIds", this.like(tagIds));
        }
        if ("asc".equalsIgnoreCase(orderType)) {
            orderType = "asc";
        } else {
            orderType = "desc";
        }
        if (null == orderField) {
            orderField = BLANK;
        }
        switch (orderField) {
        case "scores":
            queryHandler.order("bean.scores " + orderType);
            break;
        case "comments":
            queryHandler.order("bean.comments " + orderType);
            break;
        case "clicks":
            queryHandler.order("bean.clicks " + orderType);
            break;
        default:
            queryHandler.order("bean.publishDate " + orderType);
        }
        return getPage(queryHandler, pageIndex, pageSize);
    }

    public List searchByModel(Integer siteId,String modelId){
    	QueryHandler queryHandler=this.getQueryHandler("from CmsContent bean");
        if (notEmpty(siteId)) {
            queryHandler.condition("bean.siteId = :siteId").setParameter("siteId", siteId);
        }
        if (notEmpty(modelId)) {
            queryHandler.condition("bean.modelId = :modelId").setParameter("modelId", modelId);
        }
        return this.getList(queryHandler);
    }
    
    public PageHandler getPageByContentAttribute(Integer siteId,Map<String, CmsModel> modelMap,
    		Integer[] status,Boolean disabled,Boolean hasImages,Boolean hasFiles,String modelId,Integer categoryId, Integer[] categoryIds,String tagIds,List<Map> attrConList,
    		String orderField,String orderType, Integer pageIndex, Integer pageSize) throws Exception{
        CmsModel model=modelMap.get(modelId);
        if(this.empty(model)){
        	throw new Exception("无效的model id");
        }
        List<ExtendField> extendFieldList=model.getExtendList();
    	String sql="select a.id,a.site_id,a.title,a.user_id,a.check_user_id,a.category_id,"
    			+ "a.model_id,a.parent_id,a.copied,a.author,a.editor,a.only_url,a.has_images,"
    			+ "a.has_files,a.has_static,a.url,a.description,a.tag_ids,a.cover,a.childs,"
    			+ "a.scores,a.comments,a.clicks,a.publish_date,a.create_date";
    	SqlQueryHandler queryHandler=getSqlQueryHandler(sql);
    	if(this.notEmpty(extendFieldList)){
    		queryHandler.append(",");
    		for(int i=0;i<extendFieldList.size();i++){
        		ExtendField extendField=extendFieldList.get(i);
        		queryHandler.append("b."+extendField.getId().getCode());
        		if(i<(extendFieldList.size()-1)){
        			queryHandler.append(",");
       		 	}
        	}
    	}
    	queryHandler.append(" from cms_content a inner join xmt_"+modelId+" b on a.id=b.content_id " );
    	if(this.notEmpty(status)){
        	queryHandler.condition("a.status=:status").setParameter("status",status);
    	}
    	if(this.notEmpty(disabled)){
        	queryHandler.condition("a.disabled=:disabled").setParameter("disabled",disabled);
    	}
    	if(this.notEmpty(hasImages)){
        	queryHandler.condition("a.has_images=:hasImages").setParameter("hasImages",hasImages);
    	}
    	if(this.notEmpty(hasFiles)){
        	queryHandler.condition("a.has_files=:hasFiles").setParameter("hasFiles",hasFiles);
    	}
    	if(this.notEmpty(siteId)){
        	queryHandler.condition("a.site_id=:site_id").setParameter("site_id", siteId);
    	}
    	if(this.notEmpty(modelId)){
        	queryHandler.condition("a.model_id=:model_id").setParameter("model_id", modelId);
    	}
    	if (notEmpty(categoryIds)) {
            queryHandler.condition("a.category_id in (:category_ids)").setParameter("category_ids", categoryIds);
        } else if (notEmpty(categoryId)) {
            queryHandler.condition("a.category_id  = :category_id").setParameter("category_id", categoryId);
        }
    	//根据标签搜索
        if(notEmpty(tagIds)){
            queryHandler.condition("a.tag_ids like :tag_ids").setParameter("tag_ids", this.like(tagIds));
        }
        
    	if(this.notEmpty(attrConList)){
    		for(int i=0;i<attrConList.size();i++){
        		Map attrCon=attrConList.get(i);
        		String field=(String)attrCon.get("field");
       		 	String operator=(String)attrCon.get("operator");
       		 	String namedparam=attrCon.get("namedparam")==null?null:attrCon.get("namedparam").toString();//同一个字段出现两次
       		 	Object value=attrCon.get("value");
       		 	String next=(String)attrCon.get("next");
       		 	if(namedparam==null){
       	   		 	queryHandler.condition(" b."+field+operator+":"+field+" ").setParameter(field, value);
       		 	}else{
       	   		 	queryHandler.condition(" b."+field+operator+":"+namedparam+" ").setParameter(namedparam, value);
       		 	}
        	}
    	}
    	if(!this.notEmpty(orderField)){
    		orderField="publish_date";
    	}
    	if(!this.notEmpty(orderType)){
    		orderType="desc";
    	}
    	queryHandler.order("a."+orderField+" "+orderType);
    	PageHandler page=this.getPage(queryHandler, pageIndex, pageSize);
    	List pageList=page.getList();
    	List<CmsContentAndModelData> resultList=new ArrayList<CmsContentAndModelData>();
    	for(int i=0;i<pageList.size();i++){
    		CmsContentAndModelData contentAndModelData=new CmsContentAndModelData();
    		CmsContent cmscontent=new CmsContent();
    		Object[] row=(Object[])pageList.get(i);
    		cmscontent.setId(row[0]==null?null:((BigInteger)row[0]).longValue());
    		cmscontent.setSiteId((int)row[1]);
    		cmscontent.setTitle((String)row[2]);
    		cmscontent.setUserId(row[3]==null?null:((BigInteger)row[3]).longValue());
    		cmscontent.setCheckUserId(row[4]==null?null:((BigInteger)row[4]).longValue());
    		cmscontent.setCategoryId((int)row[5]);
    		cmscontent.setModelId((String)row[6]);
    		cmscontent.setParentId(row[7]==null?null:((BigInteger)row[7]).longValue());
    		cmscontent.setCopied((boolean)row[8]);
    		cmscontent.setAuthor((String)row[9]);
    		cmscontent.setEditor((String)row[10]);
    		cmscontent.setOnlyUrl((boolean)row[11]);
    		cmscontent.setHasImages((boolean)row[12]);
    		cmscontent.setHasFiles((boolean)row[13]);
    		cmscontent.setHasStatic((boolean)row[14]);
    		cmscontent.setUrl((String)row[15]);
    		cmscontent.setDescription((String)row[16]);	
    		cmscontent.setTagIds((String)row[17]);
    		cmscontent.setCover((String)row[18]);
    		cmscontent.setChilds((int)row[19]);
    		cmscontent.setScores((int)row[20]);
    		cmscontent.setComments((int)row[21]);
    		cmscontent.setClicks((int)row[22]);
    		cmscontent.setPublishDate((Date)row[23]);
    		cmscontent.setCreateDate((Date)row[24]);

    		List<ExtendData> extendModelData=new ArrayList<ExtendData>();
    		for(int j=0;j<extendFieldList.size();j++){
    			ExtendField extendField=extendFieldList.get(j);
    			String fieldName=extendField.getId().getCode();
    			ExtendData extendData=new ExtendData();
    			extendData.setName(fieldName);
    			extendData.setValue(row[25+j]==null?"":row[25+j].toString());
    			extendModelData.add(extendData);
        	}
    		contentAndModelData.setCmsContent(cmscontent);
    		contentAndModelData.setExtendModelData(extendModelData);
    		resultList.add(contentAndModelData);
    	}
    	PageHandler resultPage= new PageHandler(pageIndex, pageSize, page.getTotalCount(),null);
    	resultPage.setList(resultList);
    	return resultPage;
    }


    @Override
    protected CmsContent init(CmsContent entity) {
        if (empty(entity.getCreateDate())) {
            entity.setCreateDate(getDate());
        }
        if (empty(entity.getPublishDate())) {
            entity.setPublishDate(getDate());
        }
        if (empty(entity.getTagIds())) {
            entity.setTagIds(null);
        }
        if (empty(entity.getAuthor())) {
            entity.setAuthor(null);
        }
        if (empty(entity.getCover())) {
            entity.setCover(null);
        }
        return entity;
    }
    

}