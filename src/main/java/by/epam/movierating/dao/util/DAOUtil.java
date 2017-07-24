package by.epam.movierating.dao.util;

import by.epam.movierating.dao.connectionpool.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides util methods of dao layer
 */
public class DAOUtil {
    private static final Logger logger = Logger.getLogger(DAOUtil.class);

    /**
     * Setups language suffixes to sql query
     *
     * @param sql      primary query string
     * @param language language by which defines suffix
     * @return sql query with suffixes
     */
    public static String localizeStatement(String sql, String language) {
        StringBuilder stringBuilder = new StringBuilder(sql);
        String localizedFieldRegEx = "([a-zA-Z]*_(,|\\s|=))";
        Pattern localizedFieldPattern = Pattern.compile(localizedFieldRegEx);
        Matcher localizedFieldMatcher = localizedFieldPattern.matcher(sql);
        int position;
        int fieldAmount = 0;
        while (localizedFieldMatcher.find()) {
            position = localizedFieldMatcher.end();
            stringBuilder.insert(position - 1 + fieldAmount * 2, language.substring(0, 2));
            fieldAmount++;
        }
        return stringBuilder.toString();
    }

    /**
     * Setups field to movie's sql query
     *
     * @param sql   primary query string
     * @param field a required field
     * @return sql query with prepared field
     */
    public static String prepareMovieEditStatement(String sql, String field) {
        return sql.replaceAll("[@]", field);
    }

    /**
     * Closes resources
     *
     * @param resources resources for close
     */
    public static void close(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    if (resource instanceof Connection) {
                        ConnectionPool.getInstance().
                                freeConnection((Connection) resource);
                    } else {
                        resource.close();
                    }
                } catch (Exception e) {
                    logger.error(e);
                }
            }
        }
    }

}
