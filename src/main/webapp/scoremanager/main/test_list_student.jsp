<%-- 
    学生一覧表示画面
    該当する学生の一覧表示
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
<<<<<<< HEAD
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績参照（学生）</h2>
=======
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績一覧（学生）</h2>
>>>>>>> branch 'master' of https://github.com/kiyo-b/Exam.git
			
			<%-- 新規登録画面へのリンク --%>
			<div class="my-2 text-end px-4">
				<a href="StudentCreate.action">新規登録</a>
			</div>

			<%-- 検索・絞り込みフォーム --%>
			<form method="get" class="px-8" >
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-2 text-center">
						科目情報
					</div>
					<%-- 入学年度の選択プルダウン --%>
					<div class="col-2">
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
				
					<div class="col-2">
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
						${f3}
						${sub.cd}
					</div>
					
					


					<%-- 絞込み実行ボタン --%>
					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button" formaction="TestListSubjectExecute.action">検索</button>
					</div>

					<%-- 入力エラー（例：クラスのみ選択して年度が未選択の場合など）を表示 --%>
					<c:if test="${param.f1 == '0' or param.f2 == '0' or param.f3 == '0'}">
					    <div class="mt-2 text-warning">
					        入学年度とクラスと科目を選択してください。
					    </div>
					</c:if>
					<div class="px-3">
						<hr class="my-3 mx-4">
					</div>

					<div class="col-2 text-center">
						学生情報
					</div>
					<%-- 学生番号のテキストボックス --%>
					<div class="col-4">
					    <label class="form-label" for="student-no-input">学生番号</label>
					    <input type="text" class="form-control" id="student-no-input" name="f4" value="${studentNo}">
					</div>
					
					<%-- 絞込み実行ボタン --%>
					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button" formaction="TestListStudentExecute.action">検索</button>
					</div>
	
					<%-- 入力エラー（例：クラスのみ選択して年度が未選択の場合など）を表示 --%>
					<c:if test="${pageContext.request.servletPath == '/scoremanager/main/TestListStudentExecute.action' and empty param.f4}">
					    <div class="mt-2 text-warning">
					        学生番号を入力してください。
					    </div>
					</c:if>
				</div>
			</form>
		</section>
		<c:if test="${not empty tests}">
		    氏名：${tests[0].student_Name}(${tests[0].student_no})
		</c:if>
		<c:choose>
			<c:when test="${empty tests}">
			    <div class="text-danger">学生情報が存在しませんでした。</div>
			</c:when>
			<c:otherwise>
				<table class="table">
				    <thead>
				        <tr>
				            <th>科目名</th>
				            <th>科目コード</th>
				            <th>回数</th>
				            <th>点数</th>
				        </tr>
				    </thead>
				    <tbody>
				        <c:forEach var="t" items="${tests}">
				            <tr>
				                <td>${t.subjectName}</td>
				                <td>${t.subjectCd}</td>
				                <td>${t.no}</td>
				                <td>${t.point}</td>
				            </tr>
				        </c:forEach>
				    </tbody>
				</table>
			</c:otherwise>
		</c:choose>
	</c:param>
</c:import>
