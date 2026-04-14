package dao;
// ↑ このクラスが dao（Data Access Object）パッケージに属していることを示す
//   dao は「データベース操作を担当するクラス群」という意味でよく使われる

import java.sql.Connection;
// ↑ データベースとの接続を表す Connection クラス

import javax.naming.InitialContext;
import javax.sql.DataSource;
// ↑ サーバーに登録されているデータベース設定（DataSource）を取得するためのクラス

public class Dao {

    /**
     * データソース（DataSource）
     * ・データベース接続設定をまとめたもの
     * ・static にすることで全Daoクラスで共有する
     */
    static DataSource ds;

    /**
     * getConnection メソッド
     * データベースへの接続（Connection）を取得して返す
     *
     * @return データベースへの接続情報
     * @throws Exception
     */
    public Connection getConnection() throws Exception {

        // データソースがまだ取得されていない場合
        if (ds == null) {

            // InitialContext を作成
            // → サーバー（Tomcatなど）に登録されている設定を探すための入口
            InitialContext ic = new InitialContext();

            // JNDI を使ってデータソースを取得
            // "jdbc/exam" はサーバー側に設定されたデータベース名
            ds = (DataSource) ic.lookup("java:/comp/env/jdbc/team");
        }

        // データソースからデータベースへの接続を取得して返す
        return ds.getConnection();
    }
}