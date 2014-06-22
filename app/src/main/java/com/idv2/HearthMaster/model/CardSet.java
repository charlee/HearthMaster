package com.idv2.HearthMaster.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by charlee on 2014-06-21.
 *
 * Card Set: Basic, Expert, Reward, ...
 */
public class CardSet {

    @DatabaseField(id = true)
    public int id;

    @DatabaseField(canBeNull = false)
    public String name;
}
