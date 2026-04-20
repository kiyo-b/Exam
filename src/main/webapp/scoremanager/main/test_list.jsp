<%-- 
    学生一覧表示画面
    機能：入学年度、クラス、在学状況での絞り込みと、該当する学生の一覧表示
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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>
			
			<%-- 新規登録画面へのリンク --%>
			<div class="my-2 text-end px-4">
				<a href="StudentCreate.action">新規登録</a>
			</div>

			<%-- 検索・絞り込みフォーム --%>
			<form method="get" action="TestListSubjectAction">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					
					<%-- 入学年度の選択プルダウン --%>
					<div class="col-4">
						<label class="form-label" for="student-f1-select">入学年度</label>
						<select class="form-select" id="student-f1-select" name="f1">
							<option value="0">--------</option>
							<%-- Actionクラスから渡された ent_year_set（年度リスト）をループで回す --%>
							<c:forEach var="year" items="${ent_year_set }">
								<%-- 検索後の再表示時、選択していた年度を保持（selected）する --%>
								<option value="${year }" <c:if test="${year == f1 }">selected</c:if>>${year }</option>
							</c:forEach>
						</select>
					</div>

					<%-- クラス番号の選択プルダウン --%>
					<div class="col-4">
						<label class="form-label" for="student-f2-select">クラス</label>
						<select class="form-select" id="student-f2-select" name="f2">
							<option value="0">--------</option>
							<%-- Actionから渡された class_num_set（クラス一覧）をループで回す --%>
							<c:forEach var="class1" items="${class_num_set }">
								<%-- 検索後の再表示時、選択していたクラスを保持（selected）する --%>
								<option value="${class1 }" <c:if test="${class1 == f2 }">selected</c:if>>${class1 }</option>
							</c:forEach>
						</select>
					</div>

					<%-- 科目の選択プルダウン --%>
					<div class="col-4">
						<label class="form-label" for="student-f3-select">科目</label>
						<select class="form-select" id="student-f3-select" name="f3">
							<option value="0">--------</option>
							<%-- Actionから渡された testcount_set（クラス一覧）をループで回す --%>
							<c:forEach var="sub" items="${subject_set }">
								<%-- 検索後の再表示時、選択していたクラスを保持（selected）する --%>
								<option value="${sub.cd }" <c:if test="${sub.cd == f3 }">selected</c:if>>${sub.name }</option>
							</c:forEach>
						</select>
					</div>
					


					<%-- 絞込み実行ボタン --%>
					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button" formaction="TestListSubjectAction.action">検索</button>
					</div>

					<%-- 入力エラー（例：クラスのみ選択して年度が未選択の場合など）を表示 --%>
					<div class="mt-2 text-warning">${errors.get("f1") }</div>
				</div>

			</form>
					

			<form method="get" action="TestListStudentAction">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
		
								
				<%-- 学生番号のテキストボックス --%>
				<div class="col-4">
				    <label class="form-label" for="student-no-input">学生番号</label>
				    <input type="text" class="form-control" id="student-no-input" name="f1" value="${studentNo}">
				</div>
				
				<%-- 絞込み実行ボタン --%>
				<div class="col-2 text-center">
					<button class="btn btn-secondary" id="filter-button" >検索</button>
				</div>

				<%-- 入力エラー（例：クラスのみ選択して年度が未選択の場合など）を表示 --%>
				<div class="mt-2 text-warning">${errors.get("f1") }</div>
				
			</form>


		</section>
	</c:param>
</c:import>
