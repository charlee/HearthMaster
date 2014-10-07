package com.idv2.HearthMaster.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by charlee on 2014-09-14.
 */

@DatabaseTable
public class Deck {

    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(canBeNull = false)
    public int classId;

    @DatabaseField(canBeNull = false)
    public String name;

    /**
     * comma separated string consist of card ids
     */
    @DatabaseField(canBeNull = false)
    public String cards;

    public Deck() {}

    public Deck(int classId, String name, String cards) {
        this.classId = classId;
        this.name = name;
        this.cards = cards;
    }
}
