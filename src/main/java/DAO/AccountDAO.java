package DAO;

import java.sql.*;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());            
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountByUserName(String userName){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT account_id, username, password FROM account WHERE username = ?; ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountById(int account_Id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT account_id, username, password FROM account WHERE account_id = ?; ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_Id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
} 
