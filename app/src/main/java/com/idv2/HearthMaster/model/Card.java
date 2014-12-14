package com.idv2.HearthMaster.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.idv2.HearthMaster.HearthMasterApp;
import com.idv2.HearthMaster.R;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by charlee on 2014-06-21.
 */

@DatabaseTable
public class Card {

    /**
     * Field name
     */

    public static final String FIELD_CARDCLASS = "cardClass";
    public static final String FIELD_NAME = "name";

    /**
     * Card Class
     */
    public static final int NEUTRAL = 0;
    public static final int WARRIOR = 1;
    public static final int PALADIN = 2;
    public static final int HUNTER = 3;
    public static final int ROGUE = 4;
    public static final int PRIEST = 5;
    public static final int SHAMAN = 7;
    public static final int MAGE = 8;
    public static final int WARLOCK = 9;
    public static final int DRUID = 11;

    public static final ImmutableMap<Integer, Integer> className = ImmutableMap.<Integer, Integer>builder()
            .put(NEUTRAL, R.string.class_neutral)
            .put(WARRIOR, R.string.class_warrior)
            .put(PALADIN, R.string.class_paladin)
            .put(HUNTER, R.string.class_hunter)
            .put(ROGUE, R.string.class_rogue)
            .put(PRIEST, R.string.class_priest)
            .put(SHAMAN, R.string.class_shaman)
            .put(MAGE, R.string.class_mage)
            .put(WARLOCK, R.string.class_warlock)
            .put(DRUID, R.string.class_druid)
            .build();

    public static final ImmutableMap<Integer, Integer> classIcon = ImmutableMap.<Integer, Integer>builder()
            .put(NEUTRAL, 0)
            .put(WARRIOR, R.drawable.class_warrior)
            .put(PALADIN, R.drawable.class_paladin)
            .put(HUNTER, R.drawable.class_hunter)
            .put(ROGUE, R.drawable.class_rogue)
            .put(PRIEST, R.drawable.class_priest)
            .put(SHAMAN, R.drawable.class_shaman)
            .put(MAGE, R.drawable.class_mage)
            .put(WARLOCK, R.drawable.class_warlock)
            .put(DRUID, R.drawable.class_druid)
            .build();

    public static final ImmutableList<Integer> allClasses = ImmutableList.of(
            NEUTRAL,
            WARRIOR,
            PALADIN,
            HUNTER,
            ROGUE,
            PRIEST,
            SHAMAN,
            MAGE,
            WARLOCK,
            DRUID
    );


    /**
     * Card Set
     */
    public static final int BASIC = 2;
    public static final int EXPERT = 3;
    public static final int REWARD = 4;
    public static final int MISSIONS = 5;
    public static final int PROMOTION = 11;
    public static final int NAXXRAMAS = 12;
    public static final int GVG = 13;
    public static final int CREDITS = 16;

    public static final ImmutableMap<Integer, Integer> setName = ImmutableMap.<Integer, Integer>builder()
            .put(BASIC, R.string.set_basic)
            .put(EXPERT, R.string.set_expert)
            .put(REWARD, R.string.set_reward)
            .put(MISSIONS, R.string.set_missions)
            .put(NAXXRAMAS, R.string.set_naxxramas)
            .put(GVG, R.string.set_gvg)
            .put(PROMOTION, R.string.set_promotion)
            .put(CREDITS, R.string.set_credits)
            .build();

    public static final ImmutableList<Integer> allSets = ImmutableList.of(
            BASIC,
            EXPERT,
            REWARD,
            MISSIONS,
            PROMOTION,
            NAXXRAMAS,
            GVG
    );


    /**
     * Card Type
     */
    public static final int MINION = 4;
    public static final int SPELL = 5;
    public static final int WEAPON = 7;

    public static final ImmutableMap<Integer, Integer> typeName = ImmutableMap.of(
            MINION, R.string.type_minion,
            SPELL, R.string.type_spell,
            WEAPON, R.string.type_weapon
    );

    public static final ImmutableList<Integer> allTypes = ImmutableList.of(MINION, SPELL, WEAPON);

    /**
     * Card Quality
     */
    public static final int FREE = 0;
    public static final int COMMON = 1;
    public static final int RARE = 3;
    public static final int EPIC = 4;
    public static final int LEGENDARY = 5;

    public static final ImmutableMap<Integer, Integer> qualityName = ImmutableMap.of(
            FREE, R.string.quality_free,
            COMMON, R.string.quality_common,
            RARE, R.string.quality_rare,
            EPIC, R.string.quality_epic,
            LEGENDARY, R.string.quality_legendary
    );

    public static final ImmutableMap<Integer, Integer> qualityColor = ImmutableMap.of(
            FREE, 0xff9d9d9d,
            COMMON, 0xff000000,
            RARE, 0xff0070dd,
            EPIC, 0xffa335ee,
            LEGENDARY, 0xffff8000
    );

    public static final ImmutableList<Integer> allQualities = ImmutableList.of(FREE, COMMON, RARE, EPIC, LEGENDARY);


    /**
     * Card Race
     */
    public static final int NONE = 0;
    public static final int MURLOC = 14;
    public static final int DEMON = 15;
    public static final int MECH = 17;
    public static final int BEAST = 20;
    public static final int TOTEM = 21;
    public static final int PIRATE = 23;
    public static final int DRAGON = 24;

    public static final ImmutableMap<Integer, Integer> raceName = ImmutableMap.<Integer, Integer>builder()
            .put(NONE, R.string.race_none)
            .put(MURLOC, R.string.race_murloc)
            .put(DEMON, R.string.race_demon)
            .put(MECH, R.string.race_mech)
            .put(BEAST, R.string.race_beast)
            .put(TOTEM, R.string.race_totem)
            .put(PIRATE, R.string.race_pirate)
            .put(DRAGON, R.string.race_dragon)
            .build();

    public static final ImmutableList<Integer> allRaces = ImmutableList.of(
            NONE,
            MURLOC,
            DEMON,
            BEAST,
            TOTEM,
            PIRATE,
            DRAGON
    );
    public static final String FIELD_COST = "cost";

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

    @DatabaseField(canBeNull = false)
    public int quality;

    @DatabaseField(canBeNull = false)
    public int cardType;

    @DatabaseField(canBeNull = false)
    public int cardClass;

    @DatabaseField(canBeNull = false)
    public int cardSet;

    @DatabaseField(canBeNull = false)
    public int race;

    @DatabaseField(canBeNull = false)
    public boolean elite;

    @DatabaseField(canBeNull = false)
    public int faction;

    @DatabaseField(canBeNull = false)
    public boolean collectible;


    public Card() {}

    public Card(Card card) {
        this.id = card.id;
        this.name = card.name;
        this.description = card.description;
        this.cost = card.cost;
        this.attack = card.attack;
        this.health = card.health;
        this.image = card.image;
        this.quality = card.quality;
        this.cardType = card.cardType;
        this.cardClass = card.cardClass;
        this.cardSet = card.cardSet;
        this.race = card.race;
        this.elite = card.elite;
        this.faction = card.faction;
        this.collectible = card.collectible;
    }
}
