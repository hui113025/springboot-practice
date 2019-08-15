package com.zheng.mybatis.plugin;

import java.io.Serializable;

public class PageInfo implements Serializable{
	
	    /**
	 * 
	 */
	private static final long serialVersionUID = 2187537017740686813L;
		//pagesize ，每一页显示多少
	    private int showCount = 10;
	    //总页数
	    private int totalPage;
	    //总记录数
	    private int totalResult;
	    //当前页数
	    private int currentPage;
	    //当前显示到的ID, 在mysql limit 中就是第一个参数.
	    private int currentResult;
	    private String sortField;
	    private String order;
	    
	    private Object result;
	    private int totalCount;
	    
	    
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
		public int getShowCount() {
			return showCount;
		}
		public void setShowCount(int showCount) {
			this.showCount = showCount;
		}
		public int getTotalPage() {
			return totalPage;
		}
		public void setTotalPage(int totalPage) {
			this.totalPage = totalPage;
		}
		public int getTotalResult() {
			return totalResult;
		}
		public void setTotalResult(int totalResult) {
			this.totalResult = totalResult;
			totalCount = this.totalResult;
		}
		public int getCurrentPage() {
			return currentPage;
		}
		public void setCurrentPage(int currentPage) {
			this.currentPage = currentPage;
		}
		public int getCurrentResult() {
			if (currentPage<=0){
				currentPage =1;
			}
			return (currentPage-1) * showCount;
		}
		public void setCurrentResult(int currentResult) {
			this.currentResult = currentResult;
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
}
