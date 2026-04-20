<%-- 学生一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報登録</h2>

            <form action="${pageContext.request.contextPath}/scoremanager/main/StudentCreate.action" method="post">

                <div class="col-4">
                    <label class="form-label" for="student-f1-select">入学年度</label>
                    <select class="form-select" id="student-f1-select" name="f1">
                        <option value="">-------</option>
                        <c:forEach var="year" items="${ent_year_set}">
                            <option value="${year}" <c:if test="${year==f1}">selected</c:if>>
                                ${year}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-4">
                    <label class="form-label" for="student-f2-input">学生番号</label>
                    <input type="text" class="form-control" id="student-f2-input" name="f2" value="${f2}" placeholder="学生番号を入力してください">
                </div>

                <div class="col-4">
                    <label class="form-label" for="student-f3-input">氏名</label>
                    <input type="text" class="form-control" id="student-f3-input" name="f3" value="${f3}" placeholder="氏名を入力してください">
                </div>

                <div class="col-4">
                    <label class="form-label" for="student-f4-select">クラス</label>
                    <select class="form-select" id="student-f4-select" name="f4">
                        <option value="">選択してください</option>
                        <c:forEach var="c" items="${class_list}">
                            <option value="${c}" <c:if test="${f4==c}">selected</c:if>>
                                ${c}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-2 text-center mt-3">
                    <button type="submit" class="btn btn-secondary">登録して終了</button>
                </div>

            </form>

            <a href="${pageContext.request.contextPath}/scoremanager/main/StudentList.action">戻る</a>
        </section>
    </c:param>
</c:import>
