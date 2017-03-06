package com.sanluan.common.tools;

import java.util.List;

import com.publiccms.views.pojo.ExtendField;

public class SqlTool {
	public static String createTable(String tableName,List<ExtendField> fields){
		String sql="create table "+tableName+" (";
		sql+=" content_id bigint(20) NOT NULL, ";
		for(ExtendField field: fields){
    		String fieldName=field.getId().getCode();
    		String fieldType=field.getInputType();
    		sql+=fieldName+" ";
    		switch(fieldType){
    			case "text":
    				sql+=" varchar(255), ";
    				break;
    			case "number":
    				sql+=" float ,";
    				break;
    			default:
    				break;
    		}
    	}
		//sql=sql.substring(0, sql.lastIndexOf(","));
		sql+=" PRIMARY KEY (content_id) ";
		sql+=")";
		return sql;
	}
	public static String dropTable(String tableName){
		return "drop table "+tableName;
	}
	public static String switchModelType2DbType(String inputType){
		switch(inputType){
		case "text":
			return "varchar(255)";
		case "number":
			return "float";
		default:
			return "";
		}
	}

}
