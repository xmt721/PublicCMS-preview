package com.publiccms.logic.service.cms;

// Generated 2015-5-8 16:50:23 by com.sanluan.common.source.SourceMaker

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.publiccms.entities.cms.CmsPageDataAttribute;
import com.sanluan.common.base.BaseService;

@Service
@Transactional
public class CmsPageDataAttributeService extends BaseService<CmsPageDataAttribute> {

    public void updateAttribute(Integer pageDataId, String data) {
        CmsPageDataAttribute attribute = getEntity(pageDataId);
        if (notEmpty(attribute)) {
            attribute.setData(data);
        } else {
            save(new CmsPageDataAttribute(pageDataId, data));
        }
    }
}