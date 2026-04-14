package tool;
// ↑ このクラスが「tool」というパッケージに属していることを示す

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// ↑ Webアプリで使う「リクエスト」と「レスポンス」を扱うためのクラスを読み込む
//   ・HttpServletRequest  ：画面から送られてきた情報（リクエスト）
//   ・HttpServletResponse ：画面に返す情報（レスポンス）

public abstract class Action {
// ↑ Actionクラスを定義
//   abstract（抽象クラス）なので、このクラス単体では new できない
//   → 必ず「継承」して使うためのクラス

    public abstract void execute(
            HttpServletRequest req,
            HttpServletResponse res
        ) throws Exception;
    // ↑ 抽象メソッド execute
    //
    // ・画面からの要求を処理するメソッド
    // ・実際の処理内容は、このクラスを継承した子クラスで書く
    //
    // 引数の意味：
    //   req : ブラウザから送られてきたデータ（入力値、パラメータなど）
    //   res : ブラウザへ返すデータ（画面表示、リダイレクトなど）
    //
    // throws Exception：
    // ・エラーが発生する可能性があるため、例外を呼び出し元に投げる

}
