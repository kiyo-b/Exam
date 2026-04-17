package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {


        School school = new School();
        school.setCd("tes"); // テスト用学校コード


        List<Subject> subject = null;
        SubjectDao subjectDao = new SubjectDao();

 
        subject = subjectDao.filter(school);


        req.setAttribute("subject", subject);

       
        req.getRequestDispatcher("subject_list.jsp").forward(req, res);
    }
}