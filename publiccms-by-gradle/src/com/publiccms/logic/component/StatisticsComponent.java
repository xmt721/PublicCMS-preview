package com.publiccms.logic.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.publiccms.logic.service.cms.CmsContentService;
import com.publiccms.views.pojo.CmsContentStatistics;
import com.sanluan.common.base.Base;
import com.sanluan.common.base.Cacheable;

@Component
public class StatisticsComponent extends Base implements Cacheable {
    private static List<Integer> cachedlist = new ArrayList<Integer>();
    private static Map<Integer, CmsContentStatistics> cachedMap = new HashMap<Integer, CmsContentStatistics>();
    @Autowired
    private CmsContentService contentService;

    private void clearCache(int size) {
        if (size < cachedlist.size()) {
            List<CmsContentStatistics> list = new ArrayList<CmsContentStatistics>();
            for (int i = 0; i < size / 10; i++) {
                list.add(cachedMap.remove(cachedlist.remove(0)));
            }
            contentService.updateStatistics(list);
        }
    }

    public CmsContentStatistics clicks(Integer id) {
        CmsContentStatistics contentStatistics = cachedMap.get(id);
        if (empty(contentStatistics)) {
            contentStatistics = new CmsContentStatistics(id, 1, 0, 0, contentService.getEntity(id));
            clearCache(300);
            cachedlist.add(id);
            cachedMap.put(id, contentStatistics);
        }
        return contentStatistics;
    }

    public CmsContentStatistics comments(Integer id) {
        CmsContentStatistics contentStatistics = cachedMap.get(id);
        if (empty(contentStatistics)) {
            contentStatistics = new CmsContentStatistics(id, 0, 1, 0, contentService.getEntity(id));
            clearCache(300);
            cachedlist.add(id);
            cachedMap.put(id, contentStatistics);
        } else {
            contentStatistics.setComments(contentStatistics.getComments() + 1);
        }
        return contentStatistics;
    }

    public CmsContentStatistics scores(Integer id) {
        CmsContentStatistics contentStatistics = cachedMap.get(id);
        if (empty(contentStatistics)) {
            contentStatistics = new CmsContentStatistics(id, 0, 0, 1, contentService.getEntity(id));
            clearCache(300);
            cachedlist.add(id);
            cachedMap.put(id, contentStatistics);
        } else {
            contentStatistics.setComments(contentStatistics.getComments() + 1);
        }
        return contentStatistics;
    }

    @Override
    public void clear() {
        contentService.updateStatistics(cachedMap.values());
        cachedlist.clear();
        cachedMap.clear();
    }
}