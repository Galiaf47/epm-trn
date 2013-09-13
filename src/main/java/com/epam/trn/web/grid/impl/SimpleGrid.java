package com.epam.trn.web.grid.impl;

import java.util.List;

import com.epam.trn.web.grid.Grid;

public class SimpleGrid<T> implements Grid<T> {
	private String id = "id";
	private int rows;
	private int page = 1;
	private List<T> records;
	private int total;
	private boolean repeatedItems;

	public SimpleGrid(List<T> records) {
		this.records = records;
		this.total = this.rows = records.size();
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public boolean getRepeatedItems() {
		return repeatedItems;
	}

	public void setRepeatedItems(boolean repeatedItems) {
		this.repeatedItems = repeatedItems;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
