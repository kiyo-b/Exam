<%--
    科目一覧表示画面
    機能：科目の一覧表示
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                科目情報登録
            </h2>
            
            <label><p style="background-color:#8DC3A9; width: 100%;">登録が完了しました</p></label><br>
			<a href="subject_create.jsp">戻る</a>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="SubjectList.action">科目一覧</a>
			


        </section>
    </c:param>
</c:import>
