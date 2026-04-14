package bean;
// ↑ このクラスが bean パッケージに所属していることを示す
//   bean は「データを入れておくクラス」をまとめるために使われる名前

public class User {

    /**
     * 認証済みフラグ
     * true  ：ログイン認証済み
     * false ：未ログイン
     */
    private boolean isAuthenticated;
    // ↑ private にすることで、直接書き換えられないようにする
    //   値の取得・変更はゲッター・セッターを通して行う

    /**
     * isAuthenticated の値を取得するメソッド（ゲッター）
     * @return 認証済みなら true、未認証なら false
     */
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    /**
     * isAuthenticated の値を設定するメソッド（セッター）
     * @param isAuthenticated 認証状態（true / false）
     */
    public void setAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }
}