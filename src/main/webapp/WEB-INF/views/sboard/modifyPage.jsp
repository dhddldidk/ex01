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
	#previewBox{
		width:400px;
		height: 300px;
		border:3px dotted orange;
	}
	#previewBox img{
		width:100px;
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
				<form method="post" action="modifyPage" enctype="multipart/form-data"> 
						<input type="hidden" name="bno" value="${boardVO.bno }">
						<input type="hidden" name="page" value="${cri.page }">
						<input type="hidden" name="searchType" value="${cri.searchType }">
						<input type="hidden" name="keyword" value="${cri.keyword }">
						<div id="delFile">
							<!-- 
							수정하기 위해 먼저 선택된 파일들을 지우기 위해 
							엑스 버튼이 클릭 될 때마다 그 파일의 주소를 input 태그에
							같은 name을 줘서 쌓아서 controller에 넘김
							<input type="hidden" name="oldFiles" id="delFile"> -->
						</div>
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
							<label>FileUpload</label>
							<input type="file" name="newFiles" class="form-control" multiple="multiple" id="file">
						</div>
						<div class="form-group" id="previewBox">
							
						</div>
						<div class="form-group">
							
							<c:forEach var="file" items="${boardVO.files }">
								<div class="wrap">
									<img src="displayFile?filename=${file }">
									<button data-file="${file }">X</button>
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
			
			var inputObj = $('<input type="hidden" name="oldFiles" id="delFile">');
			$(inputObj).val(path);
			$("#delFile").append(inputObj);
			
		})
		
		$("#file").change(function(){//누군가 이미지를 다른걸 선택했다는 말
						$("#previewBox").empty();
		
						
						for(var i = 0; i<$(this)[0].files.length; i++){
						var reader = new FileReader();//e.target.result=reader.result
						reader.onload = function(e){											
								var imgObj = $("<img multiple='multiple'>").attr("src", e.target.result);
								$("#previewBox").append(imgObj);
							}
					
						reader.readAsDataURL($(this)[0].files[i]);
						}
						//$(this) input 파일 객체 제이쿼리 객체
						//$(this)[0] 자바스크립트 객체로 반환해줌 == var file = document.getElementById("file");
						//$(this)[0].files[0] input에서 multiple="multiple"할경우를 대비해서 
						//지금은 하나만 선택해서 [0]번째
						//만약 여러개이면 files[i] for문 돌려서 가져오면 됨
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