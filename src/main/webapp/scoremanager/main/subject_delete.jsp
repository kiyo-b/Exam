<%--
    科目情報変更画面
    機能：科目の情報の編集
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
				科目情報削除</h2>


			<form action="SubjectDelete.action" method="post">

				<input type="hidden" name="cd" value="${param.cd}"> <input
					type="hidden" name="name" value="${param.name}">

				<p>「${param.name}（${param.cd}）」を削除してもよろしいですか</p>

				<button type="submit" class="btn btn-secondary"
					style="background-color: red; color: white;">削除</button>
			</form>
			<a href="SubjectList.action">戻る</a>


		</section>
	</c:param>
</c:import>






