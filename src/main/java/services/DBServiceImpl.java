package services;

import db.TDBExecutor;
import db.UserProfile;
import db.UserProfilesDao;
import interfaces.services.DBService;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBServiceImpl implements DBService {
    private final Connection connection;

    public DBServiceImpl() {
        connection = getConnection();
        createTable();
    }

    private void createTable() {
        TDBExecutor executor = new TDBExecutor();
        final String queryCreateTable =
                "create table if not exists users (" +
                        "username char(20)," +
                        "password char(64)," +
                        "games_played int default 0," +
                        "games_won int default 0," +
                        "primary key (username));";
        try {
            executor.execUpdate(connection, queryCreateTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() {
        try{
            Driver driver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
            DriverManager.registerDriver(driver);

            StringBuilder url = new StringBuilder();

            url.append("jdbc:mysql://").	//db type
                append("localhost:"). 		//host name
                append("3306/").			//port
                append("hzdb?").			//db name
                append("user=root&").		//login
                append("password=1");		//password

            System.out.println("URL: " + url);

            return DriverManager.getConnection(url.toString());
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean validateUser(String username, String password) {
        UserProfilesDao userProfilesDao = new UserProfilesDao(connection);
        return userProfilesDao.validateUser(username, password);
    }

    @Override
    public void createUser(String username, String password) {
        UserProfilesDao userProfilesDao = new UserProfilesDao(connection);
        userProfilesDao.createUser(username, password);
    }

    @Override
    public boolean isUserExists(String username) {
        UserProfilesDao userProfilesDao = new UserProfilesDao(connection);
        return userProfilesDao.isUserExists(username);
    }

    @Override
    public UserProfile getUserProfile(String username) {
        UserProfilesDao userProfilesDao = new UserProfilesDao(connection);
        return userProfilesDao.get(username);
    }

    @Override
    public int getAmountOfRegisteredUsers() {
        UserProfilesDao userProfilesDao = new UserProfilesDao(connection);
        return userProfilesDao.getUsersCount();
    }

    @Override
    public void deleteUser(String username) {
        UserProfilesDao userProfilesDao = new UserProfilesDao(connection);
        userProfilesDao.deleteUser(username);
    }
}
