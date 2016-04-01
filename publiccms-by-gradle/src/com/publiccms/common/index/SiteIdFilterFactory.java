package com.publiccms.common.index;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.TermQuery;
import org.hibernate.search.annotations.Factory;
import org.hibernate.search.filter.impl.CachingWrapperFilter;

public class SiteIdFilterFactory {
    private Integer siteId;

    @Factory
    public Filter getFilter() {
        Query query = new TermQuery(new Term("siteId", siteId.toString()));
        return new CachingWrapperFilter(new QueryWrapperFilter(query));
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }
}