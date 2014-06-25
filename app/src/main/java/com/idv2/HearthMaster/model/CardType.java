package com.idv2.HearthMaster.model;

import com.j256.ormlite.field.DatabaseField;

import java.util.Collection;

/**
 * Created by charlee on 2014-06-21.
 *
 * Card Type: Minon, Spell, Weapon
 */
public class CardType {

    public static final int MINION = 4;
    public static final int SPELL = 5;
    public static final int WEAPON = 7;

    @DatabaseField(id = true)
    public int id;

    @DatabaseField(canBeNull = false)
    public String name;
}
