package scoremanager.main;

import bean.School;
import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
    	School school = new School();
		school.setCd("tes");
    	// jspのinput
    	String cd = req.getParameter("cd");
    	String name = req.getParameter("name");
    	// bean
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(school);
        SubjectDao dao = new SubjectDao();
        dao.insert(subject);


        

       
        req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
    }
}