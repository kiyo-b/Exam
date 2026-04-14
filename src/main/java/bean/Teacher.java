package bean;
// ↑ データを入れておくためのクラス（Bean）をまとめるパッケージ

import java.io.Serializable;
// ↑ オブジェクトをセッションなどに保存できるようにするために必要

public class Teacher extends User implements Serializable {
    // ↑ Userクラスを継承
    //   → 認証済みフラグ（isAuthenticated）をそのまま使える
    // ↑ Serializable を実装
    //   → セッションに保存できるようにする

    /**
     * 教員ID
     * データベース上の主キーになることが多い
     */
    private String id;

    /**
     * パスワード
     * ログイン認証に使用する
     */
    private String password;

    /**
     * 教員名
     * 画面表示などに使用する
     */
    private String name;

    /**
     * 所属校
     * School クラスのオブジェクトとして保持する
     */
    private School school;

    /**
     * ゲッター・セッター
     * フィールドの値を安全に取得・設定するためのメソッド
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
