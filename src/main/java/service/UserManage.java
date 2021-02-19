package service;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserManage implements iUserService{
    private String url = "jdbc:mysql://localhost:3306/userManage";
    private String user = "root";
    private String password = "ss123123";
    public UserManage() {
    }
    public Connection getConnetion(){
        Connection connection=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url,
                    user,
                    password
            );
        } catch (ClassNotFoundException e) {
            System.out.println("không có driver");
        } catch (SQLException throwables) {
            System.out.println("Không kết nối được");
        }
        System.out.println("ket noi thanh cong");

        return connection;
    }
    public List<User> findAll(){
        List<User> list=new ArrayList<>();
        Connection connection=getConnetion();
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("select * from user ");
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                int id=resultSet.getInt("id");
                String name=resultSet.getString("name");
                String email=resultSet.getString("email");
                User user=new User(id,name,email);
                list.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    @Override
    public User find(int id) {
        User user=null;
        Connection connection=getConnetion();
        try {
            CallableStatement callableStatement=connection.prepareCall( "{CALL get_user_by_id(?)}");
            callableStatement.setInt(1,id);
            ResultSet resultSet=callableStatement.executeQuery();
            while (resultSet.next()){
                String name=resultSet.getString("name");
                String email=resultSet.getString("email");
                user=new User(id,name,email);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    @Override
    public void add(User user) {
        Connection connection=getConnetion();
        try {
            CallableStatement callableStatement=connection.prepareCall("{call insert_user(?,?,?)}");
            callableStatement.setInt(1,user.getId());
            callableStatement.setString(2,user.getName());
            callableStatement.setString(3,user.getEmail());
            callableStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
