package com.idv2.HearthMaster.model;

import com.j256.ormlite.field.DatabaseField;

import java.util.Collection;

/**
 * Created by charlee on 2014-06-21.
 *
 * Card Class: Warrior, Paladin, Hunter, ...
 */
public class CardClass {

    public static final int WARRIOR = 1;
    public static final int PALADIN = 2;
    public static final int HUNTER = 3;
    public static final int ROGUE = 4;
    public static final int PRIEST = 5;
    public static final int SHAMAN = 7;
    public static final int MAGE = 8;
    public static final int WARLOCK = 9;
    public static final int DRUID = 11;

    @DatabaseField(id = true)
    public int id;

    @DatabaseField(canBeNull = false)
    public String name;
}
