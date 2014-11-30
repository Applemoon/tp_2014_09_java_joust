package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

class UserProfilesDao {
    private final Connection connection;

    public UserProfilesDao(Connection connection) {
        this.connection = connection;
    }

    public UserProfile get(String username) {
        final String query = "select username, password from users where username = " + username;
        TDBExecutor executor = new TDBExecutor();
        UserProfile user = null;
        try {
            user = executor.execQuery(connection, query, new TResultHandler<UserProfile>() {
                public UserProfile handle(ResultSet result) throws SQLException {
                    result.next();
                    return new UserProfile(result.getString("username"), result.getString("password"));
                }});
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean validateUser(String username, String password) {
        final String query = "select username, password from users where username = " + username +
                " and password = " + password;
        TDBExecutor executor = new TDBExecutor();
        try {
            return executor.execQuery(connection, query, new TResultHandler<Boolean>() {
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
        executor.execCreateUser(connection, username, password);
    }

    public boolean isUserExists(String username) {
        final String query = "select username from users where username = " + username;
        TDBExecutor executor = new TDBExecutor();
        try {
            return executor.execQuery(connection, query, new TResultHandler<Boolean>() {
                public Boolean handle(ResultSet result) throws SQLException {
                    return result.isBeforeFirst();
                }});
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    public int getPlayedGames(String username) {
//        return getGamesCount(username, "games_played");
//    }

//    public int getWonGames(String username) {
//        return getGamesCount(username, "games_won");
//    }

//    private int getGamesCount(String username, String gamesTypeField)
//    {
//        final String query = "select " + gamesTypeField + " from users where username = " + username;
//        TDBExecutor executor = new TDBExecutor();
//        int gamesCount = 0;
//        try {
//            gamesCount = executor.execQuery(connection, query, new TResultHandler<Integer>() {
//                public Integer handle(ResultSet result) throws SQLException {
//                    result.next();
//                    return result.getInt(1);
//                }});
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return gamesCount;
//    }

    public int getUsersCount() {
        final String query = "select count(*) from users";
        TDBExecutor executor = new TDBExecutor();
        int usersCount = 0;
        try {
            usersCount = executor.execQuery(connection, query, new TResultHandler<Integer>() {
                public Integer handle(ResultSet result) throws SQLException {
                    return result.getInt(1);
                }});
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersCount;
    }
}
