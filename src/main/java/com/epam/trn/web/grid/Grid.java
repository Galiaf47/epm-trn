package com.epam.trn.web.grid;

import java.util.List;

public interface Grid<T> {

	String getId();

	int getRows();

	int getPage();

	List<T> getRecords();

	int getTotal();

	boolean getRepeatedItems();

}
