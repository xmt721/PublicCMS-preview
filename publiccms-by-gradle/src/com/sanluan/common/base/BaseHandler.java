package com.sanluan.common.base;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.sanluan.common.handler.RenderHandler;

/**
 * 
 * BaseHandler 指令处理器基类
 *
 */
public abstract class BaseHandler extends Base implements RenderHandler {
    public static final String PARAMETERS_NAME = "parameters";
    public static final String PARAMETERS_CONTROLLER = "showParamters";
    public static final String PARAMETER_TYPE_STRING = "string";
    public static final String PARAMETER_TYPE_SHORT = "short";
    public static final String PARAMETER_TYPE_LONG = "long";
    public static final String PARAMETER_TYPE_DOUBLE = "double";
    public static final String PARAMETER_TYPE_LONGARRAY = "longArray";
    public static final String PARAMETER_TYPE_DATE = "date";
    public static final String PARAMETER_TYPE_BOOLEAN = "boolean";
    public static final String PARAMETER_TYPE_INTEGER = "integer";
    public static final String PARAMETER_TYPE_INTEGERARRAY = "integerArray";
    public static final String PARAMETER_TYPE_STRINGARRAY = "stringArray";
    protected Map<String, Object> map = new LinkedHashMap<String, Object>();
    private List<Map<String, Object>> parameterList;
    private boolean regristerParamter;

    public BaseHandler(List<Map<String, Object>> parameterList, boolean regristerParamter) {
        this.parameterList = parameterList;
        this.regristerParamter = regristerParamter;
    }

    protected void regristerParamter(String type, String name) {
        regristerParamter(type, name, null);
    }

    protected void regristerParamter(final String type, final String name, final Object defaultValue) {
        if (regristerParamter) {
            parameterList.add(new HashMap<String, Object>() {
                private static final long serialVersionUID = 1L;
                {
                    put("name", name);
                    put("type", type);
                    put("defaultValue", defaultValue);
                }
            });
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sanluan.common.handler.RenderHandler#put(java.lang.String,
     * java.lang.Object)
     */
    @Override
    public RenderHandler put(String key, Object value) {
        map.put(key, value);
        return this;
    }

    public int getSize() {
        return map.size();
    }

    protected abstract Integer getIntegerWithoutRegrister(String name) throws Exception;

    protected abstract String[] getStringArrayWithoutRegrister(String name) throws Exception;

    protected abstract String getStringWithoutRegrister(String name) throws Exception;

    protected abstract Boolean getBooleanWithoutRegrister(String name) throws Exception;

    /*
     * (non-Javadoc)
     * 
     * @see com.sanluan.common.handler.RenderHandler#getString(java.lang.String,
     * java.lang.String)
     */
    @Override
    public String getString(String name, String defaultValue) throws Exception {
        String result = getString(name);
        regristerParamter(PARAMETER_TYPE_STRING, name, defaultValue);
        return notEmpty(result) ? result : defaultValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sanluan.common.handler.RenderHandler#getInteger(java.lang.String,
     * int)
     */
    @Override
    public Integer getInteger(String name, int defaultValue) throws Exception {
        regristerParamter(PARAMETER_TYPE_INTEGER, name, defaultValue);
        Integer result = getIntegerWithoutRegrister(name);
        return notEmpty(result) ? result : defaultValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sanluan.common.handler.RenderHandler#getIntegerArray(java.lang.String
     * )
     */
    @Override
    public Integer[] getIntegerArray(String name) throws Exception {
        regristerParamter(PARAMETER_TYPE_INTEGERARRAY, name);
        String[] arr = getStringArrayWithoutRegrister(name);
        if (notEmpty(arr)) {
            Set<Integer> set = new TreeSet<Integer>();
            for (String s : arr) {
                set.add(Integer.valueOf(s));
            }
            int i = 0;
            Integer[] ids = new Integer[set.size()];
            for (Integer number : set) {
                ids[i++] = number;
            }
            return ids;
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sanluan.common.handler.RenderHandler#getLongArray(java.lang.String)
     */
    @Override
    public Long[] getLongArray(String name) throws Exception {
        regristerParamter(PARAMETER_TYPE_LONGARRAY, name);
        String[] arr = getStringArrayWithoutRegrister(name);
        if (notEmpty(arr)) {
            Set<Long> set = new TreeSet<Long>();
            for (String s : arr) {
                set.add(Long.valueOf(s));
            }
            int i = 0;
            Long[] ids = new Long[set.size()];
            for (Long number : set) {
                ids[i++] = number;
            }
            return ids;
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sanluan.common.handler.RenderHandler#getBoolean(java.lang.String,
     * java.lang.Boolean)
     */
    protected Boolean getBooleanWithoutRegrister(String name, Boolean defaultValue) throws Exception {
        Boolean result = getBooleanWithoutRegrister(name);
        return notEmpty(result) ? result : defaultValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sanluan.common.handler.RenderHandler#getBoolean(java.lang.String,
     * java.lang.Boolean)
     */
    @Override
    public Boolean getBoolean(String name, Boolean defaultValue) throws Exception {
        regristerParamter(PARAMETER_TYPE_BOOLEAN, name, defaultValue);
        return getBooleanWithoutRegrister(name, defaultValue);
    }
}