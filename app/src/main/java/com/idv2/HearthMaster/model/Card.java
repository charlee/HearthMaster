package com.idv2.HearthMaster.model;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by charlee on 2014-06-21.
 */

@DatabaseTable
public class Card {

    public static final int CARD_HEIGHT = 465;
    public static final int CARD_WIDTH = 307;

    @DatabaseField(id = true)
    public int id;

    @DatabaseField(canBeNull = false)
    public String name;

    @DatabaseField(canBeNull = false)
    public String description;

    @DatabaseField(canBeNull = false)
    public int cost;

    @DatabaseField(canBeNull = false)
    public int attack;

    @DatabaseField(canBeNull = false)
    public int health;

    @DatabaseField(canBeNull = false)
    public String image;

    @DatabaseField(foreign = true)
    public Quality quality;

    @DatabaseField(foreign = true)
    public CardType cardType;

    @DatabaseField(foreign = true)
    public CardClass cardClass;

    @DatabaseField(foreign = true)
    public CardSet cardSet;

    @DatabaseField(foreign = true)
    public Race race;

    @DatabaseField(canBeNull = false)
    public boolean elite;

    @DatabaseField(canBeNull = false)
    public int faction;

    @DatabaseField(canBeNull = false)
    public boolean collectible;


    public boolean isSpell() {
        return CardManager.getInstance().getCardType(cardType.id).equals(CardType.SPELL);
    }

    public boolean isWeapon() {
        return CardManager.getInstance().getCardType(cardType.id).equals(CardType.WEAPON);
    }

    public boolean isMinion() {
        return CardManager.getInstance().getCardType(cardType.id).equals(CardType.MINION);
    }
}
