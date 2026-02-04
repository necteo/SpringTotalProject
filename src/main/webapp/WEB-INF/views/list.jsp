<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
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
h3 {
	text-align: center
}
p {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<h3>목록</h3>
			<c:forEach var="vo" items="${ data.list }">
				<div class="col-md-3">
			    <div class="thumbnail">
			      <a href="detail?fno=${ vo.fno }">
			        <img src="${ vo.poster }" style="width: 240px; height: 120px">
			        <div class="caption">
			          <p>${ vo.name }</p>
			        </div>
			      </a>
			    </div>
			  </div>
			</c:forEach>
		</div>
		<div class="row text-center" style="margin-top: 10px">
			<ul class="pagination">
				<c:if test="${ data.startPage > 1 }">
					<li><a href="?page=${ data.startPage - 1 }">&laquo;</a></li>
				</c:if>
				<c:forEach var="i" begin="${ data.startPage }" end="${ data.endPage }">
					<li ${ i == data.curpage ? 'class="active"' : '' }><a href="?page=${ i }">${ i }</a></li>
				</c:forEach>
				<c:if test="${ data.endPage < data.totalpage }">
					<li><a href="?page=${ data.endPage + 1 }">&raquo;</a></li>
				</c:if>
			</ul>
		</div>
	</div>
</body>
</html>
