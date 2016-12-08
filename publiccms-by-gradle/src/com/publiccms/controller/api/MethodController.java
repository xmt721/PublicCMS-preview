package com.publiccms.controller.api;

import static com.publiccms.controller.api.ApiController.NOT_FOUND_MAP;
import static org.springframework.util.StringUtils.uncapitalize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.publiccms.common.base.AbstractController;
import com.publiccms.logic.component.template.TemplateComponent;
import com.sanluan.common.base.BaseMethod;

import freemarker.template.TemplateModelException;

/**
 * 
 * MethodController 方法统一分发
 *
 */
@RestController
public class MethodController extends AbstractController {
    private Map<String, BaseMethod> methodMap = new HashMap<String, BaseMethod>();
    private List<Map<String, String>> methodList = new ArrayList<Map<String, String>>();

    /**
     * 接口指令统一分发
     * 
     * @param method
     * @param callback
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("method/{name}")
    public MappingJacksonValue method(@PathVariable String name, String callback, HttpServletRequest request,
            HttpServletResponse response) {
        BaseMethod method = methodMap.get(name);
        if (notEmpty(method)) {
            try {
                String[] paramters = request.getParameterValues("paramters");
                if (notEmpty(paramters)) {
                    return getMappingJacksonValue(method.exec(Arrays.asList(paramters)), callback);
                } else {
                    return getMappingJacksonValue(method.exec(null), callback);
                }
            } catch (TemplateModelException e) {
                return getMappingJacksonValue(null, callback);
            }
        } else {
            return getMappingJacksonValue(NOT_FOUND_MAP, callback);
        }

    }

    /**
     * 接口列表
     * 
     * @param callback
     * @return
     */
    @RequestMapping("methods")
    public MappingJacksonValue methods(String callback) {
        return getMappingJacksonValue(methodList, callback);
    }

    /**
     * @param methodMap
     *            接口初始化
     */
    @Autowired
    public void setActionMap(Map<String, BaseMethod> map) {
        for (Entry<String, BaseMethod> entry : map.entrySet()) {
            String methodName = uncapitalize(entry.getKey().replaceAll(templateComponent.getMethodRemoveRegex(), BLANK));
            methodMap.put(methodName, entry.getValue());
            Map<String, String> resultMap = new HashMap<String, String>();
            resultMap.put("name", methodName);
            resultMap.put("needAppToken", String.valueOf(true));
            resultMap.put("needUserToken", String.valueOf(false));
            methodList.add(resultMap);
        }
    }

    @Autowired
    private TemplateComponent templateComponent;
}
