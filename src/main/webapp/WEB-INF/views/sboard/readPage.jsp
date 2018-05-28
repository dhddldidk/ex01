<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../include/header.jsp" %>
<style type="text/css">
	.pagination{
		width:100%;
	}
	.pagination li{
		list-style: none;
		float:left;
		padding:3px;
		border:1px solid orange;
		margin:3px;
	}
	.pagination li a{
		margin:3px;
		text-decoration: none;	
	}
</style>
<script src="${pageContext.request.contextPath }/resources/handlebars-v4.0.10.js"></script>
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
						<c:forEach var="file" items="${boardVO.files }">
								<input type="hidden" name="files" value="${file }">
						</c:forEach>
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
						<div class="form-group">
							<c:forEach var="file" items="${boardVO.files }">
								<img src="displayFile?filename=${file }">
							</c:forEach>
						</div>
				</div>
				<div class="box-footer">
					<c:if test="${login.uid==boardVO.writer}">
						<button type="submit" class="btn btn-warning" id="modifyBtn">MODIFY</button>
						<button type="submit" class="btn btn-danger" id="deleteBtn">DELETE</button>
					</c:if>
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
					
					
					//댓글이 달린 게시물은 삭제할 수 없도록 처리
					$("#deleteBtn").click(function(){
						
						var replyCnt = $("#replyCnt").html();
						
						if(replyCnt>0){
							alert("댓글이 달린 게시물을 삭제할 수 없습니다.");
							return;
						}
						
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
					<input class="form-control" type="text" id="newReplyWriter" value=${login.uid } readonly="readonly">
					
					<label>Reply text</label>
					<input class="form-control" type="text" placeholder="Reply text" id="newReplyText">
				</div>
				<div class="box-footer">
					<button class="btn btn-primary" id="replyAddBtn">ADD REPLY</button> 
				</div>
			</div>
			<ul class="timeline">
				<li class="time-label" id="repliesDiv">
					<span class="bg-green">REPLIES LIST [<span id="replyCnt">${boardVO.replycnt }</span>]</span>
				</li>
			</ul>
			<div class="text-center">
				<ul id="pagination" class="pagination pagination-sm no-margin">
				</ul>
			</div>
		</div>
	</div>
</section>
<script id="template" type="text/x-handlebars-template">
{{#each.}}
<li class="replyLi" data-rno={{rno}}>
	<i class="fa fa-comments bg-blue"></i>
	<div class="timeline-item">
		<span class="time">
			<i class="fa fa-clock-o"></i>{{prettifyDate regdate}}
		</span>
			<h3 class="timeline-header"><strong>{{rno}}</strong> -{{replyer}}</h3>
			<div class="timeline-body">{{replytext}}
		</div>
			{{#if replyer}}
		<div class="timeline-footer">
			<a class="btn btn-primary btn-xs" data-toggle="modal" data-target="#modifyModal">Modify</a>
			<a class="btn btn-danger">Delete</a>
		</div>
			{{/if}}
	</div>

</li>
{{/each}}
</script>

<!-- 날짜가 이쁘게 나오게 하기 위해+ 댓글 단 사람만 수정, 삭제 할 수 있도록 처리 -->
<script>
	//보통 매개변수 하나만 받음 근데 매개변수 처리하기 위해서 하나 더 받음
	//댓글 단 사람만 수정할 수 있도록 처리하기
	Handlebars.registerHelper("if", function(replyer, options){
		if(replyer == "${login.uid}"){
			return options.fn(this);//if문에 해당하는 div를 그대로 반환할 수 있음-수정, 삭제 버튼이 들어감
		}else{
			return '';
		}
	})


	Handlebars.registerHelper("prettifyDate", function(value){
		var dateObj = new Date(value);
		var year = dateObj.getFullYear();
		var month = dateObj.getMonth()+1;
		var date = dateObj.getDate();
		
		return year+"/"+month+"/"+date;
	})
	
	var source = $("#template").html();
	var tFunc = Handlebars.compile(source);
	var bnoVal=${boardVO.bno };
	var pageNumber=1;
	
	$("#replyAddBtn").click(function(){
	//	var bnoVal=${boardVO.bno };
		var replyerVal = $("#newReplyWriter").val();
		var replytextVal = $("#newReplyText").val();
		var sendData = {bno:bnoVal, replyer:replyerVal, replytext:replytextVal};//키 : 값
		var replycnt = Number($("#replyCnt").html())+1;
		
		
		if(replytextVal==""){
			alert("댓글을 작성해주세요!");
			return;
		}
		
		
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
					$("#newReplyText").val("");
					$("#replyCnt").html(replycnt);
				}
			}
		})
	})
	
	//댓글 리스트를 누르면 페이징과 함께 리스트가 나오도록 처리
	$("#repliesDiv").click(function(){
		
		//주소 : ex01/sboard/replies이더라도 ${pageContext.request.contextPath}/replies라고 쓰면
		//바로 replies로 가게 됨
		$.ajax({
			url:"${pageContext.request.contextPath}/replies/"+bnoVal+"/"+pageNumber,
			type:"get",
			dataType:"json",
			success:function(result){
				console.log(result);
				
				// 덧글 list
				displayList(result.list);
				
				
				// 덧글 pagination
				displayPaging(result);
			}
		
		})
	})
	
	function displayList(data){
		$(".replyLi").remove();
		var str = tFunc(data);
		$(".timeline").append(str);
	}
	
	function displayPaging(result){
		var str = "";
		if(result.pageMaker.prev){
			str += "<li><a href='#'> << </a></li>";
		}
		
		for(var i = result.pageMaker.startPage; i<=result.pageMaker.endPage; i++){
			str += "<li><a href='#'> "+i+" </a></li>";
		}
		
		if(result.pageMaker.next){
			str += "<li><a href='#'> >> </a></li>";
		}
		$(".pagination").html(str);
	}
	
	//덧글 페이징에 a태그를 눌렀을 때
	$(document).on("click", ".pagination a", function(e){
		e.preventDefault();//a태그 링크 막기
		
		//해당 페이지 정보 얻기
		pageNumber = $(this).text();//해당 a태그의 값이 들어가면 됨
		
		//getListPage(ajax를 실행시켜야 함 ) - > 버튼이 클릭되도록 함getListPage
		$("#repliesDiv").trigger("click"); // = $("#repliesDiv").click();
			
	})
	
	//댓글 삭제
	
	$(document).on("click", ".timeline-footer a:last-child", function(e){
		var rno = $(this).parents(".replyLi").attr("data-rno");
		var replycnt = Number($("#replyCnt").html())-1;
		
		$.ajax({
			type:"delete",
			url:"${pageContext.request.contextPath}/replies/"+rno,
			dataType:"text",
			success:function(result){
				console.log(result);
				if(result == "success"){
					alert("삭제 되었습니다.");
					$("#replyCnt").html(replycnt);
				}
				$("#repliesDiv").trigger("click");
				
			}
		})
		
	})
	
	//댓글 수정1 Modal에 값 넣기
	$(document).on("click", ".timeline-footer a:first-child", function(e){
		var rno = $(this).parents(".replyLi").attr("data-rno");
		var replytext = $(this).parents(".replyLi").find(".timeline-body").html();
		
		$("#rno").val(rno);
		$("#content").val(replytext);
	})
	
	//댓글 수정2 Modal에 댓글 수정하기-
	$(document).on("click",".updateComplete", function(){
			var rnoVal=$("#rno").val();
			var replytextVal = $("#content").val();
			var sendData = {replytext:replytextVal};//키 : 값
			
			$.ajax({
			type:"put",
			url:"${pageContext.request.contextPath}/replies/"+rnoVal,
			data:JSON.stringify(sendData), //(보내는 타입) JSON string 으로 바꿔서 보냄
			dataType:"text",//xml, text, json형태가 들어갈 수 있음(받는 타입)
			headers:{"Content-Type":"application/json"},
			success:function(result){
				console.log(result);
				if(result == "success"){
					alert("수정되었습니다.");
				}
				$("#repliesDiv").trigger("click");
			}
		})
	})
</script>
<!-- Modal 댓글 수정누르면 수정하는 Modal 뜨도록 -->
  <div class="modal fade" id="modifyModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
        	<!-- data-dismiss="modal" 닫게 해줌 -->
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">수정하기</h4>
        </div>
        <div class="modal-body">
          
			<div class="form-group">
				<label for="rno">번호</label>
				<input type="text" class="form-control" id="rno" readonly="readonly">
			</div>
			<div class="form-group">
				<label for="content">덧글내용</label>
				<input type="text" class="form-control" id="content">
			</div>
			<div class="form-group">
				<button type="submit" class="btn btn-primary updateComplete" data-dismiss="modal">수정하기</button>
			</div>
		
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-dismiss="modal">닫기</button>
        </div>
      </div>
      
    </div>
  </div>
<%@ include file="../include/footer.jsp" %>