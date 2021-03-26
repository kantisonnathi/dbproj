package com.dbproj.hms.repository;

import com.dbproj.hms.model.Doctor;
import com.dbproj.hms.model.Employee;

import java.sql.*;


public class EmployeeRepository {
    /*@Query("SELECT owner FROM Owner owner left join fetch owner.pets WHERE owner.id =:id")
    @Transactional(readOnly = true)
    Owner findById(@Param("id") Integer id);*/
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/dbproj";


    static final String USER = "root";
    static final String PASS = "";

    static Connection connection = null;
    static Statement statement = null;

    public EmployeeRepository() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        statement = connection.createStatement();
    }


    public Employee findByID(Integer ID) throws SQLException {
        String sql = "select * from employee where EmpID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, ID.toString());
        ResultSet result = preparedStatement.executeQuery();
        Employee employee = null;
        while (result.next()) {
            employee = new Employee(result.getInt("EmpID"),
                    result.getString("EmpName"),
                    result.getString("username"),
                    result.getString("password"),
                    result.getString("gender").charAt(0),
                    result.getInt("salary"),
                    result.getString("phno"),
                    result.getString("email"),
                    result.getString("address"),
                    result.getString("authorization").charAt(0));
        }
        return employee;
    }


}
