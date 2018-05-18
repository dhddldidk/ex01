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
					<h3 class="box-title">Search ListPage Board</h3>
				</div>
				<div class="box-body">
					<select name="searchType" id="searchType">
						<option value="n" ${cri.searchType=='n'? 'selected="selected"':''}>==============</option>
						<option value="t" ${cri.searchType=='t'? 'selected="selected"':''}>Title</option>
						<option value="c" ${cri.searchType=='c'? 'selected="selected"':''}>Content</option>
						<option value="w" ${cri.searchType=='w'? 'selected="selected"':''}>Writer</option>
						<option value="tc" ${cri.searchType=='tc'? 'selected="selected"':''}>Title OR Content</option>
						<option value="cw" ${cri.searchType=='cw'? 'selected="selected"':''}>Content OR Writer</option>
						<option value="tcw" ${cri.searchType=='tcw'? 'selected="selected"':''}>Title OR Content OR Writer</option>
					</select>
					<input type="text" name="keyword" id="keyword" value="${cri.keyword }">
					<button id="searchBtn">Search</button>
					<button id="register">New Board</button>
					
					<script type="text/javascript">
						$("#searchBtn").click(function(){
							var searchType = $("#searchType").val();
							var keyword = $("#keyword").val();
							
							location.href="listPage?searchType="+searchType+"&keyword="+keyword;
						})
					</script>
					
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
								<td><a href="readPage?bno=${item.bno }&page=${pageMaker.cri.page}&searchType=${cri.searchType}&keyword=${cri.keyword}&flag=true">${item.title } [${item.replycnt }]</a></td>
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
								<li ${pageMaker.cri.page == idx ? 'class="active"':'' }><a href="listPage?page=${idx }&searchType=${cri.searchType}&keyword=${cri.keyword}">${idx }</a></li>
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