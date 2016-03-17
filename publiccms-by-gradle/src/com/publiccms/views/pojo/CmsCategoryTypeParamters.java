package com.publiccms.views.pojo;

import java.util.List;

import com.publiccms.entities.sys.SysExtendField;

public class CmsCategoryTypeParamters {
    private List<SysExtendField> categoryExtends;

    public List<SysExtendField> getCategoryExtends() {
        return categoryExtends;
    }

    public void setCategoryExtends(List<SysExtendField> categoryExtends) {
        this.categoryExtends = categoryExtends;
    }
}