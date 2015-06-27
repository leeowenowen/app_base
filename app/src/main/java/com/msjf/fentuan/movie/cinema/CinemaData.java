package com.msjf.fentuan.movie.cinema;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

//Has some groups
public class CinemaData extends LinkedHashMap<String, List<CinemaDataItem>> {
	private static final long serialVersionUID = 1L;

	public void add(String group, CinemaDataItem item) {
		List<CinemaDataItem> list = get(group);
		if (list == null) {
			list = new ArrayList<CinemaDataItem>();
			put(group, list);
		}
		list.add(item);
	}
}
