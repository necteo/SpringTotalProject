package com.sist.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.web.dto.ListDataDTO;
import com.sist.web.service.FoodService;
import com.sist.web.vo.FoodVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FoodController {
	
	private final FoodService fService;
	
	@GetMapping("/")
	public String foodList(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
		ListDataDTO<FoodVO> dto = fService.foodListData(page);
		model.addAttribute("data", dto);
		return "list";
	}
	
	@GetMapping("/detail")
	public String foodDetail(@RequestParam("fno") int fno, Model model) {
		FoodVO vo = fService.foodDetailData(fno);
		model.addAttribute("vo", vo);
		return "detail";
	}

}
