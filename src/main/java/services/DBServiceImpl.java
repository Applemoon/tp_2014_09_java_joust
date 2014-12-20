package services;

import db.DBSettings;
import db.TDBExecutor;
import db.UserProfile;
import db.UserProfilesDao;
import interfaces.services.DBService;
import messageSystem.Address;
import messageSystem.MessageSystem;
import messageSystem.ThreadSettings;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBServiceImpl implements DBService {
    private final Connection connection;
    private final MessageSystem messageSystem;
    private final Address address = new Address();

    public DBServiceImpl(MessageSystem messageSystem) {
        connection = getConnection();
        createTable();

        this.messageSystem = messageSystem;
        messageSystem.addService(this);
        messageSystem.getAddressService().registerDBService(this);
    }

    private void createTable() {
        TDBExecutor executor = new TDBExecutor();
        final String queryCreateTable =
                "create table if not exists users (" +
                "username char(20) not null," +
                "password char(64) not null," +
                "games_played int default 0 not null," +
                "games_won int default 0 not null," +
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

            ResourceFactory.instance().setResource(ResourceFactory.dbSettingsFilename);
            DBSettings dbSettings = (DBSettings) ResourceFactory.instance().getResource(ResourceFactory.dbSettingsFilename);
            final String url = dbSettings.getType() + "://" +
                    dbSettings.getHost() + ":" + dbSettings.getPort() + "/" +
                    dbSettings.getName() + "?user=" + dbSettings.getLogin() +
                    "&password=" + dbSettings.getPassword();

            System.out.println("URL: " + url);

            return DriverManager.getConnection(url);
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

    @Override
    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void run() {
        while (true){
            messageSystem.execForAbonent(this);
            try {
                Thread.sleep(ThreadSettings.SERVICE_SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
