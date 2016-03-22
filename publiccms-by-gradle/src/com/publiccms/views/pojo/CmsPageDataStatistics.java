package com.publiccms.views.pojo;

import com.publiccms.entities.cms.CmsPageData;
import com.sanluan.common.base.Base;

public class CmsPageDataStatistics extends Base {
    private int id;
    private int clicks;
    private CmsPageData entity;

    public CmsPageDataStatistics(int id, int clicks, CmsPageData entity) {
        this.clicks = clicks;
        this.id = id;
        this.entity = entity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public CmsPageData getEntity() {
        return entity;
    }

    public void setEntity(CmsPageData entity) {
        this.entity = entity;
    }
}