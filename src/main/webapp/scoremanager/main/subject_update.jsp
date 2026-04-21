<%--
    科目情報変更画面
    機能：科目の情報の編集
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                科目情報変更
            </h2>

			<label class="form-label">科目コード</label>
			
			<form action="SubjectUpdate.action" method="post">
			
				<input type="text" class="form-label"  name="cd" value="${cd}" placeholder="科目コードを入力してください" style="width: 900px;" required><br>
			
				<label class="form-label">科目名</label><br>
			
				<input type="text" class="form-label" name="name" value="${name}" placeholder="科目名を入力してください" style="width: 900px;" required><br>
			
				<button type="submit" class="btn btn-secondary" style="background-color: blue; color: white;">変更</button>
			</form>
			<a href="SubjectList.action">戻る</a>


        </section>
    </c:param>
</c:import>



