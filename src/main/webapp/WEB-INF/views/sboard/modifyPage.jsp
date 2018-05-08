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
					<h3 class="box-title">Modify Board</h3>
				</div>
				<form method="post" action="modifyPage"> 
						<input type="hidden" name="bno" value="${boardVO.bno }">
						<input type="hidden" name="page" value="${cri.page }">
						<input type="hidden" name="searchType" value="${cri.searchType }">
						<input type="hidden" name="keyword" value="${cri.keyword }">
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
				</div>
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