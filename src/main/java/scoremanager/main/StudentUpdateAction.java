package scoremanager.main;

import bean.Student;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ① URL パラメータ no を取得
        String noStr = req.getParameter("no");

        // ② null チェック（直接アクセス対策）
        if (noStr == null || noStr.isEmpty()) {
            // 学生一覧に戻す
            res.sendRedirect(req.getContextPath() + "/scoremanager/main/StudentList.action");
            return;
        }

        int no = Integer.parseInt(noStr);

        // ③ DAO で学生情報を取得
        StudentDao dao = new StudentDao();
        Student student = dao.get(noStr);

        // ⑤ JSP に渡す
        req.setAttribute("student", student);

        // ⑥ 更新画面へフォワード
        req.getRequestDispatcher("student_update.jsp").forward(req, res);
    }
}
