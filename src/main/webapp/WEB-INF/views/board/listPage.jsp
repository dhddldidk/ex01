<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../include/header.jsp" %>
<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">ListAll Board</h3>
				</div>
				<div class="box-body">
					<table class="table table-bordered">
						<tr>
							<th style="width:10px;">BNO</th>
							<th>TITLE</th>
							<th>WRITER</th>
							<th>REGDATE</th>
							<th style="width:40px;">VIEWCNT</th>
						</tr>
						<c:forEach var="item" items="${list }">
							<tr>
								<td>${item.bno }</td>
								<td><a href="readPage?bno=${item.bno }&page=${pageMaker.cri.page}">${item.title }</a></td>
								<td>${item.writer }</td>
								<td><fmt:formatDate value="${item.regdate }" pattern="yyyy-MM-dd HH:mm"/></td>
								<td><span class="badge bg-red">${item.viewcnt }</span></td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="box-footer">
					<div class="text-center">
						<ul class="pagination">
							<c:if test="${pageMaker.prev }">
								<li><a href="listPage?page=${pageMaker.startPage-1 }">&laquo;</a></li>
							</c:if>
							<!-- pageMaker.startPage ~ endPage // for(int idx=startPage; idx<=endPage; idx++)-->
							<c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="idx">
								<!-- 현재 페이지 번호에 색깔 추가 class="active" -->
								<li ${pageMaker.cri.page == idx ? 'class="active"':'' }><a href="listPage?page=${idx }">${idx }</a></li>
							</c:forEach>
							<c:if test="${pageMaker.next }">
								<li><a href="listPage?page=${pageMaker.endPage+1 }">&raquo;</a></li>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<%@ include file="../include/footer.jsp" %>