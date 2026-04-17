<<%-- 成績管理一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>


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
			
			  <div class="col-2">
			    <label class="form-label">科目</label>
			    <select class="form-select" style="width: 300px;" name="f3">
			      <option value="0">--------</option>
			    </select>
			  </div>
			
			  <div class="col-2">
			    <label class="form-label">回数</label>
			    <select class="form-select" name="f4">
			      <option value="0">--------</option>
			    </select>
			  </div>
			
			  <div class="col-2">
			    <button class="btn btn-secondary w-100">検索</button>
			  </div>
			</div>
			
			
		</section>
	</c:param>
</c:import>