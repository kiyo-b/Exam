<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>

    <c:param name="content">
        <section class="me-4">

            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                学生情報変更
            </h2>

            <form action="${pageContext.request.contextPath}/scoremanager/main/StudentUpdate.action" method="post">

                <div class="row g-3">

                    <!-- 入学年度（表示のみ） -->
                    <div class="col-12">
                        <label class="form-label">入学年度</label>
                        <p class="form-control-plaintext">${f1}</p>
                        <input type="hidden" name="f1" value="${f1}">
                    </div>

                    <!-- 学生番号（表示のみ） -->
                    <div class="col-12">
                        <label class="form-label">学生番号</label>
                        <p class="form-control-plaintext">${f2}</p>
                        <input type="hidden" name="f2" value="${f2}">
                    </div>

                    <!-- 氏名（編集可能） -->
                    <div class="col-12">
                        <label class="form-label" for="student-f3-input">氏名</label>
                        <input type="text" class="form-control" id="student-f3-input"
                               name="f3" value="${f3}" placeholder="氏名を入力してください">
                        <c:if test="${errors.f3 != null}">
                            <div class="text-danger mt-1">${errors.f3}</div>
                        </c:if>
                    </div>

                    <!-- クラス（編集可能） -->
                    <div class="col-12">
                        <label class="form-label" for="student-f4-select">クラス</label>
                        <select class="form-select" id="student-f4-select" name="f4">
                            <option value="">選択してください</option>
                            <c:forEach var="c" items="${class_list}">
                                <option value="${c}" <c:if test="${f4 == c}">selected</c:if>>
                                    ${c}
                                </option>
                            </c:forEach>
                        </select>
                        <c:if test="${errors.f4 != null}">
                            <div class="text-danger mt-1">${errors.f4}</div>
                        </c:if>
                    </div>

                    <!-- 在学中チェックボックス -->
                    <div class="col-12">
                        <label class="form-label">在学中</label>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" name="isAttend"
                                   id="attend-check" value="true"
                                   <c:if test="${attend}">checked</c:if>>
                            <label class="form-check-label" for="attend-check">
                                在学中
                            </label>
                        </div>
                    </div>

                </div>

                <!-- ボタン -->
                <div class="mt-4">
                    <button type="submit" class="btn btn-secondary">変更</button>
                    <a href="${pageContext.request.contextPath}/scoremanager/main/StudentList.action"
                       class="btn btn-link">戻る</a>
                </div>

            </form>

        </section>
    </c:param>
</c:import>
