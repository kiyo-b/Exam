package scoremanager.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Student;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        School school = new School();
        school.setCd("tes");

        StudentDao studentDao = new StudentDao();
        ClassNumDao classNumDao = new ClassNumDao();

        Map<String, String> errors = new HashMap<>();

        // クラス一覧
        List<String> classList = classNumDao.filter(school);
        req.setAttribute("class_list", classList);

        // ============================
        // GET（初回表示）
        // ============================
        if (req.getMethod().equals("GET")) {

            String no = req.getParameter("no");
            Student student = studentDao.get(no);

            if (student == null) {
                req.setAttribute("error", "学生情報が見つかりません");
                req.getRequestDispatcher("student_list.jsp").forward(req, res);
                return;
            }

            // 表示用データセット
            req.setAttribute("f1", student.getEntYear());
            req.setAttribute("f2", student.getNo());
            req.setAttribute("f3", student.getName());
            req.setAttribute("f4", student.getClassNum());

            // ★ 在学中を JSP に渡す（これが無いと常に checked になる）
            req.setAttribute("attend", student.isAttend());

            req.getRequestDispatcher("student_update.jsp").forward(req, res);
            return;
        }

        // ============================
        // POST（更新処理）
        // ============================
        String entYearStr = req.getParameter("f1");
        String no = req.getParameter("f2");
        String name = req.getParameter("f3");
        String classNum = req.getParameter("f4");

        // ★ チェックボックスの値を正しく取得
        boolean attend = "true".equals(req.getParameter("isAttend"));

        // 入力保持（エラー時も表示される）
        req.setAttribute("f1", entYearStr);
        req.setAttribute("f2", no);
        req.setAttribute("f3", name);
        req.setAttribute("f4", classNum);
        req.setAttribute("attend", attend);

        // バリデーション
        if (name == null || name.isEmpty()) {
            errors.put("f3", "氏名を入力してください");
        }

        if (classNum == null || classNum.isEmpty()) {
            errors.put("f4", "クラスを選択してください");
        }

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("student_update.jsp").forward(req, res);
            return;
        }

        // Student オブジェクト作成
        Student student = new Student();
        student.setEntYear(Integer.parseInt(entYearStr));
        student.setNo(no);
        student.setName(name);
        student.setClassNum(classNum);
        student.setAttend(attend);  // ★ 修正：POST の値を反映
        student.setSchool(school);

        // 更新処理
        studentDao.update(student);

        // 完了画面へ
        req.getRequestDispatcher("student_update_done.jsp").forward(req, res);
    }
}
