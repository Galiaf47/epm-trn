package com.epam.trn.model;

import java.io.Serializable;
import java.util.List;

public class UsersPage implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -1588030058563583602L;
	private Integer records;
	private Integer total;
	private Integer page;
	private List<User> rows;

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public List<User> getRows() {
		return rows;
	}

	public void setRows(List<User> rows) {
		this.rows = rows;
	}
}
