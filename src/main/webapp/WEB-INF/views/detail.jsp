<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
.container {
	margin-top: 50px;
}
.row {
	margin: 0 auto;
	width: 960px;
}
</style>
</head>
<body>
  <div class="container">
    <div class="row">
      <table class="table">
        <tbody>
          <tr>
            <td class="text-center" rowspan="8" width="30%">
              <img src="${ vo.poster }" style="width: 270px; height: 300px">
            </td>
            <td colspan="2">
              <h3>${ vo.name }&nbsp;<span style="color: orange">${ vo.score }</span></h3>
            </td>
          </tr>
          <tr>
            <td width="15%" style="color: gray">주소</td>
            <td width="60%">${ vo.address }</td>
          </tr>
          <tr>
            <td width="15%" style="color: gray">전화</td>
            <td width="60%">${ vo.phone }</td>
          </tr>
          <tr>
            <td width="15%" style="color: gray">음식종류</td>
            <td width="60%">${ vo.type }</td>
          </tr>
          <tr>
            <td width="15%" style="color: gray">가격대</td>
            <td width="60%">${ vo.price }</td>
          </tr>
          <tr>
            <td width="15%" style="color: gray">주차</td>
            <td width="60%">${ vo.parking }</td>
          </tr>
          <tr>
            <td width="15%" style="color: gray">테마</td>
            <td width="60%">${ vo.theme }</td>
          </tr>
        </tbody>
      </table>
      <table class="table">
        <tbody>
          <tr>
            <td>${ vo.content }</td>
          </tr>
          <tr>
            <td class="text-right">
            	<a href="/find" class="btn btn-xs btn-primary">맛집검색</a>
              <a href="javascript:history.back()" class="btn btn-xs btn-primary">목록</a>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</body>
</html>