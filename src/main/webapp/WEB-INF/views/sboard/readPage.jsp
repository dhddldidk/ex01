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
					<h3 class="box-title">ReadPage Board</h3>
				</div>
				<div class="box-body">
					<form method="get" id="f1"> 
						<input type="hidden" name="bno" value="${boardVO.bno }">
						<input type="hidden" name="page" value="${cri.page }">
						<input type="hidden" name="searchType" value="${cri.searchType }">
						<input type="hidden" name="keyword" value="${cri.keyword }">
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
					//	location.href= "${pageContext.request.contextPath}/board/listAll";
					$("#f1").attr("action","listPage");
					$("#f1").submit();//form 태그 안에 page 번호가 들어있어서 그대로 사용하면 됨
					})
					$("#modifyBtn").click(function(){ // form 태그에서 보내는거나 jQuery에서 보내는거난 같음
						$("#f1").attr("action","modifyPage");
						$("#f1").submit();
					})
					$("#deleteBtn").click(function(){
						var flag = confirm("정말 삭제하시겠습니까?");
						
						if(flag==true){
							$("#f1").attr("action","removePage");
							$("#f1").submit();
						}						
					})
				</script>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<div class="box box-success">
				<div class="box-header">
					<h3 class="box-title">ADD NEW REPLY</h3>
				</div>
				<div class="box-body">
					<label>Writer</label>
					<input class="form-control" type="text" placeholder="User ID" id="newReplyWriter">
					
					<label>Reply text</label>
					<input class="form-control" type="text" placeholder="Reply text" id="newReplyText">
				</div>
				<div class="box-footer">
					<button class="btn btn-primary" id="replyAddBtn">ADD REPLY</button> 
				</div>
			</div>
		</div>
	</div>
</section>
<script>
	$("#replyAddBtn").click(function(){
		var bnoVal=${boardVO.bno };
		var replyerVal = $("#newReplyWriter").val();
		var replytextVal = $("#newReplyText").val();
		var sendData = {bno:bnoVal, replyer:replyerVal, replytext:replytextVal};//키 : 값
		
		//Spring Controller에 
		//@requestBody를 쓸 경우 jsp 파일에 JSON.stringify, headers에 Content-type을 써줘야 함
		$.ajax({
			type:"post",
			url:"${pageContext.request.contextPath}/replies", //방법2 : "/ex02/replies" == 방법3 : "${pageContext.request.contextPath}/replies"
			data:JSON.stringify(sendData), //(보내는 타입) JSON string 으로 바꿔서 보냄
			dataType:"text",//xml, text, json형태가 들어갈 수 있음(받는 타입)
			headers:{"Content-Type":"application/json"},
			success:function(result){
				console.log(result);
				if(result=="success"){
					alert("등록되었습니다.");
					$("#newReplyWriter").val("");
					$("#newReplyText").val("");
				}
			}
		})
	})
</script>
<%@ include file="../include/footer.jsp" %>