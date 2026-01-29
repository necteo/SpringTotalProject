package com.sist.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sist.web.vo.FoodVO;

// Spring AI => chatbot + 추천 지도
// <mybatis base>
@Mapper
public interface FoodMapper {
	
	@Select("SELECT fno, name, poster "
		  + "FROM menupan_food "
		  + "ORDER BY fno "
		  + "OFFSET #{start} ROWS FETCH NEXT 12 ROWS ONLY")
	public List<FoodVO> foodListData(int start);
	
	@Select("SELECT CEIL(COUNT(*) / 12) FROM menupan_food")
	public int foodTotalPage();
	
	@Select("SELECT fno, name, poster "
		  + "FROM (SELECT fno, name, poster "
		  		+ "FROM menupan_food "
		  		+ "WHERE REGEXP_LIKE(address, #{address})"
		  		+ "ORDER BY fno) "
		  + "OFFSET #{start} ROWS FETCH NEXT 12 ROWS ONLY")
	public List<FoodVO> foodFindData(@Param("start") int start, @Param("address") String address);

	@Select("SELECT CEIL(COUNT(*) / 12) FROM menupan_food "
		  + "WHERE REGEXP_LIKE(address, #{address})")
	public int foodFindTotalPage(String address);
	
	// 상세보기
	@Update("UPDATE menupan_food "
		  + "SET hit = hit + 1 "
		  + "WHERE fno = #{fno}")
	public void foodHitIncrement(int fno);
	
	@Select("SELECT fno, name, poster, address, phone, type, time, parking, score, theme, content, hit, price "
		  + "FROM menupan_food "
		  + "WHERE fno = #{fno}")
	public FoodVO foodDetailData(int fno);

}
