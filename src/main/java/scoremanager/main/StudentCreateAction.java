package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentCreateAction extends Action{
	
	@Override
	public void execute(HttpServletRequest req,HttpServletResponse res) throws Exception {
		
		School school = new School();
		school.setCd("tes");
		
		String entYearStr = "";
		int no = 0;
		String name = "";
		String class_num = "";
		int entYear = 0; 
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();
		StudentDao studentDao = new StudentDao(); 
		ClassNumDao classNumDao = new ClassNumDao(); 
		Map<String, String> errors = new HashMap<>(); 
		
		entYearStr = req.getParameter("f1");
		no = Integer.parseInt(req.getParameter("f2"));
		name = req.getParameter("f3");
		class_num = req.getParameter("f4");
		
		List<Integer> entYearSet = new ArrayList<>();
		for (int i = year - 10; i <= year; i++) { // 10年前から今年まで
			entYearSet.add(i);
		}
		
		req.setAttribute("f1", entYear);
		req.setAttribute("f2", no);
		req.setAttribute("f3", name);
		req.setAttribute("f4", class_num);
		req.setAttribute("ent_year_set", entYearSet);
		// JSPへフォワード 7
		req.getRequestDispatcher("student_create.jsp").forward(req, res);


}
}