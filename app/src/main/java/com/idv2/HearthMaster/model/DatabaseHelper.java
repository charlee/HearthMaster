package com.idv2.HearthMaster.model;

import android.app.ActionBar;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.idv2.HearthMaster.R;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by charlee on 2014-06-21.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "cards.db";
    private static final String STOCK_DB_NAME = "cards.db";
    private static final int DATABASE_VERSION = 2;

    private Context context;

    private RuntimeExceptionDao<Card, Integer> cardDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.db_config);
        this.context = context;
    }

    /**
     * Get main database path
     * @return
     */
    public File getDatabasePath() {
        return context.getDatabasePath(DATABASE_NAME);
    }

    /**
     * Copy stock database file to app database
     * @throws IOException
     */
    private void copyDatabase() throws IOException {

        InputStream is = context.getAssets().open(STOCK_DB_NAME);
        OutputStream os = new FileOutputStream(getDatabasePath());

        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }

        os.flush();
        os.close();
        is.close();
    }

    /**
     * Check if the database file exists
     * @return
     */
    private boolean databaseExists() {
        File dbFile = getDatabasePath();
        return dbFile.exists();
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, Card.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        importData();
    }

    private void importData() {
        RuntimeExceptionDao<Card, Integer> cardDao = getCardDao();
        try {
            InputStream is = context.getAssets().open("cards.sql");
            BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder buf = new StringBuilder();
            String sql;
            while ((sql = in.readLine()) != null) {
                cardDao.executeRaw(sql);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        // TODO: think about how to upgrade existing db

    }

    public RuntimeExceptionDao<Card, Integer> getCardDao() {
        if (cardDao == null) {
            cardDao = getRuntimeExceptionDao(Card.class);
        }
        return cardDao;
    }

}
