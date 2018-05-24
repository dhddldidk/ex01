<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<style type="text/css">
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
					<h3 class="box-title">Register Board</h3>
				</div>
				<div class="box-body">
					<form role="form" method="post" action="register" enctype="multipart/form-data">
						<div class="form-group">
							<label>Title</label>
							<input type="text" name="title" class="form-control" placeholder="Enter Title">
						</div>
						<div class="form-group">
							<label>Content</label>
							<textarea rows="5" cols="30" name="content" class="form-control"></textarea>
						</div>
						<div class="form-group">
							<label>Writer</label>
							<input type="text" name="writer" class="form-control" placeholder="Writer">
						</div>
						<div class="form-group">
							<label>Files</label>
							<input type="file" name="imageFiles" multiple="multiple" class="form-control" id="file">
						</div>
						<div class="form-group" id="previewBox">
							
						</div>
						<div class="form-group">
							<button type="submit" class="btn btn-primary">Submit</button>
						</div>
						
						
						<!-- 파일 선택할 때 프리뷰 하는 방법 -->
						<script type="text/javascript">
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
					</form>
				</div>
			</div>
		</div>
	</div>
</section>
<%@ include file="../include/footer.jsp" %>