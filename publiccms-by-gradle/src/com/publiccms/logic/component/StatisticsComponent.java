package com.publiccms.logic.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.publiccms.logic.service.cms.CmsContentRelatedService;
import com.publiccms.logic.service.cms.CmsContentService;
import com.publiccms.logic.service.cms.CmsPageDataService;
import com.publiccms.views.pojo.CmsContentRelatedStatistics;
import com.publiccms.views.pojo.CmsContentStatistics;
import com.publiccms.views.pojo.CmsPageDataStatistics;
import com.sanluan.common.base.Base;
import com.sanluan.common.base.Cacheable;

@Component
public class StatisticsComponent extends Base implements Cacheable {
    private static List<Integer> cachedlist = new ArrayList<Integer>();
    private static Map<Integer, CmsContentStatistics> cachedMap = new HashMap<Integer, CmsContentStatistics>();
    private static List<Integer> placeCachedlist = new ArrayList<Integer>();
    private static Map<Integer, CmsPageDataStatistics> placeCachedMap = new HashMap<Integer, CmsPageDataStatistics>();
    private static List<Integer> relatedCachedlist = new ArrayList<Integer>();
    private static Map<Integer, CmsContentRelatedStatistics> relatedCachedMap = new HashMap<Integer, CmsContentRelatedStatistics>();
    @Autowired
    private CmsContentService contentService;
    @Autowired
    private CmsContentRelatedService contentRelatedService;
    @Autowired
    private CmsPageDataService pageDataService;

    private void clearCache(int size) {
        if (size < cachedlist.size()) {
            List<CmsContentStatistics> list = new ArrayList<CmsContentStatistics>();
            for (int i = 0; i < size / 10; i++) {
                list.add(cachedMap.remove(cachedlist.remove(0)));
            }
            contentService.updateStatistics(list);
        }
    }

    private void clearPlaceCache(int size) {
        if (size < placeCachedlist.size()) {
            List<CmsPageDataStatistics> list = new ArrayList<CmsPageDataStatistics>();
            for (int i = 0; i < size / 10; i++) {
                list.add(placeCachedMap.remove(placeCachedlist.remove(0)));
            }
            pageDataService.updateStatistics(list);
        }
    }

    private void clearRelatedCache(int size) {
        if (size < relatedCachedlist.size()) {
            List<CmsContentRelatedStatistics> list = new ArrayList<CmsContentRelatedStatistics>();
            for (int i = 0; i < size / 10; i++) {
                list.add(relatedCachedMap.remove(relatedCachedlist.remove(0)));
            }
            contentRelatedService.updateStatistics(list);
        }
    }

    public CmsContentRelatedStatistics relatedClicks(Integer id) {
        CmsContentRelatedStatistics contentRelatedStatistics = relatedCachedMap.get(id);
        if (empty(contentRelatedStatistics)) {
            clearRelatedCache(100);
            contentRelatedStatistics = new CmsContentRelatedStatistics(id, 1, contentRelatedService.getEntity(id));
            relatedCachedlist.add(id);
        } else {
            contentRelatedStatistics.setClicks(contentRelatedStatistics.getClicks() + 1);
        }
        relatedCachedMap.put(id, contentRelatedStatistics);
        return contentRelatedStatistics;
    }

    public CmsPageDataStatistics placeClicks(Integer id) {
        CmsPageDataStatistics pageDataStatistics = placeCachedMap.get(id);
        if (empty(pageDataStatistics)) {
            clearPlaceCache(100);
            pageDataStatistics = new CmsPageDataStatistics(id, 1, pageDataService.getEntity(id));
            placeCachedlist.add(id);
        } else {
            pageDataStatistics.setClicks(pageDataStatistics.getClicks() + 1);
        }
        placeCachedMap.put(id, pageDataStatistics);
        return pageDataStatistics;
    }

    public CmsContentStatistics clicks(Integer id) {
        CmsContentStatistics contentStatistics = cachedMap.get(id);
        if (empty(contentStatistics)) {
            clearCache(300);
            contentStatistics = new CmsContentStatistics(id, 1, 0, 0, contentService.getEntity(id));
            cachedlist.add(id);
        } else {
            contentStatistics.setClicks(contentStatistics.getClicks() + 1);
        }
        cachedMap.put(id, contentStatistics);
        return contentStatistics;
    }

    public CmsContentStatistics comments(Integer id) {
        CmsContentStatistics contentStatistics = cachedMap.get(id);
        if (empty(contentStatistics)) {
            clearCache(300);
            contentStatistics = new CmsContentStatistics(id, 0, 1, 0, contentService.getEntity(id));
            cachedlist.add(id);
        } else {
            contentStatistics.setComments(contentStatistics.getComments() + 1);
        }
        cachedMap.put(id, contentStatistics);
        return contentStatistics;
    }

    public CmsContentStatistics scores(Integer id) {
        CmsContentStatistics contentStatistics = cachedMap.get(id);
        if (empty(contentStatistics)) {
            clearCache(300);
            contentStatistics = new CmsContentStatistics(id, 0, 0, 1, contentService.getEntity(id));
            cachedlist.add(id);
        } else {
            contentStatistics.setComments(contentStatistics.getComments() + 1);
        }
        cachedMap.put(id, contentStatistics);
        return contentStatistics;
    }

    @Override
    public void clear() {
        pageDataService.updateStatistics(placeCachedMap.values());
        placeCachedlist.clear();
        placeCachedMap.clear();
        contentRelatedService.updateStatistics(relatedCachedMap.values());
        relatedCachedlist.clear();
        relatedCachedMap.clear();
        contentService.updateStatistics(cachedMap.values());
        cachedlist.clear();
        cachedMap.clear();
    }
}