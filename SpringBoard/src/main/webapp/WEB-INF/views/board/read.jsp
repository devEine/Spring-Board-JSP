<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../include/header.jsp"%>

<div class="box box-primary">
	<div class="box-header with-border">
		<h3 class="box-title">ITWILL 게시판 본문</h3>
	</div>
	<!-- 수정,삭제 시 필요한 글번호 저장하는 폼태그  -->
	<form role="form" method="post">
		<input type="hidden" name="bno" value="${vo.bno }">
	</form>
	<!-- 수정,삭제 시 필요한 글번호 저장하는 폼태그  -->

		<div class="box-body">
			<div class="form-group">
				<label for="exampleInputEmail1">제 목</label>
				
				<input type="text" class="form-control" id="exampleInputEmail1"
					value="${vo.title }" name="title" readonly>
			</div>
			
			<div class="form-group">
				<label for="exampleInputEmail1">작성자</label>
				
				<input type="text" class="form-control" id="exampleInputEmail1"
					value="${vo.writer }" name="writer" readonly>
			</div>
			
			<div class="form-group">
				<label for="exampleInputEmail1">내 용</label>
				<textarea class="form-control" rows="3" 
				    name="content" readonly>${vo.content }</textarea>
			</div>
		
			
		</div>

		<div class="box-footer">
		<!-- 글 bno 필요 -->
			<button type="submit" class="btn btn-info">수정하기</button>
			<button type="submit" class="btn btn-warning">삭제하기</button>
		<!-- 글 bno 필요 -->
		<!-- 글 bno 필요없음 -->
			<button type="submit" class="btn btn-success">목록</button>
		<!-- 글 bno 필요없음 -->
		</div>
</div>

<script type="text/javascript"> /* 제이쿼리 사용 -> 라이브러리 추가되어 있어야함 */
	$(document).ready(function(){
		//alert('제이쿼리 실행!');
		
		//글번호 정보를 포함하는 폼태그에 접근 
		var fr = $('form[role="form"]');
		//alert(fr);
		//console.log(fr);
		
		$(".btn-info").click(function(){
			alert('수정버튼 클릭');
			fr.attr("action","/board/modify");
			fr.attr("method","get");
			fr.submit();
		});
		
		$(".btn-success").click(function(){ //목록 버튼 클릭 시 실행 함수
			location.href = "/board/listAll";
		});
	});
</script>

<%@ include file="../include/footer.jsp"%>

