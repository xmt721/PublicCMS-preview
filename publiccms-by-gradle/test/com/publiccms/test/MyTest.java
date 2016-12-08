package com.publiccms.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.publiccms.mapper.tools.ToolsMapper;

import config.ApplicationConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@Transactional
public class MyTest {
    @Autowired
    private ToolsMapper toolsMapper;

    @Test
    public void UpdateTo2017() {
        List<Map<String, Object>> modelList = toolsMapper.select("select * from cms_model");
        for (Map<String, Object> model : modelList) {
            System.out.println(model.get("id"));
        }
    }
}