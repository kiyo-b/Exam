<%--
    科目情報変更画面
    機能：科目の情報の編集
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>
	<c:param name="scripts"></c:param>
	
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
				ログイン</h2>


			<form action="Login.action" method="post">

				<div class="card mx-auto mt-4" style="max-width: 420px;">
					<div class="card-body text-center">

						<input type="text" class="form-control mb-3" name="id"
							value="${id}" placeholder="半角でご入力ください" required> 
						<input type="password" class="form-control mb-3" name="password"
							placeholder="30文字以内の半角英数字でご入力ください" required>

						<div class="form-check d-flex justify-content-center mb-3">
							<input type="checkbox" class="form-check-input me-2"
								id="chk_d_ps" name="chk_d_ps"> 
								<label class="form-check-label" for="chk_d_ps"> パスワードを表示 </label>
						</div>

						<button type="submit" class="btn btn-primary w-100">ログイン
						</button>

					</div>
				</div>

			</form>

		</section>
	</c:param>
</c:import>



