package com.publiccms.mapper.tools;

import java.util.List;
import java.util.Map;

public interface ToolsMapper {
    List<Map<String, Object>> select(String sql);

    int insert(String sql);

    int update(String sql);

    int delete(String sql);
}