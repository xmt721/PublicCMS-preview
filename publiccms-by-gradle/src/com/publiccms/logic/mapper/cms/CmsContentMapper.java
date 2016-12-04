package com.publiccms.logic.mapper.cms;

import com.publiccms.entities.cms.CmsContent;

public interface CmsContentMapper {
    CmsContent selectByPrimaryKey(Long id);
}