package tool;
// ↑ 共通処理用の tool パッケージ

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// ↑ Servlet を作るために必要なクラス群

@WebServlet(urlPatterns = { "*.action" })
// ↑ URLが「〜.action」で終わるリクエストは、すべてこのServletで処理する
public class FrontController extends HttpServlet {

    /**
     * GETリクエストを処理するメソッド
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            // リクエストされたパスを取得（例：/login.action）
            String path = req.getServletPath().substring(1);
            // ↑ substring(1) で先頭の「/」を削除
            //    → login.action

            // ファイル名をクラス名に変換
            String name = path
                    .replace(".a", "A")   // .action → Action
                    .replace('/', '.');  // パス区切りをパッケージ区切りに変換
            // 例：login.action → loginAction

            // クラス名から Action クラスを動的に生成
            Action action = (Action)
                    Class.forName(name)
                         .getDeclaredConstructor()
                         .newInstance();

            // Actionクラスの処理を実行
            action.execute(req, res);

        } catch (Exception e) {
            // エラー内容をコンソールに出力
            e.printStackTrace();

            // エラーページへ画面遷移
            req.getRequestDispatcher("/error.jsp")
               .forward(req, res);
        }
    }

    /**
     * POSTリクエストを処理するメソッド
     * 中身は doGet に任せる
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        doGet(req, res);
    }
}