<%--
	科目情報更新の完了画面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                ログアウト
            </h2>
            
            <label><p style="background-color:#8DC3A9; width: 100%;">ログアウトしました</p></label><br>
			<a href="Login.action">ログイン</a>
			


        </section>
    </c:param>
</c:import>
