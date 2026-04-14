package tool;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("*.action")
public class FrontController extends HttpServlet {
	protected void doGet(
				HttpServletRequest req, HttpServletResponse res
	)throws ServletException, IOException{
		try{
			String path = req.getServletPath().substring(1);
			String name = path.replace(".action", "Action").replace("/", ".");
			System.out.println("★ servlet path ->" + req.getServletPath());
			System.out.println("★ class name ->" + name);

			Action action = (Action) Class.forName(name).getDeclaredConstructor().newInstance();
			action.execute(req,res);
		}catch(Exception e){
			e.printStackTrace();
			req.getRequestDispatcher("/error.jsp").forward(req, res);

			// エラー内容を表示させたいとき
		    // e.printStackTrace(); // ←これ重要！！
		    // throw new ServletException(e); // ←これ追加
		}
	}
	protected void doPost(
				HttpServletRequest req, HttpServletResponse res
			)throws ServletException, IOException{
		doGet(req, res);
	}
}
