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

    private Map<Integer, CardClass> cardClassMap;
    private Map<Integer, CardSet> cardSetMap;
    private Map<Integer, CardType> cardTypeMap;
    private Map<Integer, Quality> qualityMap;
    private Map<Integer, Race> raceMap;

    private CardManager() {
        Context context = HearthMasterApp.getInstance().getApplicationContext();
        dbHelper = new OpenHelperManager().getHelper(context, DatabaseHelper.class);
        cardDao = dbHelper.getCardDao();

        init();
    }

    /**
     * Init, preload all auxiliary classes
     */
    private void init() {

        // preload CardClass
        List<CardClass> cardClasses = dbHelper.getCardClassDao().queryForAll();
        cardClassMap = new HashMap<Integer, CardClass>();
        for (CardClass cardClass: cardClasses) {
            cardClassMap.put(cardClass.id, cardClass);
        }

        // preload CardSet
        List<CardSet> cardSets = dbHelper.getCardSetDao().queryForAll();
        cardSetMap = new HashMap<Integer, CardSet>();
        for (CardSet cardSet: cardSets) {
            cardSetMap.put(cardSet.id, cardSet);
        }

        // preload CardType
        List<CardType> cardTypes = dbHelper.getCardTypeDao().queryForAll();
        cardTypeMap = new HashMap<Integer, CardType>();
        for (CardType cardType: cardTypes) {
            cardTypeMap.put(cardType.id, cardType);
        }

        // preload Quality
        List<Quality> qualities = dbHelper.getQualityDao().queryForAll();
        qualityMap = new HashMap<Integer, Quality>();
        for (Quality quality: qualities) {
            qualityMap.put(quality.id, quality);
        }

        // preload Race
        List<Race> races = dbHelper.getRaceDao().queryForAll();
        raceMap = new HashMap<Integer, Race>();
        for (Race race: races) {
            raceMap.put(race.id, race);
        }

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

    /**
     * Get card race name
     * @param id card race id
     * @return
     */
    public String getRace(int id) {
        return raceMap.get(id).name;
    }

    /**
     * Get card quality name
     * @param id card quality id
     * @return
     */
    public String getQuality(int id) {
        return qualityMap.get(id).name;
    }

    public int getQualityColor(int id) {
        return Color.parseColor(qualityMap.get(id).textcolor);
    }

    /**
     * Get card class name
     * @param card card
     * @return
     */
    public String getCardClass(Card card) {
        if (card.cardClass != null && cardClassMap.containsKey(card.cardClass.id)) {
            return cardClassMap.get(card.cardClass.id).name;
        } else {
            return "Neutral";
        }
    }

    /**
     * Get card set name
     * @param id card set id
     * @return
     */
    public String getCardSet(int id) {
        return cardSetMap.get(id).name;
    }

    /**
     * Get card type name
     * @param id card type id
     * @return
     */
    public String getCardType(int id) {
        return cardTypeMap.get(id).name;
    }

}
