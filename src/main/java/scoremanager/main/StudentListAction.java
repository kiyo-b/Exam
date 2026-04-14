package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class StudentListAction extends Action {
	public void execute(
			HttpServletRequest request, HttpServletResponse response
	)throws Exception{
		System.out.println("=== execute start ===");

//		HttpSession session = request.getSession();
//		Teacher teacher = (Teacher)session.getAttribute("user");
		
		Teacher teacher = new Teacher();

		School school = new School();
		school.setCd("tes"); // ←自分のDBにあるschool_cd

		teacher.setSchool(school);
		
//		if (teacher == null) {
//		    response.sendRedirect("login.jsp");
//		    return;
//		}
		String entYearStr = request.getParameter("entYear");
		String classNum = request.getParameter("class_num");
		String isAttendStr = request.getParameter("isAttend");

		int entYear = 0;
		boolean isAttend = "on".equals(isAttendStr);

		List<Student> students = null;

		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();

		StudentDao sDao = new StudentDao();
		ClassNumDao cNumDao = new ClassNumDao();

		Map<String, String> errors = new HashMap<>();

		entYearStr = request.getParameter("f1");
		classNum = request.getParameter("f2");
		isAttendStr = request.getParameter("f3");
		
		

		// 入学年度変換
		if (entYearStr != null) {
			entYear = Integer.parseInt(entYearStr);
		}

		// 入学年度リスト作成
		List<Integer> entYearSet = new ArrayList<>();
		for (int i = year - 10; i <= year; i++) {
			entYearSet.add(i);
		}

		// クラス一覧取得
		List<String> list = cNumDao.filter(teacher.getSchool());

//		// 在学フラグ
//		if (isAttendStr != null) {
//			isAttend = true;
//		}

		// 検索条件分岐
		if (entYear != 0 && !"0".equals(classNum)) {
//		if (entYear != 0 && !classNum.equals("0")) {
			students = sDao.filter(teacher.getSchool(), entYear, classNum, isAttend);

		} else if (entYear != 0 && "0".equals(classNum)) {
//		} else if (entYear != 0 && classNum.equals("0")) {
			students = sDao.filter(teacher.getSchool(), entYear, isAttend);

		} else if (entYear == 0 && (classNum == null || "0".equals(classNum))) {
//		} else if (entYear == 0 && classNum == null || entYear == 0 && classNum.equals("0")) {
			students = sDao.filter(teacher.getSchool(), isAttend);

		} else {
			errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
			request.setAttribute("errors", errors);
			students = sDao.filter(teacher.getSchool(), isAttend);
		}

		// リクエストスコープ
		request.setAttribute("f1", entYear);
		request.setAttribute("f2", classNum);
		request.setAttribute("f3", isAttendStr);
		request.setAttribute("students", students);
		request.setAttribute("class_num_set", list);
		request.setAttribute("ent_year_set", entYearSet);

		// フォワード
		request.getRequestDispatcher("student_list.jsp").forward(request, response);
	}
}