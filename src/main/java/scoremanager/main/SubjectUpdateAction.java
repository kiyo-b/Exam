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

//        boolean hasError = false;
//
//        // --- 入力チェック ---
//
//        // 科目コード未入力
//        if (cd == null || cd.isEmpty()) {
//            req.setAttribute("cdError", "科目コードを入力してください");
//            hasError = true;
//        }
//        // 科目コード文字数チェック
//        else if (cd.length() != 2) {
//            req.setAttribute("cdError", "科目コードは2文字で入力してください");
//            hasError = true;
//        }
//        // 科目コード重複チェック
//        else {
//            SubjectDao dao = new SubjectDao();
//            Subject subject = dao.get(cd, school);
//
//            if (subject != null) {
//                req.setAttribute("cdError", "科目コードが重複しています");
//                hasError = true;
//            }
//        }
//
//        // 科目名未入力
//        if (name == null || name.isEmpty()) {
//            req.setAttribute("nameError", "科目名を入力してください");
//            hasError = true;
//        }

        // 入力値を JSP に戻す
        req.setAttribute("cd", cd);
        req.setAttribute("name", name);

//        // エラーがある場合は登録画面へ戻す
//        if (hasError) {
//            req.getRequestDispatcher("subject_create.jsp").forward(req, res);
//            return;
//        }

        // --- 登録処理 ---
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(school);

        SubjectDao dao = new SubjectDao();
        dao.update(subject);

        // 完了画面へ
        req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
    }
}