<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>

    <c:param name="content">
        <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
            学生情報登録
        </h2>

        <p>登録が完了しました。</p>

        <a href="${pageContext.request.contextPath}/StudentCreate.action">続けて登録する</a>
        <br>
        <a href="${pageContext.request.contextPath}/StudentList.action">学生一覧へ戻る</a>
    </c:param>
</c:import>
