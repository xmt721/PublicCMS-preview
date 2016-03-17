package com.publiccms.logic.service.sys;

// Generated 2016-1-19 11:41:45 by com.sanluan.common.source.SourceMaker
import static com.publiccms.logic.service.cms.CmsPageDataService.PAGE_TYPE_DYNAMIC;
import static com.publiccms.logic.service.cms.CmsPageDataService.PAGE_TYPE_STATIC;
import static org.apache.commons.lang3.ArrayUtils.contains;
import static org.apache.commons.lang3.ArrayUtils.removeElement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.publiccms.entities.sys.SysDeptCategory;
import com.publiccms.entities.sys.SysDeptPage;
import com.publiccms.logic.dao.sys.SysDeptPageDao;
import com.sanluan.common.base.BaseService;
import com.sanluan.common.handler.PageHandler;

@Service
@Transactional
public class SysDeptPageService extends BaseService<SysDeptPage> {

    @Autowired
    private SysDeptPageDao dao;

    @Transactional(readOnly = true)
    public PageHandler getPage(Integer deptId, String type, String page, Integer pageIndex, Integer pageSize) {
        return dao.getPage(deptId, type, page, pageIndex, pageSize);
    }

    @Transactional(readOnly = true)
    public List<SysDeptPage> getEntitys(Integer deptId, String type, String[] pages) {
        return dao.getEntitys(deptId, type, pages);
    }

    @Transactional(readOnly = true)
    public SysDeptPage getEntity(Integer deptId, String page) {
        return dao.getEntity(deptId, page);
    }

    public void updateDeptPages(Integer deptId, String[] pages, String[] dynamicPages) {
        if (notEmpty(deptId)) {
            @SuppressWarnings("unchecked")
            List<SysDeptPage> list = (List<SysDeptPage>) getPage(deptId, null, null, null, null).getList();
            for (SysDeptPage deptPage : list) {
                if (PAGE_TYPE_STATIC.equalsIgnoreCase(deptPage.getType()) && contains(pages, deptPage.getPage())) {
                    pages = removeElement(pages, deptPage.getPage());
                } else if (PAGE_TYPE_DYNAMIC.equalsIgnoreCase(deptPage.getType()) && contains(dynamicPages, deptPage.getPage())) {
                    dynamicPages = removeElement(dynamicPages, deptPage.getPage());
                } else {
                    delete(deptPage.getId());
                }
            }
            saveDeptPages(deptId, pages, dynamicPages);
        }
    }

    public void delete(Integer deptId, String type, String page) {
        if (notEmpty(page) && notEmpty(type) || notEmpty(deptId)) {
            @SuppressWarnings("unchecked")
            List<SysDeptCategory> list = (List<SysDeptCategory>) getPage(deptId, type, page, null, null).getList();
            for (SysDeptCategory deptCategory : list) {
                delete(deptCategory.getId());
            }
        }
    }

    public void saveDeptPages(Integer deptId, String[] pages, String[] dynamicPages) {
        if (notEmpty(deptId)) {
            if (notEmpty(pages)) {
                for (String page : pages) {
                    save(new SysDeptPage(deptId, PAGE_TYPE_STATIC, page));
                }

            }
            if (notEmpty(dynamicPages)) {
                for (String page : dynamicPages) {
                    save(new SysDeptPage(deptId, PAGE_TYPE_DYNAMIC, page));
                }
            }
        }
    }
}