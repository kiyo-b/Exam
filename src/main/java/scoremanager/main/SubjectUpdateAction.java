package scoremanager.main;

import bean.School;
import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // 仮の学校情報
        School school = new School();
        school.setCd("tes");

        // JSP から受け取る値
        String cd = req.getParameter("cd");
        String name = req.getParameter("name");
        
        SubjectDao dao = new SubjectDao();

        boolean hasError = false;

        // --- 入力チェック ---
		Subject dbSubject = dao.get(cd, school);
        // 科目コード未入力
        if (dbSubject == null) {
            req.setAttribute("cdError", "科目が存在していません");
            hasError = true;
        }
       
        // 入力値を JSP に戻す
        req.setAttribute("cd", cd);
        req.setAttribute("name", name);

        // エラーがある場合は登録画面へ戻す
        if (hasError) {
            req.getRequestDispatcher("subject_create.jsp").forward(req, res);
            return;
        }

        // --- 登録処理 ---
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(school);

        
        dao.update(subject);

        // 完了画面へ
        req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
    }
}