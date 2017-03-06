package com.publiccms.logic.component.template;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.publiccms.common.spi.SiteCache;
import com.publiccms.entities.sys.SysSite;
import com.publiccms.logic.component.site.SiteComponent;
import com.publiccms.views.pojo.CmsModel;
import com.publiccms.views.pojo.ExtendField;
import com.sanluan.common.api.Json;
import com.sanluan.common.base.Base;
import com.sanluan.common.cache.CacheEntity;
import com.sanluan.common.cache.CacheEntityFactory;
import com.sanluan.common.tools.SqlTool;

/**
 *
 * ModelComponent 模型操作组件
 *
 */
@Component
public class ModelComponent extends Base implements SiteCache,Json {

    private CacheEntity<Integer, Map<String, CmsModel>> modelCache;
    @Autowired
    private SiteComponent siteComponent;
    @Autowired
    private  SessionFactory sessionFactory;

    public List<CmsModel> getList(SysSite site, String parentId, Boolean hasChild, Boolean onlyUrl, Boolean hasImages,
            Boolean hasFiles) {
        List<CmsModel> modelList = new ArrayList<CmsModel>();
        Map<String, CmsModel> map = getMap(site);
        for (CmsModel model : map.values()) {
            if ((empty(parentId) && empty(model.getParentId()) || notEmpty(parentId) && parentId.equals(model.getParentId()))
                    || (notEmpty(hasChild) && hasChild.equals(model.isHasChild()))
                    || (notEmpty(onlyUrl) && onlyUrl.equals(model.isOnlyUrl()))
                    || (notEmpty(hasImages) && hasImages.equals(model.isHasImages()))
                    || (notEmpty(hasFiles) && hasFiles.equals(model.isHasFiles()))) {
                modelList.add(model);
            }
        }
        return modelList;
    }

    public Map<String, CmsModel> getMap(SysSite site) {
        Map<String, CmsModel> modelMap = modelCache.get(site.getId());
        if (empty(modelMap)) {
            File file = new File(siteComponent.getModelFilePath(site));
            if (notEmpty(file)) {
                try {
                    modelMap = objectMapper.readValue(file, new TypeReference<Map<String, CmsModel>>() {
                    });
                } catch (IOException | ClassCastException e) {
                    modelMap = new HashMap<String, CmsModel>();
                }
            } else {
                modelMap = new HashMap<String, CmsModel>();
            }
            modelCache.put(site.getId(), modelMap);
        }
        return modelMap;
    }

    /**
     * 保存模型
     * 
     * @param site
     * @param modelMap
     * @return
     */
    public boolean save(SysSite site, Map<String, CmsModel> modelMap) {
        File file = new File(siteComponent.getModelFilePath(site));
        if (empty(file)) {
            file.getParentFile().mkdirs();
        }
        try {
            objectMapper.writeValue(file, modelMap);
        } catch (IOException e) {
            return false;
        }
        clear(site.getId());//每次保存以后，清除缓存中当前站点的模型定义。这样调用this.getMap时，都能获得最新的模型定义。
        return true;
    }
    /**
     * 创建与模型对应的数据库表
     * */
    public void createModelTable(CmsModel model){
    	String tableName="XMT_"+model.getId();
    	String sql=SqlTool.createTable(tableName, model.getExtendList());
    	Session session=sessionFactory.openSession();
    	SQLQuery query=session.createSQLQuery(sql);
    	query.executeUpdate();
    	session.close();
    }
    /**
     * 删除与模型对应的数据库表
     * */
    public void dropModelTable(CmsModel model){
    	String tableName="XMT_"+model.getId();
    	String sql=SqlTool.dropTable(tableName);
    	Session session=sessionFactory.openSession();
    	SQLQuery query=session.createSQLQuery(sql);
    	query.executeUpdate();
    	session.close();
    }
    /**
     * 更新表结构
     **/
    public void updateModelTable(CmsModel oldModel,CmsModel newModel){
    	List<String> sqls=new ArrayList<String>();
    	//首先比较表名是否修改
    	if(!oldModel.getId().equals(newModel.getId())){
    		sqls.add(" rename table XMT_"+oldModel.getId()+" to XMT_"+newModel.getId()+";");    		
    	}
    	//比较字段是否修改
    	List<ExtendField> oldFields=oldModel.getExtendList();
    	oldFields=oldFields==null?new ArrayList<ExtendField>():oldFields;
    	List<ExtendField> newFields=newModel.getExtendList();
    	newFields=newFields==null?new ArrayList<ExtendField>():newFields;
    	for(ExtendField oldField:oldFields){
    		String oldFieldCode=oldField.getId().getCode();
    		String oldFieldType=oldField.getInputType();
    		boolean existFlag=false;
    		for(ExtendField newField:newFields){
    			String newFieldCode=newField.getId().getCode();
    			String newFieldType=newField.getInputType();
    			if(!newFieldCode.equals(oldFieldCode)){
    				continue;
    			}else{
    				//同一字段，则比较数据类型
    				existFlag=true;
    				if(newFieldType.equals(oldFieldType)){
    					//类型也不变
    					newFields.remove(newField);
    				}else{
    					newFields.remove(newField);
    					//添加修改列类型的sql语句
    					sqls.add("alter table XMT_"+newModel.getId()+" modify "+newFieldCode+" "+SqlTool.switchModelType2DbType(newFieldType)+";");
    				}
					break;
    			}
    		}
    		if(!existFlag){//老字段在新模型中没有
    			sqls.add("alter table XMT_"+newModel.getId()+" drop column "+oldFieldCode+";");
    		}
    	}
    	//上方循环不能检测出新添加的字段
    	//检测新添加字段
    	for(ExtendField newField:newFields){
    		String newFieldCode=newField.getId().getCode();
			String newFieldType=newField.getInputType();
			boolean existFlag=false;
			for(ExtendField oldField:oldFields){
				String oldFieldCode=oldField.getId().getCode();
	    		if(newFieldCode.equals(oldFieldCode)){
	    			existFlag=true;
	    			oldFields.remove(oldField);
	    			break;
	    		}
			}
			if(!existFlag){//如果oldFields循环一遍后，existFlag=false，表明当前newField是新增字段
				sqls.add("alter table XMT_"+newModel.getId()+" add  column "+newFieldCode+" "+SqlTool.switchModelType2DbType(newFieldType)+";");
			}
    	}
    	//执行这一系列sql语句
    	Session session=sessionFactory.openSession();
    	for(String sql:sqls){
        	session.createSQLQuery(sql).executeUpdate();
    	}
    	session.close();
    }

    @Override
    public void clear() {
        modelCache.clear();
    }

    @Override
    public void clear(int siteId) {
        modelCache.remove(siteId);
    }

    @Autowired
    public void initCache(CacheEntityFactory cacheEntityFactory) {
        modelCache = cacheEntityFactory.createCacheEntity("cmsModel");
    }
}
