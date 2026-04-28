package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.Teacher;
import dao.TeacherDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class LoginAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        /* 1. ローカル変数 */
        String id = "";
        String password = "";
        Map<String, String> errors = new HashMap<>();

        TeacherDao teacherDao = new TeacherDao();
        Teacher teacher = null;

        /* 2. リクエストパラメータ取得 */
        id = req.getParameter("id");
        password = req.getParameter("password");


        /* 3. ログイン判定 */
        teacher = teacherDao.search(id, password);

        if (teacher == null) {
            errors.put("login", "ログインに失敗しましたIDまたはパスワードが正しくありません");
            req.setAttribute("errors", errors);
            req.setAttribute("id", id);
            req.getRequestDispatcher("login.jsp").forward(req, res);
            return;
        }

        /* 4. セッションに保存 */
        HttpSession session = req.getSession();
        session.setAttribute("user", teacher);

        /* 5. 成功 → メニューへ */
        res.sendRedirect("menu.jsp");
    }
}