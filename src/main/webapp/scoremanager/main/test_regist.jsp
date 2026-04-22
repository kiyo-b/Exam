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
			<form method="get" >
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					
					
			<%-- 入学年度の選択プルダウン --%>
			<div class="row g-3 align-items-end">

			  <div class="col-2">
			    <label class="form-label">入学年度</label>
			    <select class="form-select" name="f1">
			      <option value="0">--------</option>
			      <c:forEach var="year" items="${ent_year_set}">
			        <option value="${year}" <c:if test="${year == f1}">selected</c:if>>
			          ${year}
			        </option>
			      </c:forEach>
			    </select>
			  </div>
			
			  <div class="col-2">
			    <label class="form-label">クラス</label>
			    <select class="form-select" name="f2">
			      <option value="0">--------</option>
			      <c:forEach var="num" items="${class_num_set}">
			        <option value="${num}" <c:if test="${num == f2}">selected</c:if>>
			          ${num}
			        </option>
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
			
			  <div class="col-2">
			    <label class="form-label">回数</label>
			    <select class="form-select" name="f4">
			      <option value="0">--------</option>
			      <c:forEach var="testcount" items="${testcount_set}">
			        <option value="${testcount}" <c:if test="${testcount == f4}">selected</c:if>>
			          ${testcount}
			        </option>
			      </c:forEach>
			    </select>
			  </div>


					<%-- 絞込み実行ボタン --%>
					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button" formaction="TestRegistExecute.action">検索</button>
					</div>


			</form>


		</section>
	</c:param>
</c:import>
