package com.sanluan.common.directive;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sanluan.common.base.Base;

/**
 * 
 * BaseTemplateDirective 自定义指令基类
 *
 */
public abstract class BaseDirective extends Base implements Directive {
    protected List<Map<String, Object>> parameterList = new ArrayList<Map<String, Object>>();
    protected boolean regristerParamter = true;
}