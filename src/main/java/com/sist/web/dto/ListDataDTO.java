package com.sist.web.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListDataDTO<T> {
	
	private List<T> list;
	private int curpage, totalpage, startPage, endPage;
	
	public ListDataDTO(List<T> list, int curpage, int totalpage) {
		this.list = list;
		this.curpage = curpage;
		this.totalpage = totalpage;
	}

}
