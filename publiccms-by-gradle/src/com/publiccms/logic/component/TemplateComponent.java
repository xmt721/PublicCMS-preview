package com.publiccms.logic.component;

import static com.publiccms.common.constants.CommonConstants.DEFAULT_PAGE_BREAK_TAG;
import static com.publiccms.common.tools.ExtendUtils.getExtendMap;
import static com.publiccms.logic.component.SiteComponent.CONTEXT_SITE;
import static com.publiccms.logic.component.SiteComponent.expose;
import static com.publiccms.logic.component.SiteComponent.getFullFileName;
import static com.publiccms.logic.component.TemplateCacheComponent.CONTENT_CACHE;
import static com.publiccms.logic.service.cms.CmsPageDataService.PAGE_TYPE_STATIC;
import static com.sanluan.common.tools.FreeMarkerUtils.makeFileByFile;
import static com.sanluan.common.tools.FreeMarkerUtils.makeStringByString;
import static org.apache.commons.lang3.StringUtils.splitByWholeSeparator;
import static org.apache.commons.lang3.StringUtils.uncapitalize;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.publiccms.common.base.AbstractTaskDirective;
import com.publiccms.common.base.AbstractTemplateDirective;
import com.publiccms.entities.cms.CmsCategory;
import com.publiccms.entities.cms.CmsCategoryAttribute;
import com.publiccms.entities.cms.CmsCategoryModel;
import com.publiccms.entities.cms.CmsContent;
import com.publiccms.entities.cms.CmsContentAttribute;
import com.publiccms.entities.sys.SysSite;
import com.publiccms.logic.service.cms.CmsCategoryAttributeService;
import com.publiccms.logic.service.cms.CmsCategoryModelService;
import com.publiccms.logic.service.cms.CmsCategoryService;
import com.publiccms.logic.service.cms.CmsContentAttributeService;
import com.publiccms.logic.service.cms.CmsContentService;
import com.publiccms.logic.service.cms.CmsPageDataService;
import com.publiccms.views.pojo.CmsPageMetadata;
import com.sanluan.common.base.Base;
import com.sanluan.common.base.Cacheable;
import com.sanluan.common.handler.PageHandler;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * 
 * TemplateComponent 模板处理组件
 *
 */
public class TemplateComponent extends Base implements Cacheable {
    public static String INCLUDE_DIRECTORY = "include";

    private String directivePrefix;
    private String directiveRemoveRegex;
    private String methodRemoveRegex;
    private Configuration staticConfiguration;
    private Configuration adminConfiguration;
    private Configuration dynamicConfiguration;
    private Configuration taskConfiguration;

    @Autowired
    private CmsContentAttributeService contentAttributeService;
    @Autowired
    private CmsCategoryAttributeService categoryAttributeService;
    @Autowired
    private CmsContentService contentService;
    @Autowired
    private CmsCategoryModelService categoryModelService;
    @Autowired
    private CmsCategoryService categoryService;
    @Autowired
    private SiteComponent siteComponent;
    @Autowired
    private MetadataComponent metadataComponent;
    @Autowired
    private CmsPageDataService pageDataService;

    /**
     * 创建静态化页面
     * 
     * @param templatePath
     * @param filePath
     * @param model
     * @return
     * @throws TemplateException
     * @throws IOException
     */
    public String createStaticFile(SysSite site, String templatePath, String filePath, Integer pageIndex,
            CmsPageMetadata metadata, Map<String, Object> model) throws IOException, TemplateException {
        if (notEmpty(filePath)) {
            if (empty(model)) {
                model = new HashMap<String, Object>();
            }
            if (empty(metadata)) {
                metadata = metadataComponent.getTemplateMetadata(filePath);
            }
            expose(model, site);
            model.put("metadata", metadata);
            filePath = makeStringByString(filePath, staticConfiguration, model);
            model.put("url", site.getSitePath() + filePath);
            if (notEmpty(pageIndex) && 1 < pageIndex) {
                int index = filePath.lastIndexOf('.');
                filePath = filePath.substring(0, index) + '_' + pageIndex + filePath.substring(index, filePath.length());
            }
            makeFileByFile(templatePath, siteComponent.getStaticFilePath(site, filePath), staticConfiguration, model);
        }
        return filePath;
    }

    /**
     * 内容页面静态化
     * 
     * @param entity
     * @param category
     * @param templatePath
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public boolean createContentFile(SysSite site, CmsContent entity, CmsCategory category, CmsCategoryModel categoryModel) {
        if (notEmpty(site) && notEmpty(entity)) {
            if (empty(category)) {
                category = categoryService.getEntity(entity.getCategoryId());
            }
            if (empty(categoryModel)) {
                categoryModel = categoryModelService.getEntity(entity.getModelId(), entity.getCategoryId());
            }
            if (notEmpty(categoryModel) && notEmpty(category) && !entity.isOnlyUrl()) {
                try {
                    if (notEmpty(categoryModel.getTemplatePath())) {
                        contentService.updateStaticUrl(
                                entity.getId(),
                                site.getSitePath()
                                        + createContentFile(site, entity, category, true,
                                                getFullFileName(site, categoryModel.getTemplatePath()), null, null));
                    } else {
                        Map<String, Object> model = new HashMap<String, Object>();
                        model.put("content", entity);
                        model.put("category", category);
                        model.put(CONTEXT_SITE, site);
                        contentService
                                .updateDynamicUrl(
                                        entity.getId(),
                                        site.getDynamicPath()
                                                + makeStringByString(category.getContentPath(), staticConfiguration, model));
                    }
                    return true;
                } catch (IOException | TemplateException e) {
                    log.error(e.getMessage());
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 内容页面静态化
     * 
     * @param entity
     * @param category
     * @param templatePath
     * @param filePath
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public String createContentFile(SysSite site, CmsContent entity, CmsCategory category, boolean createMultiContentPage,
            String templatePath, String filePath, Integer pageIndex) throws IOException, TemplateException {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("content", entity);
        model.put("category", category);

        CmsContentAttribute attribute = contentAttributeService.getEntity(entity.getId());
        if (notEmpty(attribute)) {
            Map<String, String> map = getExtendMap(attribute.getData());
            map.put("text", attribute.getText());
            map.put("source", attribute.getSource());
            map.put("sourceUrl", attribute.getSourceUrl());
            map.put("wordCount", String.valueOf(attribute.getWordCount()));
            model.put("attribute", map);
        } else {
            model.put("attribute", attribute);
        }
        if (empty(filePath)) {
            filePath = category.getContentPath();
        }

        if (notEmpty(attribute) && notEmpty(attribute.getText())) {
            String[] texts = splitByWholeSeparator(attribute.getText(), DEFAULT_PAGE_BREAK_TAG);
            if (createMultiContentPage) {
                for (int i = 1; i < texts.length; i++) {
                    PageHandler page = new PageHandler(i + 1, 1, texts.length, null);
                    model.put("text", texts[i]);
                    model.put("page", page);
                    createStaticFile(site, templatePath, filePath, i + 1, null, model);
                }
                PageHandler page = new PageHandler(pageIndex, 1, texts.length, null);
                model.put("page", page);
                model.put("text", texts[page.getPageIndex() - 1]);
            } else {
                PageHandler page = new PageHandler(pageIndex, 1, texts.length, null);
                model.put("page", page);
                model.put("text", texts[page.getPageIndex() - 1]);
            }
        }
        return createStaticFile(site, templatePath, filePath, 1, null, model);
    }

    /**
     * 分类页面静态化
     * 
     * @param entity
     * @param templatePath
     * @param filePath
     * @param pageIndex
     * @param totalPage
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public boolean createCategoryFile(SysSite site, CmsCategory entity, Integer pageIndex, Integer totalPage) {
        if (notEmpty(entity.getPath())) {
            try {
                if (notEmpty(entity.getTemplatePath())) {
                    categoryService.updateStaticUrl(
                            entity.getId(),
                            site.getSitePath()
                                    + createCategoryFile(site, entity, getFullFileName(site, entity.getTemplatePath()),
                                            entity.getPath(), pageIndex, totalPage));
                } else {
                    Map<String, Object> model = new HashMap<String, Object>();
                    model.put("category", entity);
                    model.put(CONTEXT_SITE, site);
                    categoryService.updateDynamicUrl(entity.getId(),
                            site.getDynamicPath() + makeStringByString(entity.getPath(), staticConfiguration, model));
                }
            } catch (IOException | TemplateException e) {
                return false;
            }
            return true;
        } else {
            return false;
        }

    }

    /**
     * 分类页面静态化
     * 
     * @param entity
     * @param templatePath
     * @param filePath
     * @param pageIndex
     * @param totalPage
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public String createCategoryFile(SysSite site, CmsCategory entity, String templatePath, String filePath, Integer pageIndex,
            Integer totalPage) throws IOException, TemplateException {
        Map<String, Object> model = new HashMap<String, Object>();
        if (empty(pageIndex)) {
            pageIndex = 1;
        }
        model.put("category", entity);
        CmsCategoryAttribute attribute = categoryAttributeService.getEntity(entity.getId());
        if (notEmpty(attribute)) {
            Map<String, String> map = getExtendMap(attribute.getData());
            map.put("title", attribute.getTitle());
            map.put("keywords", attribute.getKeywords());
            map.put("description", attribute.getDescription());
            model.put("attribute", map);
        } else {
            model.put("attribute", attribute);
        }

        if (notEmpty(totalPage) && pageIndex + 1 <= totalPage) {
            for (int i = pageIndex + 1; i <= totalPage; i++) {
                model.put("pageIndex", i);
                createStaticFile(site, templatePath, filePath, i, null, model);
            }
        }

        model.put("pageIndex", pageIndex);
        return createStaticFile(site, templatePath, filePath, 1, null, model);
    }

    /**
     * 静态化页面片段
     * 
     * @param filePath
     * @return
     * @throws TemplateException
     * @throws IOException
     */
    public void staticPlace(SysSite site, String templatePath, CmsPageMetadata metadata) throws IOException, TemplateException {
        if (notEmpty(templatePath)) {
            int pageSize = 10;
            if (notEmpty(metadata.getSize())) {
                pageSize = metadata.getSize();
            }
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("page", pageDataService.getPage(site.getId(), null, templatePath.substring(INCLUDE_DIRECTORY.length()),
                    PAGE_TYPE_STATIC, null, null, null, getDate(), CmsPageDataService.STATUS_NORMAL, false, null, null, 1,
                    pageSize));
            createStaticFile(site, getFullFileName(site, templatePath), templatePath, null, metadata, model);
        }
    }

    @Autowired(required = false)
    private void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer,
            Map<String, AbstractTaskDirective> taskDirectiveMap, Map<String, AbstractTemplateDirective> templateDirectiveMap,
            Map<String, TemplateMethodModelEx> methodMap) throws IOException, TemplateModelException {
        Map<String, Object> freemarkerVariables = new HashMap<String, Object>();
        adminConfiguration = freeMarkerConfigurer.getConfiguration();
        log.info("Freemarker directives and methods Handler started");

        StringBuffer templateDirectives = new StringBuffer();
        for (Entry<String, AbstractTemplateDirective> entry : templateDirectiveMap.entrySet()) {
            String directiveName = directivePrefix + uncapitalize(entry.getKey().replaceAll(directiveRemoveRegex, BLANK));
            freemarkerVariables.put(directiveName, entry.getValue());
            if (0 != templateDirectives.length()) {
                templateDirectives.append(",");
            }
            templateDirectives.append(directiveName);
        }
        StringBuffer methods = new StringBuffer();
        for (Entry<String, TemplateMethodModelEx> entry : methodMap.entrySet()) {
            String methodName = uncapitalize(entry.getKey().replaceAll(methodRemoveRegex, BLANK));
            freemarkerVariables.put(methodName, entry.getValue());
            if (0 != methods.length()) {
                methods.append(",");
            }
            methods.append(methodName);
        }
        adminConfiguration.setAllSharedVariables(new SimpleHash(freemarkerVariables, adminConfiguration.getObjectWrapper()));
        log.info(templateDirectiveMap.size() + " template directives created:[" + templateDirectives.toString() + "];");
        log.info(methodMap.size() + " methods created:[" + methods.toString() + "];");

        staticConfiguration = new Configuration(Configuration.getVersion());
        File staticFilePath = new File(siteComponent.getStaticTemplateFilePath());
        staticFilePath.mkdirs();
        staticConfiguration.setDirectoryForTemplateLoading(staticFilePath);
        copyConfig(adminConfiguration, staticConfiguration);
        staticConfiguration.setAllSharedVariables(new SimpleHash(freemarkerVariables, staticConfiguration.getObjectWrapper()));

        dynamicConfiguration = new Configuration(Configuration.getVersion());
        File dynamicFilePath = new File(siteComponent.getDynamicTemplateFilePath());
        dynamicFilePath.mkdirs();
        dynamicConfiguration.setDirectoryForTemplateLoading(dynamicFilePath);
        copyConfig(adminConfiguration, dynamicConfiguration);
        Map<String, Object> dynamicFreemarkerVariables = new HashMap<String, Object>(freemarkerVariables);
        dynamicFreemarkerVariables.put(CONTENT_CACHE, new NoCacheDirective());
        dynamicConfiguration.setAllSharedVariables(new SimpleHash(dynamicFreemarkerVariables, dynamicConfiguration
                .getObjectWrapper()));

        taskConfiguration = new Configuration(Configuration.getVersion());
        File taskFilePath = new File(siteComponent.getTaskTemplateFilePath());
        taskFilePath.mkdirs();
        taskConfiguration.setDirectoryForTemplateLoading(taskFilePath);
        copyConfig(adminConfiguration, taskConfiguration);

        StringBuffer taskDirectives = new StringBuffer();
        for (Entry<String, AbstractTaskDirective> entry : taskDirectiveMap.entrySet()) {
            String directiveName = directivePrefix + uncapitalize(entry.getKey().replaceAll(directiveRemoveRegex, BLANK));
            freemarkerVariables.put(directiveName, entry.getValue());
            if (0 != taskDirectives.length()) {
                taskDirectives.append(",");
            }
            taskDirectives.append(directiveName);
        }

        taskConfiguration.setAllSharedVariables(new SimpleHash(freemarkerVariables, taskConfiguration.getObjectWrapper()));
        log.info((taskDirectiveMap.size()) + " task directives created:[" + taskDirectives.toString() + "];");
    }

    private void copyConfig(Configuration source, Configuration target) {
        target.setBooleanFormat(source.getBooleanFormat());
        target.setDateFormat(source.getDateFormat());
        target.setDefaultEncoding(source.getDefaultEncoding());
        target.setURLEscapingCharset(source.getURLEscapingCharset());
        target.setTemplateUpdateDelayMilliseconds(source.getTemplateUpdateDelayMilliseconds());
        target.setLocale(source.getLocale());
        target.setNewBuiltinClassResolver(source.getNewBuiltinClassResolver());
        target.setDateFormat(source.getDateFormat());
        target.setTimeFormat(source.getTimeFormat());
        target.setTemplateExceptionHandler(source.getTemplateExceptionHandler());
    }

    @Override
    public void clear() {
        adminConfiguration.clearTemplateCache();
        staticConfiguration.clearTemplateCache();
        dynamicConfiguration.clearTemplateCache();
        taskConfiguration.clearTemplateCache();
    }

    public Configuration getDynamicConfiguration() {
        return dynamicConfiguration;
    }

    public void setDirectivePrefix(String directivePrefix) {
        this.directivePrefix = directivePrefix;
    }

    public void setDirectiveRemoveRegex(String directiveRemoveRegex) {
        this.directiveRemoveRegex = directiveRemoveRegex;
    }

    public void setMethodRemoveRegex(String methodRemoveRegex) {
        this.methodRemoveRegex = methodRemoveRegex;
    }

    public String getDirectiveRemoveRegex() {
        return directiveRemoveRegex;
    }

    public Configuration getStaticConfiguration() {
        return staticConfiguration;
    }

    public Configuration getTaskConfiguration() {
        return taskConfiguration;
    }
}
