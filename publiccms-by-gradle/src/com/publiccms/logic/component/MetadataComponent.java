package com.publiccms.logic.component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.publiccms.entities.sys.SysExtendField;
import com.publiccms.views.pojo.CmsPageMetadata;
import com.publiccms.views.pojo.ExtendData;
import com.sanluan.common.base.Base;
import com.sanluan.common.base.Cacheable;

@Component
public class MetadataComponent extends Base implements Cacheable {
    private ObjectMapper objectMapper = new ObjectMapper();
    public static String METADATA_FILE = "metadata.data";
    private static List<String> cachedlist = new ArrayList<String>();
    private static Map<String, Map<String, CmsPageMetadata>> cachedMap = new HashMap<String, Map<String, CmsPageMetadata>>();

    private void clearCache(int size) {
        if (size < cachedlist.size()) {
            for (int i = 0; i < size / 10; i++) {
                cachedMap.remove(cachedlist.remove(0));
            }
        }
    }

    /**
     * 获取元数据
     * 
     * @param filePath
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    public CmsPageMetadata getTemplateMetadata(String filePath) {
        File file = new File(filePath);
        if (notEmpty(file)) {
            CmsPageMetadata pageMetadata = getMetadataMap(file.getParent()).get(file.getName());
            if (notEmpty(pageMetadata)) {
                return pageMetadata;
            }
        }
        return new CmsPageMetadata();
    }

    /**
     * 获取扩展数据
     * 
     * @param filePath
     * @param extendDataList
     * @return
     */
    public Map<String, String> getExtendDataMap(String filePath, List<ExtendData> extendDataList) {
        Map<String, String> extendFieldMap = new HashMap<String, String>();
        Map<String, String> map = new HashMap<String, String>();
        List<SysExtendField> pageExtendList = getTemplateMetadata(filePath).getPageExtendList();
        if (notEmpty(pageExtendList)) {
            for (ExtendData extendData : extendDataList) {
                extendFieldMap.put(extendData.getName(), extendData.getValue());
            }
            for (SysExtendField extend : pageExtendList) {
                String value = extendFieldMap.get(extend.getCode());
                if (notEmpty(value)) {
                    map.put(extend.getCode(), value);
                } else {
                    map.put(extend.getCode(), extend.getDefaultValue());
                }
            }
        }
        return map;
    }

    /**
     * 更新元数据
     * 
     * @param filePath
     * @param map
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    public boolean updateMetadata(String filePath, CmsPageMetadata metadata) {
        File file = new File(filePath);
        if (notEmpty(file)) {
            String dirPath = file.getParent();
            Map<String, CmsPageMetadata> metadataMap = getMetadataMap(dirPath);
            metadataMap.put(file.getName(), metadata);
            try {
                saveMetadata(dirPath, metadataMap);
                return true;
            } catch (IOException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 删除元数据
     * 
     * @param filePath
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    public boolean deleteMetadata(String filePath) {
        File file = new File(filePath);
        if (notEmpty(file)) {
            String dirPath = file.getParent();
            Map<String, CmsPageMetadata> metadataMap = getMetadataMap(dirPath);
            metadataMap.remove(file.getName());
            try {
                saveMetadata(dirPath, metadataMap);
                return true;
            } catch (IOException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 获取目录元数据
     * 
     * @param dirPath
     * @return
     */
    public Map<String, CmsPageMetadata> getMetadataMap(String dirPath) {
        Map<String, CmsPageMetadata> medatadaMap = cachedMap.get(dirPath);
        if (empty(medatadaMap)) {
            File file = new File(dirPath + SEPARATOR + METADATA_FILE);
            if (notEmpty(file)) {
                try {
                    medatadaMap = objectMapper.readValue(file, new TypeReference<Map<String, CmsPageMetadata>>() {
                    });
                } catch (IOException | ClassCastException e) {
                    medatadaMap = new HashMap<String, CmsPageMetadata>();
                }
            } else {
                medatadaMap = new HashMap<String, CmsPageMetadata>();
            }
            clearCache(100);
            cachedlist.add(dirPath);
            cachedMap.put(dirPath, medatadaMap);
        }
        return medatadaMap;
    }

    /**
     * 保存元数据
     * 
     * @param dirPath
     * @param metadataMap
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    private void saveMetadata(String dirPath, Map<String, CmsPageMetadata> metadataMap) throws JsonGenerationException,
            JsonMappingException, IOException {
        File file = new File(dirPath + SEPARATOR + METADATA_FILE);
        if (empty(file)) {
            file.getParentFile().mkdirs();
        }
        objectMapper.writeValue(file, metadataMap);
        clear();
    }

    @Override
    public void clear() {
        cachedlist.clear();
        cachedMap.clear();
    }
}
