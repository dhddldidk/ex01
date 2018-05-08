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
					<h3 class="box-title">Read Board</h3>
				</div>
				<div class="box-body">
					<form method="get" id="f1"> 
						<input type="hidden" name="bno" value="${boardVO.bno }">
					</form>
						<div class="form-group">
							<label>Title</label>
							<input type="text" name="title" class="form-control" value="${boardVO.title }" readonly="readonly">
						</div>
						<div class="form-group">
							<label>Content</label>
							<textarea rows="5" cols="30" name="content" class="form-control" readonly="readonly">${boardVO.content }</textarea>
						</div>
						<div class="form-group">
							<label>Writer</label>
							<input type="text" name="writer" class="form-control" value="${boardVO.writer }" readonly="readonly">
						</div>
				</div>
				<div class="box-footer">
					<button type="submit" class="btn btn-warning" id="modifyBtn">MODIFY</button>
					<button type="submit" class="btn btn-danger" id="deleteBtn">DELETE</button>
					<button type="submit" class="btn btn-primary" id="goListBtn">GO LIST</button>
				</div>
				<script type="text/javascript">
					$("#goListBtn").click(function(){
						location.href= "${pageContext.request.contextPath}/board/listAll";
					
					})
					$("#modifyBtn").click(function(){ // form 태그에서 보내는거나 jQuery에서 보내는거난 같음
						$("#f1").attr("action","modify");
						$("#f1").submit();
					})
					$("#deleteBtn").click(function(){
						var flag = confirm("정말 삭제하시겠습니까?");
						
						if(flag==true){
							$("#f1").attr("action","remove");
							$("#f1").submit();
						}						
					})
				</script>
			</div>
		</div>
	</div>
</section>
<%@ include file="../include/footer.jsp" %>