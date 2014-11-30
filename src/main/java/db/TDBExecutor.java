package db;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.*;

class TDBExecutor {
    public void createTable(Connection connection) {
        final String queryCreateTable =
                "create table if not exists users (" +
                "username char(20)," +
                "password char(64)," +
                "games_played int default 0," +
                "games_won int default 0," +
                "primary key (username));";
        try {
            execUpdate(connection, queryCreateTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <T> T execQuery(Connection connection, String query, TResultHandler<T> handler)
            throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(query);
        ResultSet result = stmt.getResultSet();
        T value = handler.handle(result);
        result.close();
        stmt.close();
        return value;
    }

    void execUpdate(Connection connection, String update) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(update);
        stmt.close();
    }

    public void execCreateUser(Connection connection, String username, String password) {
        try{
            connection.setAutoCommit(false);
            String update = "insert into users (username, password) values(?,?)";
            PreparedStatement stmt = connection.prepareStatement(update);

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();

            connection.commit();
            stmt.close();
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.err.println("User " + username + " is already exists");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
