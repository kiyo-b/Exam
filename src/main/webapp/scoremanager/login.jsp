<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">ログイン | 得点管理システム</c:param>

    <c:param name="scripts">
        <script>
        function togglePassword() {
            const pw = document.getElementById("login-password");
            const chk = document.getElementById("chk_d_ps");
            pw.type = chk.checked ? "text" : "password";
        }
        </script>
    </c:param>

    <c:param name="content">
        <section class="me-4">

            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                ログイン
            </h2>

            <!-- ログイン枠 -->
            <div style="width:420px; margin:20px auto; background-color:#ffffff;
                        border:1px solid #ccc; box-shadow:0px 0px 8px rgba(0,0,0,0.15); padding:25px;">

                <!-- エラー -->
                <c:if test="${not empty errors}">
                    <div style="background-color:#f8d7da; color:#842029; padding:10px; margin-bottom:15px;">
                        <ul>
                            <c:forEach var="error" items="${errors}">
                                <li>${error}</li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>

                <!-- フォーム -->
                <form method="post"
                      action="<c:url value='/scoremanager/main/LoginExecute.action'/>">

                    <input type="text" name="id"
                           value="${id}"
                           placeholder="ID"
                           style="width:100%; padding:10px; margin-bottom:15px; background:#eef2f7;" required>

                    <input type="password" name="password"
                           id="login-password"
                           placeholder="パスワード"
                           style="width:100%; padding:10px; margin-bottom:15px; background:#eef2f7;" required>

                    <div style="text-align:center; margin-bottom:15px;">
                        <input type="checkbox" id="chk_d_ps" onclick="togglePassword()">
                        <label for="chk_d_ps">パスワードを表示</label>
                    </div>

                    <input type="submit" value="ログイン"
                           style="width:100%; padding:10px; background-color:#2f80ed; color:white; border:none;">
                </form>

            </div>

        </section>
    </c:param>
</c:import>
