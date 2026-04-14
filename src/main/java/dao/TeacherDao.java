
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.Teacher;

public class TeacherDao extends Dao {

    public Teacher search(String login, String password)

        throws Exception {

        Teacher teacher = null;

        Connection con = getConnection();

        PreparedStatement st;

        st = con.prepareStatement(

            "select * from teacher where login=? and password=?"

        );

        st.setString(1, login);

        st.setString(2, password);

        ResultSet rs = st.executeQuery();

        while (rs.next()) {

            teacher = new Teacher();

            teacher.setId(rs.getString("id"));

            teacher.setPassword(rs.getString("password"));

            teacher.setName(rs.getString("name"));

//            teacher.setSchool(school);



        }

        st.close();

        con.close();

        return teacher;
    }

}
