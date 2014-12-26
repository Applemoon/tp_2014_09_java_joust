package db;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserProfilesDao {
    private final Connection connection;

    public UserProfilesDao(Connection connection) {
        this.connection = connection;
    }

    public UserProfile get(String username) {
        final String query = "select username, password from users where username = ?";
        final String[] parameters = {username};
        TDBExecutor executor = new TDBExecutor();
        try {
            return executor.execQuery(connection, query, parameters, new TResultHandler<UserProfile>() {
                public UserProfile handle(ResultSet result) throws SQLException {
                    result.next();
                    return new UserProfile(result.getString("username"), result.getString("password"));
                }});
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean validateUser(String username, String password) {
        final String query = "select username, password from users where username = ? and password = ?";
        final String[] parameters = {username, password};
        TDBExecutor executor = new TDBExecutor();
        try {
            return executor.execQuery(connection, query, parameters, new TResultHandler<Boolean>() {
                public Boolean handle(ResultSet result) throws SQLException {
                    return result.isBeforeFirst();
                }});
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createUser(String username, String password) {
        TDBExecutor executor = new TDBExecutor();
        final String query = "insert into users (username, password) values(?,?)";
        final String[] params = {username, password};
        try {
            executor.execUpdate(connection, query, params);
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.err.println("User " + username + " is already exists");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isUserExists(String username) {
        final String query = "select username from users where username = ?";
        final String[] parameters = {username};
        TDBExecutor executor = new TDBExecutor();
        try {
            return executor.execQuery(connection, query, parameters, new TResultHandler<Boolean>() {
                public Boolean handle(ResultSet result) throws SQLException {
                    return result.isBeforeFirst();
                }});
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getUsersCount() {
        final String query = "select count(*) from users";
        TDBExecutor executor = new TDBExecutor();
        int usersCount = 0;
        try {
            usersCount = executor.execQuery(connection, query, new TResultHandler<Integer>() {
                public Integer handle(ResultSet result) throws SQLException {
                    result.next();
                    return result.getInt(1);
                }});
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersCount;
    }

    public void deleteUser(String username) {
        TDBExecutor executor = new TDBExecutor();
        final String query = "delete from users where username = ?";
        final String[] params = {username};
        try {
            executor.execUpdate(connection, query, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
