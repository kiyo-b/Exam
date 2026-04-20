package scoremanager.main;

import bean.Subject;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
    	// jspのinput
    	String cd = req.getParameter("cd");
    	String name = req.getParameter("name");
    	
    	// bean
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        
        SubjectDao dao = new SubjectDao();
        dao.insert(subject);


        

       
        req.getRequestDispatcher("subject_list.jsp").forward(req, res);
    }
}