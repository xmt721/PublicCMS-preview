package com.publiccms.logic.service.cms;

// Generated 2015-12-24 10:49:03 by com.sanluan.common.source.SourceMaker

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.publiccms.entities.cms.CmsPageData;
import com.publiccms.logic.dao.cms.CmsPageDataDao;
import com.publiccms.views.pojo.CmsPageDataStatistics;
import com.sanluan.common.base.BaseService;
import com.sanluan.common.handler.PageHandler;

@Service
@Transactional
public class CmsPageDataService extends BaseService<CmsPageData> {
    public static final int STATUS_CONTRIBUTE = 0, STATUS_NORMAL = 1;
    public static final String ITEM_TYPE_CONTENT = "content", ITEM_TYPE_CATEGORY = "category", ITEM_TYPE_CUSTOM = "custom";

    @Transactional(readOnly = true)
    public PageHandler getPage(Integer siteId, Integer userId, String path, String itemType, Integer itemId,
            Date startPublishDate, Date endPublishDate, Integer status, Boolean disabled, String orderField, String orderType,
            Integer pageIndex, Integer pageSize) {
        return dao.getPage(siteId, userId, path, itemType, itemId, startPublishDate, endPublishDate, status, disabled,
                orderField, orderType, pageIndex, pageSize);
    }

    public void updateStatistics(Collection<CmsPageDataStatistics> entitys) {
        for (CmsPageDataStatistics pageDataStatistics : entitys) {
            CmsPageData entity = getEntity(pageDataStatistics.getId());
            if (notEmpty(entity)) {
                entity.setClicks(entity.getClicks() + pageDataStatistics.getClicks());
            }
        }
    }

    public void check(int siteId, Integer[] ids) {
        Date now = getDate();
        for (CmsPageData entity : getEntitys(ids)) {
            if (siteId == entity.getSiteId() && STATUS_CONTRIBUTE == entity.getStatus()) {
                entity.setStatus(STATUS_NORMAL);
                if (now.after(entity.getPublishDate())) {
                    entity.setPublishDate(now);
                }
            }
        }
    }

    public void refresh(int siteId, Integer[] ids) {
        Date now = getDate();
        for (CmsPageData entity : getEntitys(ids)) {
            if (notEmpty(entity) && STATUS_NORMAL == entity.getStatus() && siteId == entity.getSiteId()) {
                if (now.after(entity.getPublishDate())) {
                    entity.setPublishDate(now);
                }
            }
        }
    }

    public void delete(int siteId, Integer[] ids) {
        for (CmsPageData entity : getEntitys(ids)) {
            if (siteId == entity.getSiteId() && !entity.isDisabled()) {
                entity.setDisabled(true);
            }
        }
    }

    public int delete(int siteId, String path) {
        return dao.delete(siteId, path);
    }

    @Autowired
    private CmsPageDataDao dao;
}