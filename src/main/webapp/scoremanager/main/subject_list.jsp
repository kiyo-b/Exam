<%-- 
    科目一覧表示画面
    機能：科目の一覧表示
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%-- 共通テンプレート（base.jsp）を読み込み、ページ全体のレイアウトを適用 --%>
<c:import url="/common/base.jsp" >
	<%-- ページのタイトルをパラメータとして渡す --%>
	<c:param name="title">得点管理システム</c:param>

	<c:param name="scripts"></c:param>

	<%-- メインコンテンツ部分の定義 --%>
	<c:param name="content">
		<section class="me=4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目管理</h2>
			
			<%-- 新規登録画面へのリンク --%>
			<div class="my-2 text-end px-4">
				<a href="StudentCreate.action">新規登録</a> <!-- 要変更 -->
			</div>


			<%-- 科目リストの表示 --%>
			<c:choose>
				<table class="table table-hover">
					<tr>
						<th>科目コード</th>
						<th>科目名</th>
						<th></th>
						<th></th>
					</tr>
					<%-- 科目リスト(subject)を1つずつsubject変数に取り出して表示 --%>
					<c:forEach var="subject" items="${subject }">
						<tr>
							<td>${subject.cd }</td>
							<td>${subject.name }</td>
							<%-- 科目コードをパラメータとして渡し、編集画面へ遷移 --%>
							<td><a href="SubjectUpdate.action?no=${subject.cd }">変更</a></td>
							<td><a href="SubjectDeleate.action?no=${subject.cd }">削除</a></td>
						</tr>
					</c:forEach>
				</table>
			</c:choose>
		</section>
	</c:param>
</c:import>
