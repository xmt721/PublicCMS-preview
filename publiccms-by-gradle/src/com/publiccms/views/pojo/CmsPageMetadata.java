package com.publiccms.views.pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.publiccms.entities.sys.SysExtendField;
import com.sanluan.common.base.Base;

public class CmsPageMetadata extends Base {
    private String type;
    private String alias;
    private String publishPath;
    private Integer size;
    private boolean needLogin;
    private String acceptParamters;
    private Integer cacheTime;
    private boolean allowContribute;
    private List<SysExtendField> pageExtendList;
    private List<SysExtendField> metadataExtendList;
    private List<ExtendData> extendDataList;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPublishPath() {
        return publishPath;
    }

    public void setPublishPath(String publishPath) {
        this.publishPath = publishPath;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public boolean isNeedLogin() {
        return needLogin;
    }

    public void setNeedLogin(boolean needLogin) {
        this.needLogin = needLogin;
    }

    public List<SysExtendField> getPageExtendList() {
        return pageExtendList;
    }

    public void setPageExtendList(List<SysExtendField> pageExtendList) {
        this.pageExtendList = pageExtendList;
    }

    public boolean isAllowContribute() {
        return allowContribute;
    }

    public void setAllowContribute(boolean allowContribute) {
        this.allowContribute = allowContribute;
    }

    public List<SysExtendField> getMetadataExtendList() {
        return metadataExtendList;
    }

    public void setMetadataExtendList(List<SysExtendField> metadataExtendList) {
        this.metadataExtendList = metadataExtendList;
    }

    public Map<String, String> getExtendData() {
        Map<String, String> map = new HashMap<String, String>();
        if (Base.notEmpty(extendDataList)) {
            for (ExtendData extend : extendDataList) {
                map.put(extend.getName(), extend.getValue());
            }
        }
        return map;
    }

    public List<ExtendData> getExtendDataList() {
        return extendDataList;
    }

    public void setExtendDataList(List<ExtendData> extendDataList) {
        this.extendDataList = extendDataList;
    }

    public Integer getCacheTime() {
        return cacheTime;
    }

    public void setCacheTime(Integer cacheTime) {
        this.cacheTime = cacheTime;
    }

    public String getAcceptParamters() {
        return acceptParamters;
    }

    public void setAcceptParamters(String acceptParamters) {
        this.acceptParamters = acceptParamters;
    }
}