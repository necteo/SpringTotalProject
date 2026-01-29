package com.sist.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sist.web.dto.ListDataDTO;
import com.sist.web.mapper.FoodMapper;
import com.sist.web.vo.FoodVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
	
	private final FoodMapper mapper;
	private static final int ROW_SIZE = 12;
	private static final int BLOCK = 10;

	@Override
	public ListDataDTO<FoodVO> foodListData(int page) {
		if (page < 1)
			page = 1;
		List<FoodVO> list = mapper.foodListData(getOffSet(page));
		int totalpage = mapper.foodTotalPage();
		ListDataDTO<FoodVO> dto = new ListDataDTO<>(list, page, totalpage);
		setPagination(dto);
		return dto;
	}

	@Override
	public ListDataDTO<FoodVO> foodFindData(int page, String address) {
		if (page < 1)
			page = 1;
		List<FoodVO> list = mapper.foodFindData(getOffSet(page), address);
		int totalpage = mapper.foodFindTotalPage(address);
		ListDataDTO<FoodVO> dto = new ListDataDTO<>(list, page, totalpage);
		setPagination(dto);
		return dto;
	}

	@Override
	public FoodVO foodDetailData(int fno) {
		mapper.foodHitIncrement(fno);
		return mapper.foodDetailData(fno);
	}
	
	private int getOffSet(int page) {
		return (page - 1) * ROW_SIZE;
	}
	
	private void setPagination(ListDataDTO<FoodVO> dto) {
		int startPage = (dto.getCurpage() - 1) / BLOCK * BLOCK + 1;
		int endPage = (dto.getCurpage() - 1) / BLOCK * BLOCK + BLOCK;
		if (endPage > dto.getTotalpage())
			endPage = dto.getTotalpage();
		dto.setStartPage(startPage);
		dto.setEndPage(endPage);
	}
	
}
