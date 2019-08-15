package com.zheng.order.entity.support.jsonpage;

public class PageInfo {

	private int endRowNum;
	private int totalPageNum;
	private int totalRowNum;
	private int pageSize;
	private int startRowNum;
	private int pageNum;

    private String sortField;
    private String order;
    
    private Object result;
    private int totalCount;
    //当前页数
    private int currentPage;

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * Add constructor methods <br>
	 * WEISY-20130121
	 */
	public PageInfo() {
		super();
	}

	public PageInfo(int pageSize, int pageNum) {
		super();
		this.pageSize = pageSize;
		this.pageNum = pageNum;
	}

	public PageInfo(int endRowNum, int totalPageNum, int totalRowNum, int pageSize, int startRowNum, int pageNum) {
		super();
		this.endRowNum = endRowNum;
		this.totalPageNum = totalPageNum;
		this.totalRowNum = totalRowNum;
		this.pageSize = pageSize;
		this.startRowNum = startRowNum;
		this.pageNum = pageNum;
	}

	/**
	 * WEISY-20130121
	 */
	public int getEndRowNum() {
		// if endRowNum > 0 means have set value
		if (endRowNum <= 0) {
			endRowNum = pageSize * pageNum;
			if (endRowNum > totalRowNum) {
				endRowNum = totalRowNum;
			}
		}
		return endRowNum;
	}

	public void setEndRowNum(int endRowNum) {
		this.endRowNum = endRowNum;
	}

	public int getTotalPageNum() {
		return totalPageNum;
	}

	public void setTotalPageNum(int totalPageNum) {
		this.totalPageNum = totalPageNum;
	}

	public int getTotalRowNum() {
		return totalRowNum;
	}

	public void setTotalRowNum(int totalRowNum) {
		this.totalRowNum = totalRowNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStartRowNum() {
		// if startRowNum > 0 means had set value
		if (startRowNum <= 0) {
			startRowNum = (pageNum - 1) * pageSize + 1;
			if (startRowNum < 1) {// pageNum == 0
				startRowNum = 1;
			}
		}
		return startRowNum;
	}

	public void setStartRowNum(int startRowNum) {
		this.startRowNum = startRowNum;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}