package com.idv2.HearthMaster.model;

import android.content.Context;
import android.graphics.Color;

import com.idv2.HearthMaster.HearthMasterApp;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
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
    private RuntimeExceptionDao<Deck, Integer> deckDao;

    private boolean initDone;

    private CardManager() {
        Context context = HearthMasterApp.getInstance().getApplicationContext();
        dbHelper = new OpenHelperManager().getHelper(context, DatabaseHelper.class);
        cardDao = dbHelper.getCardDao();
        deckDao = dbHelper.getDeckDao();
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
        try {
            QueryBuilder<Card, Integer> qb = cardDao.queryBuilder();
            qb.orderBy(Card.FIELD_NAME, true);
            return qb.query();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<Card>();
        }
    }

    /**
     * Get cards by class id
     * @param classId
     * @return
     */
    public List<Card> getCardsByClass(int classId) {
        try {
            QueryBuilder<Card, Integer> qb = cardDao.queryBuilder();
            qb.where().eq(Card.FIELD_CARDCLASS, classId);
            qb.orderBy(Card.FIELD_COST, true);
            qb.orderBy(Card.FIELD_NAME, true);
            return qb.query();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<Card>();
        }

    }

    public List<Deck> getAllDecks() {
        try {
            QueryBuilder<Deck, Integer> qb = deckDao.queryBuilder();
            qb.orderBy("id", true);
            return qb.query();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<Deck>();
        }
    }

    public Deck getDeck(int deckId) {
        return deckDao.queryForId(deckId);
    }

    public void updateDeck(Deck deck) {
        deckDao.update(deck);
    }

    /**
     * Create a deck
     * @param deck created deck id
     * @return
     */
    public int createDeck(Deck deck) {
        try {
            deckDao.create(deck);
            QueryBuilder<Deck, Integer> qb = deckDao.queryBuilder();
            qb.selectRaw("MAX(id)");
            GenericRawResults<String[]> results = deckDao.queryRaw(qb.prepareStatementString());
            // TODO
            String[] result = results.getResults().get(0);
            int max = Integer.parseInt(result[0]);

            return max;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
