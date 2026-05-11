package scoremanager.main;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
    	req.setCharacterEncoding("UTF-8");
        // 仮の学校情報
    	HttpSession session = req.getSession(); // コメントアウト
		Teacher teacher = (Teacher)session.getAttribute("user"); // コメントアウト
		School school = teacher.getSchool();

        // JSP から受け取る値
        String cd = req.getParameter("cd");
        String name = req.getParameter("name");
        
        // 入力値を JSP に戻す
        req.setAttribute("cd", cd);
        req.setAttribute("name", name);
        
        // 削除するsubject
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setSchool(school);

        SubjectDao dao = new SubjectDao();
        dao.delete(subject);
        


        // 完了画面へ
        req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res);
    }
}