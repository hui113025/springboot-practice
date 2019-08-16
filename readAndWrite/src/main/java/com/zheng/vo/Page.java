package com.zheng.vo;

import java.util.List;

public class Page<T> {
	private List<T> records;
	private String totalPage;
	private String totalCount;
	private String currentPage;
	private String pageSize;
	public int startRecord = 0;
	public int endRecord = 0;

	
	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;

		totalPage = String.valueOf(Integer.parseInt(totalCount) % Integer.parseInt(pageSize) == 0 ? Integer.parseInt(totalCount) / Integer.parseInt(pageSize) : (Integer.parseInt(totalCount)
				/ Integer.parseInt(pageSize) + 1));
		startRecord = (Integer.parseInt(currentPage) - 1) * Integer.parseInt(pageSize) + 1;
		endRecord = Integer.parseInt(currentPage) * Integer.parseInt(pageSize) + 1;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

}