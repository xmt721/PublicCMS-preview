package com.publiccms.views.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtendField implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ExtendFieldId id;
    private String inputType;
    private boolean required;
    private String name;
    private String description;
    private String defaultValue;

    public ExtendField() {
    }

    public ExtendField(String code, String inputType, boolean required, String name, String description, String defaultValue) {
        this.id = new ExtendFieldId(code);
        this.inputType = inputType;
        this.required = required;
        this.name = name;
        this.description = description;
        this.defaultValue = defaultValue;
    }
    
    public ExtendFieldId getId() {
        return this.id;
    }

    public void setId(ExtendFieldId id) {
        this.id = id;
    }
    
    public void setCode(String code) {
        this.id = new ExtendFieldId(code);
    }
    
    public String getInputType() {
        return this.inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public boolean isRequired() {
        return this.required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

}
