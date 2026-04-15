package tool;
// ↑ tool パッケージに属するクラス

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
// ↑ フィルタ機能を使うためのクラス・インタフェースを読み込む

@WebFilter(urlPatterns = { "/*" })
// ↑ このフィルタを「すべてのURL」に適用する設定
//   /* は「全リクエスト」を意味する
public class EncodingFilter implements Filter {
    // ↑ Filter インタフェースを実装したクラス
    //   → フィルタとして動作する

    /**
     * doFilterメソッド
     * サーブレットやJSPが実行される前後に動く処理を書く
     */
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        // リクエストの文字コードを UTF-8 に設定
        // フォーム入力などの日本語文字化け防止
        request.setCharacterEncoding("UTF-8");

        // レスポンスの文字コードを UTF-8 に設定
        // ブラウザに UTF-8 の HTML として返す
        response.setContentType("text/html; charset=UTF-8");

        // フィルタの「前処理」
        // System.out.println("フィルタの前処理");

        // 次の処理（別のフィルタ or Servlet / Action）を実行
        chain.doFilter(request, response);

        // フィルタの「後処理」
        // System.out.println("フィルタの後処理");
    }

    // フィルタが最初に読み込まれたときに1回だけ呼ばれる
    public void init(FilterConfig filterConfig) {
    }

    // フィルタが破棄されるときに呼ばれる
    public void destroy() {
    }
}