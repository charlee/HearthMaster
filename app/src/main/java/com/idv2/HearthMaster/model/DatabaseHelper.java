package com.idv2.HearthMaster.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.idv2.HearthMaster.R;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

/**
 * Created by charlee on 2014-06-21.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "cards.db";
    private static final int DATABASE_VERSION = 4;

    private Context context;

    private RuntimeExceptionDao<Card, Integer> cardDao = null;
    private RuntimeExceptionDao<Deck, Integer> deckDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.db_config);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, Card.class);
            TableUtils.createTable(connectionSource, Deck.class);
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
            String sql;
            while ((sql = in.readLine()) != null) {
                sql = sql.trim();
                if (sql.length() == 0 || sql.startsWith("--")) continue;         // skip comments
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
        try {
            // re-create Card table but keep Deck table
            TableUtils.dropTable(connectionSource, Card.class, true);
            TableUtils.createTable(connectionSource, Card.class);
            importData();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public RuntimeExceptionDao<Card, Integer> getCardDao() {
        if (cardDao == null) {
            cardDao = getRuntimeExceptionDao(Card.class);
        }
        return cardDao;
    }

    public RuntimeExceptionDao<Deck, Integer> getDeckDao() {
        if (deckDao == null) {
            deckDao = getRuntimeExceptionDao(Deck.class);
        }
        return deckDao;
    }

}
