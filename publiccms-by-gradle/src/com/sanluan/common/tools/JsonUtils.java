package com.sanluan.common.tools;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanluan.common.base.Base;

/**
 * 
 * JsonUtils
 * 
 * @author kerneler
 *
 */
public final class JsonUtils extends Base {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String getString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            return null;
        }
    }
}
