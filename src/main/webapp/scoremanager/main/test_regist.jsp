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
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>
			


			<%-- 検索・絞り込みフォーム --%>
			<form method="get" action="TestRegist.action" >
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
			</div>
			
		<%-- 回数の選択プルダウン --%>
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
					<button type="submit" class="btn btn-secondary" id="filter-button" >検索</button>
				</div>


			</form>


		</section>
		科目：${subjectName}
		<c:choose>
			<c:when test="${empty tests}">
			    <div class="text-danger"></div>
			</c:when>
			<c:otherwise>
				<form method = "get" action="TestRegistExecute.action">
					<table class="table">
					    <thead>
					        <tr>
					            <th>入学年度</th>
					            <th>クラス</th>
					            <th>学生番号</th>
					            <th>氏名</th>
					            <th>点数</th>
					        </tr>
					    </thead>
					    <tbody>
					        <c:forEach var="t" items="${tests}">
					            <tr>
					                <td>${t.entYear}</td>
					                <td>${t.class_num}</td>
    								<input type="hidden" name="class_num[]" value="${t.class_num}" />
					                <%-- 学生番号を引数にする --%>
					                <td>${t.student_no}
	    								<input type="hidden" name="student_no[]" value="${t.student_no}" /></td>
					                <td>${t.student_Name}</td>
					                <%-- 変更後の点数を引数にする --%>
					                <td><input type="text" name="point[]" value="${t.point != null ? t.point : ''}"></td>
					                <input type="hidden" name="no[]" value="${f4}" />
					                <input type="hidden" name="subject[]" value="${f3}" />
					            </tr>
					        </c:forEach>
					    </tbody>				    
					</table>
					 <%-- 絞込み実行ボタン --%>
					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button" >登録</button>
					</div>
				</form>
			</c:otherwise>
		</c:choose>
		
	</c:param>
</c:import>
