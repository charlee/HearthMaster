package com.idv2.HearthMaster.model;

import android.content.Context;
import android.graphics.Color;

import com.idv2.HearthMaster.HearthMasterApp;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by charlee on 2014-06-24.
 */
public class CardManager {

    private static CardManager instance = null;

    private DatabaseHelper dbHelper = null;
    private RuntimeExceptionDao<Card, Integer> cardDao;

    private boolean initDone;

    private CardManager() {
        Context context = HearthMasterApp.getInstance().getApplicationContext();
        dbHelper = new OpenHelperManager().getHelper(context, DatabaseHelper.class);
        cardDao = dbHelper.getCardDao();
    }

    public static CardManager getInstance() {
        if (instance == null) {
            instance = new CardManager();
        }
        return instance;
    }

    /**
     * Get Card by id
     * @param cardId card id
     * @return
     */
    public Card getCard(int cardId) {
        return cardDao.queryForId(cardId);
    }

    public List<Card> getAllCards() {
        return cardDao.queryForAll();
    }

}
