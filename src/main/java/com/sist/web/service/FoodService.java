package com.sist.web.service;

import com.sist.web.dto.ListDataDTO;
import com.sist.web.vo.FoodVO;

public interface FoodService {
	
	public ListDataDTO<FoodVO> foodListData(int page);
	public ListDataDTO<FoodVO> foodFindData(int page, String address);
	public FoodVO foodDetailData(int fno);

}
