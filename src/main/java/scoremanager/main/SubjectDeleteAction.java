package scoremanager.main;

import bean.School;
import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // 仮の学校情報
        School school = new School();
        school.setCd("tes");

        // JSP から受け取る値
        String cd = req.getParameter("cd");
        
        System.out.println("cd=" + cd);
        // --- 登録処理 ---
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setSchool(school);

        SubjectDao dao = new SubjectDao();
        dao.delete(subject);
        
        // 入力値を JSP に戻す
        req.setAttribute("cd", cd);

        // 完了画面へ
        req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res);
    }
}