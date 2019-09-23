package cn.com.data.engine.entity;

import java.io.Serializable;

public class DbFieldInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String fieldName;
	private String fieldType;
	private String remarks;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
