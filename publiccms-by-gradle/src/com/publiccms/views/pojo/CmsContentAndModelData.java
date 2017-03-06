package com.publiccms.views.pojo;

import java.util.List;

import com.publiccms.entities.cms.CmsContent;

public class CmsContentAndModelData {
	private CmsContent cmsContent;
	private List<ExtendData> extendModelData;
	public CmsContent getCmsContent() {
		return cmsContent;
	}
	public void setCmsContent(CmsContent cmsContent) {
		this.cmsContent = cmsContent;
	}
	public List<ExtendData> getExtendModelData() {
		return extendModelData;
	}
	public void setExtendModelData(List<ExtendData> extendModelData) {
		this.extendModelData = extendModelData;
	}
	
	
}
