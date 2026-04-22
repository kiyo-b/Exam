package scoremanager.main;

	import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

	public class MenuAction extends Action {

		@Override
		public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {


			// JSPへフォワード 7
			// 全てのデータを「student_list.jsp」というファイルに渡して、画面を表示させます
			req.getRequestDispatcher("/scoremanager/main/menu.jsp").forward(req, res);
		
	}

}
