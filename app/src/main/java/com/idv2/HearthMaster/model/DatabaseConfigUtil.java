package com.idv2.HearthMaster.model;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by charlee on 2014-06-21.
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    public static void main(String[] args) throws IOException, SQLException {
        writeConfigFile("db_config.txt") ;
    }
}
