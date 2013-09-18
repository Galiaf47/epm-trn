package com.epam.trn.web.grid;

import java.util.List;

public interface Grid<T> {

	public Integer getRecords();//total records

	//public void setRecords(Integer records);

	public Integer getTotal();//total pages

	//public void setTotal(Integer total);

	public Integer getPage();//current page

	//public void setPage(Integer page);

	public List<T> getRows();//rows for current page

	//public void setRows(List<T> rows);

}
