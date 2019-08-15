package com.zheng.order.entity.support.jsonpage;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("columnInfo")
public class ColumnInfo {
	private String id;
	
	private String header;
	
	private String headerKey;
	
	private String fieldName;
	
	private String fieldIndex;
	
	private String sortOrder;
	
	private boolean hidden;
	
	private boolean exportable;
	
	private boolean printable;
	
	private boolean moveable;
	
	private int width;
	
	private String align;

	private String belongToTable;
	
	private String belongToTableAlias;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getHeaderKey() {
		return headerKey;
	}

	public void setHeaderKey(String headerKey) {
		this.headerKey = headerKey;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldIndex() {
		return fieldIndex;
	}

	public void setFieldIndex(String fieldIndex) {
		this.fieldIndex = fieldIndex;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public boolean isExportable() {
		return exportable;
	}

	public void setExportable(boolean exportable) {
		this.exportable = exportable;
	}

	public boolean isPrintable() {
		return printable;
	}

	public void setPrintable(boolean printable) {
		this.printable = printable;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public boolean isMoveable() {
		return moveable;
	}

	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}

	public String getBelongToTable() {
		return belongToTable;
	}

	public void setBelongToTable(String belongToTable) {
		this.belongToTable = belongToTable;
	}

	public String getBelongToTableAlias() {
		return belongToTableAlias;
	}

	public void setBelongToTableAlias(String belongToTableAlias) {
		this.belongToTableAlias = belongToTableAlias;
	}

}
