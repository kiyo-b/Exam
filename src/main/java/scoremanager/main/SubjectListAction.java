package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {


    	HttpSession session = req.getSession(); // コメントアウト
		Teacher teacher = (Teacher)session.getAttribute("user"); // コメントアウト
		School school = teacher.getSchool();


        List<Subject> subject = null;
        SubjectDao subjectDao = new SubjectDao();

 
        subject = subjectDao.filter(school);


        req.setAttribute("subject", subject);

       
        req.getRequestDispatcher("subject_list.jsp").forward(req, res);
    }
}