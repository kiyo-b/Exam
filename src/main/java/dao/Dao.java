package dao;

import java.sql.Connection;

import javax.naming.InitialContext;
import javax.sql.DataSource;

// DBに接続するための"共通のクラス"
public class Dao {
	
//	フィールド（データ・変数）
//	static：クラスで共有（newされたインスタンス全体で共有）
//	DB接続は、各テーブルでも共通だから
	static DataSource ds;
	
//	コンストラクタ（初期化メソッド）：クラス名と同じ
	
//	メソッド（処理）
	public Connection getConnection() throws Exception{
//		nullだったら（1回DBと接続したら２回目は必要ない）
		if (ds==null) {
			InitialContext ic=new InitialContext();
			ds=(DataSource)ic.lookup("java:/comp/env/jdbc/team");
		}
		return ds.getConnection();
	}
}
