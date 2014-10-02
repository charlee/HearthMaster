package com.idv2.HearthMaster.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.idv2.HearthMaster.R;
import com.idv2.HearthMaster.model.Card;

/**
 * Created by charlee on 2014-09-12.
 */
public class CardListView extends ListView {

    private Typeface listFont;
    private CardAdapter cardsAdapter;
    private boolean mCompat = false;

    public CardListView(Context context) {
        super(context);
        init(null, 0);
    }

    public CardListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CardListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

        Context context = getContext();

        // load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CardListView, defStyle, 0);

        mCompat = a.getBoolean(R.styleable.CardListView_compact, false);

        // load typeface
        if (!isInEditMode()) {
            listFont = Typeface.createFromAsset(context.getAssets(), "fonts/BelweBT-Bold.ttf");
        }

        cardsAdapter = new CardAdapter(context);
        this.setAdapter(cardsAdapter);
    }

    public class CardAdapter extends ArrayAdapter<Card> {

        public CardAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                v = inflater.inflate(mCompat ? R.layout.card_list_item_compact : R.layout.card_list_item, null);
            }

            Card card = getItem(position);

            if (card != null) {
                TextView cardName = (TextView) v.findViewById(R.id.card_name);

                if (!isInEditMode()) {
                    cardName.setTypeface(listFont);
                }
                cardName.setTextColor(Card.qualityColor.get(card.quality));
                cardName.setText(card.name);

                if (mCompat) {
                    TextView cardMeta = (TextView) v.findViewById(R.id.card_meta);
                    TextView cardCost = (TextView) v.findViewById(R.id.card_cost);
                    cardCost.setText(String.format("%d", card.cost));
                    cardMeta.setText(card.cardType == Card.SPELL ? "" : String.format("%d/%d", card.attack, card.health));

                } else {
                    ImageView classIcon = (ImageView) v.findViewById(R.id.class_icon);

                    TextView cardHealth = (TextView) v.findViewById(R.id.card_health);
                    TextView cardCost = (TextView) v.findViewById(R.id.card_cost);
                    TextView cardAttack = (TextView) v.findViewById(R.id.card_attack);
                    ImageView iconAttack = (ImageView) v.findViewById(R.id.icon_attack);
                    ImageView iconHealth = (ImageView) v.findViewById(R.id.icon_health);

                    cardCost.setText(String.format("%d", card.cost));
                    cardHealth.setText(String.format("%d", card.health));
                    cardAttack.setText(String.format("%d", card.attack));

                    if (card.cardType == Card.SPELL) {
                        cardHealth.setVisibility(View.GONE);
                        cardAttack.setVisibility(View.GONE);
                        iconHealth.setVisibility(View.GONE);
                        iconAttack.setVisibility(View.GONE);
                    } else {
                        cardHealth.setVisibility(View.VISIBLE);
                        cardAttack.setVisibility(View.VISIBLE);
                        iconHealth.setVisibility(View.VISIBLE);
                        iconAttack.setVisibility(View.VISIBLE);
                    }

                    if (card.cardType == Card.WEAPON) {
                        iconHealth.setImageDrawable(getResources().getDrawable(R.drawable.durability));
                        iconAttack.setImageDrawable(getResources().getDrawable(R.drawable.weapon));
                    } else {
                        iconHealth.setImageDrawable(getResources().getDrawable(R.drawable.health));
                        iconAttack.setImageDrawable(getResources().getDrawable(R.drawable.attack));
                    }

                    switch (card.cardClass) {
                        case Card.WARRIOR: classIcon.setImageResource(R.drawable.class_warrior); break;
                        case Card.MAGE: classIcon.setImageResource(R.drawable.class_mage); break;
                        case Card.DRUID: classIcon.setImageResource(R.drawable.class_druid); break;
                        case Card.WARLOCK: classIcon.setImageResource(R.drawable.class_warlock); break;
                        case Card.ROGUE: classIcon.setImageResource(R.drawable.class_rogue); break;
                        case Card.PRIEST: classIcon.setImageResource(R.drawable.class_priest); break;
                        case Card.PALADIN: classIcon.setImageResource(R.drawable.class_paladin); break;
                        case Card.HUNTER: classIcon.setImageResource(R.drawable.class_hunter); break;
                        case Card.SHAMAN: classIcon.setImageResource(R.drawable.class_shaman); break;
                        default: classIcon.setImageResource(0);break;
                    }
                }

            }

            return v;
        }
    }
}

