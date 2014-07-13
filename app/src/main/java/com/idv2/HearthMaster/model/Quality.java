package com.idv2.HearthMaster.model;

import com.j256.ormlite.field.DatabaseField;

import java.util.Collection;

/**
 * Created by charlee on 2014-06-21.
 *
 * Quality: Free, Common, Rare, Epic, legendary
 */
public class Quality {

    public static final int FREE = 0;
    public static final int COMMON = 1;
    public static final int RARE = 3;
    public static final int EPIC = 4;
    public static final int LEGENDARY = 5;

    @DatabaseField(id = true)
    public int id;

    @DatabaseField(canBeNull = false)
    public String name;

    @DatabaseField(canBeNull = true)
    public String textcolor;
}
