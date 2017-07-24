package by.epam.movierating.dao.connectionpool;

import java.util.ResourceBundle;

/**
 * Provides getting instance of {@link ResourceBundle} values
 */
class DBResourceManager {
    private static final DBResourceManager instance = new DBResourceManager();

    private ResourceBundle resourceBundle = ResourceBundle.getBundle("db");

    private DBResourceManager() {
    }


    public static DBResourceManager getInstance() {
        return instance;
    }

    String getValue(String key) {
        return resourceBundle.getString(key);
    }
}
