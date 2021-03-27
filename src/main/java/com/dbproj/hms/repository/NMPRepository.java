package com.dbproj.hms.repository;

import com.dbproj.hms.model.NMP;

import java.sql.*;

public class NMPRepository {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/dbproj";

    static final String USER = "root";
    static final String PASS = "";

    static Connection connection = null;
    static Statement statement = null;

    public NMPRepository() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        statement = connection.createStatement();
    }

    public NMP findByID(Integer id) throws SQLException {
        String sql = "select * from non_medical_professionals where NP_id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,id.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        //id is unique - only one possible outcome
        NMP nmp = null;
        while (resultSet.next()) {
            nmp = new NMP(resultSet.getInt("NP_id"),
                    resultSet.getInt("empid"),
                    resultSet.getString("title"));
        }
        return nmp;

    }


}
