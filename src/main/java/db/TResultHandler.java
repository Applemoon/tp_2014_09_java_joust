package db;

import java.sql.ResultSet;
import java.sql.SQLException;

interface TResultHandler<T> {
    T handle(ResultSet resultSet) throws SQLException;
}
