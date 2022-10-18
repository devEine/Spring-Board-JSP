<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<div class="box box-primary">
<div class="box-header with-border">
<h3 class="box-title">ITWILL 게시판</h3>
</div>

<!-- action: "" (주소가 없으면)다시 자신의 주소를 호출 -->
<!-- action="/board/regist" 이것과 동일 (다시 자기 주소로 가서 post로 호출하는 것) -->
<form role="form" action="" method="post">
<div class="box-body">

<div class="form-group">
	<label for="exampleInputEmail1">제 목</label>
	<input type="text" class="form-control" id="exampleInputEmail1" placeholder="제목을 입력하세요" name="title">
</div>

<div class="form-group">
	<label for="exampleInputEmail1">작성자</label>
	<input type="text" class="form-control" id="exampleInputEmail1" placeholder="이름을 입력하세요" name="writer">
</div>

<div class="form-group">
	<label for="exampleInputEmail1">내 용</label>
	<textarea class="form-control" rows="3" placeholder="내용을 입력하세요" name="content"></textarea>
</div>

<div class="box-footer">
<button type="submit" class="btn btn-primary">글쓰기</button>
</div>

</form>
</div>

<%@ include file="../include/footer.jsp"%>