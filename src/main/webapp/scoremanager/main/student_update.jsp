<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">

            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                学生情報変更
            </h2>
            
            <form action="${pageContext.request.contextPath}/scoremanager/main/StudentUpdateExecute.action"method="post">
                <!-- 学生番号（変更不可） -->
                
                <div class="col-4">
                	<label class="form-label">学生番号</label>
                	<input type="hidden" class="form-control" name="no"value="${student.no}" readonly>
                </div>
                <!-- 入学年度（変更不可） -->
				<div class="col-4">
				    <label class="form-label">入学年度</label>
				    <input type="hidden" class="form-control" name="entYear"value="${student.entYear}" readonly>
				</div>
                <!-- 氏名 -->
                <div class="col-4">
                    <label class="form-label">氏名</label>
                    <input type="text" class="form-control" name="name"value="${student.name}">
                </div>

                <!-- クラス -->
                <div class="col-4">
                    <label class="form-label">クラス</label>
                    <select class="form-select" name="classNum">
                        <c:forEach var="num" items="${class_num_set}">
                            <option value="${num}"
                                <c:if test="${num == student.classNum}">selected</c:if>>
                                ${num}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <!-- 在学中 -->
                <div class="col-2 form-check text-center">
                    <label class="form-check-label">在学中</label>
                    <input class="form-check-input" type="checkbox"
                           name="isAttend" value="1"
                           <c:if test="${student.isAttend == 1}">checked</c:if>>
                </div>

                <!-- 変更ボタン -->
                <div class="col-2 text-center">
                    <button class="btn btn-secondary" id="update-button">変更</button>
                </div>

            </form>

            <!-- 戻る -->
            <a href="${pageContext.request.contextPath}/scoremanager/main/StudentList.action">
                戻る
            </a>

        </section>
    </c:param>
</c:import>
