package com.publiccms.logic.dao.sys;

// Generated 2016-1-19 11:41:45 by com.sanluan.common.source.SourceMaker

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.publiccms.entities.sys.SysDeptPage;
import com.sanluan.common.base.BaseDao;
import com.sanluan.common.handler.PageHandler;
import com.sanluan.common.handler.QueryHandler;

@Repository
public class SysDeptPageDao extends BaseDao<SysDeptPage> {
    public PageHandler getPage(Integer deptId, String type, String page, Integer pageIndex, Integer pageSize) {
        QueryHandler queryHandler = getQueryHandler("from SysDeptPage bean");
        if (notEmpty(deptId)) {
            queryHandler.condition("bean.deptId = :deptId").setParameter("deptId", deptId);
        }
        if (notEmpty(type)) {
            queryHandler.condition("bean.type = :type").setParameter("type", type);
        }
        if (notEmpty(page)) {
            queryHandler.condition("bean.page = :page").setParameter("page", page);
        }
        queryHandler.order("bean.id desc");
        return getPage(queryHandler, pageIndex, pageSize);
    }

    public SysDeptPage getEntity(Integer deptId, String type, String page) {
        if (notEmpty(deptId) && notEmpty(type) && notEmpty(page)) {
            QueryHandler queryHandler = getQueryHandler("from SysDeptPage bean");
            queryHandler.condition("bean.deptId = :deptId").setParameter("deptId", deptId);
            queryHandler.condition("bean.type = :type").setParameter("type", type);
            queryHandler.condition("bean.page = :page").setParameter("page", page);
            return getEntity(queryHandler);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<SysDeptPage> getEntitys(Integer deptId, String type, String[] pages) {
        if (notEmpty(deptId) && notEmpty(type) && notEmpty(pages)) {
            QueryHandler queryHandler = getQueryHandler("from SysDeptPage bean");
            queryHandler.condition("bean.deptId = :deptId").setParameter("deptId", deptId);
            queryHandler.condition("bean.type = :type").setParameter("type", type);
            queryHandler.condition("bean.page in (:pages)").setParameter("pages", pages);
            return (List<SysDeptPage>) getList(queryHandler);
        }
        return new ArrayList<SysDeptPage>();
    }

    @Override
    protected SysDeptPage init(SysDeptPage entity) {
        return entity;
    }

}