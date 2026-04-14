<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<style>
    .center-container {
        display: flex;
        justify-content: center;
        align-items: center;

    }
</style>

<c:import url="/common/base.jsp">
    <c:param name="title">
        ログイン
    </c:param>
        <c:param name="scripts"></c:param>



    <c:param name="content">
        <div class="center-container">
            <form action="LoginAction" method="get">
            　I D：
                <input type="text" name="id">
            <br>
            PASS：
                <input type="text" name="pass">
            <br>
            <br>
            　　　　　
                <input type="submit" value="ログイン">
        </form>    	
        </div>

    </c:param>
</c:import>
