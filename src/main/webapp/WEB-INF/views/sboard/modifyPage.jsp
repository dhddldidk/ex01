<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../include/header.jsp" %>
<style>
	.wrap{
		position: relative;
		width:100px;
		height: 100px;
		border:1px solid gray;
		text-align: center;
	}
	.wrap button{
		position: absolute;
		top:0px;
		right: 0px;
		background: orange;
	}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">Modify Board</h3>
				</div>
				<form method="post" action="modifyPage"> 
						<input type="hidden" name="bno" value="${boardVO.bno }">
						<input type="hidden" name="page" value="${cri.page }">
						<input type="hidden" name="searchType" value="${cri.searchType }">
						<input type="hidden" name="keyword" value="${cri.keyword }">
						<c:forEach var="file" items="${boardVO.files }">
								<input type="hidden" name="files" value="${file }">
						</c:forEach>
				<div class="box-body">
						<div class="form-group">
							<label>Bno</label>
							<input type="text" name="bno" class="form-control" value="${boardVO.bno }" readonly="readonly">
						</div>
						<div class="form-group">
							<label>Title</label>
							<input type="text" name="title" class="form-control" value="${boardVO.title }">
						</div>
						<div class="form-group">
							<label>Content</label>
							<textarea rows="5" cols="30" name="content" class="form-control" >${boardVO.content }</textarea>
						</div>
						<div class="form-group">
							<label>Writer</label>
							<input type="text" name="writer" class="form-control" value="${boardVO.writer }" readonly="readonly">
						</div>
						<div class="form-group">
							
							<c:forEach var="file" items="${boardVO.files }">
								<div class="wrap">
									<img src="displayFile?filename=${file }">
									<button data-file="${file }" name="deleteFile">X</button>
								</div>
							</c:forEach>
						</div>
				</div>
	
	<!-- 사진에 x를 클릭했을 때 -->			
	<script type="text/javascript">
			$(".wrap button").click(function(){
			//버튼을 클릭을 했을 때 버튼이 파일 경로의 값을 들고 있을 수 있도록
			//버튼에 속성을 줄 수 있음 ex)<button data-file="${file }">X</button>
			
			var path = $(this).attr("data-file");
			$(this).parent().remove();
			$("#delFile").val(path);
		})
	</script>
				<div class="box-footer">
					<button type="submit" class="btn btn-warning">MODIFY</button>
					<button type="submit" class="btn btn-danger">CANCEL</button>
				</div>
				</form>
			</div>
		</div>
	</div>
</section>
<%@ include file="../include/footer.jsp" %>