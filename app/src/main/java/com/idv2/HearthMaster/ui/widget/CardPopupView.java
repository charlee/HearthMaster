package com.idv2.HearthMaster.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.idv2.HearthMaster.R;

/**
 * Created by charlee on 2014-09-12.
 */
public class CardPopupView extends FrameLayout {

    private CardView cardView;

    public CardPopupView(Context context) {
        super(context);
        init(null, 0);
    }

    public CardPopupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }


    public CardPopupView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        FrameLayout layout = (FrameLayout) inflater.inflate(R.layout.widget_card_popup, null);
        addView(layout);

        cardView = (CardView) layout.findViewById(R.id.card_view);

        // animate out card and hide popup when clicked
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation cardOutAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.card_out);
                cardOutAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        hide();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                cardView.startAnimation(cardOutAnimation);
            }
        });
    }

    public void show(int cardId) {
        cardView.setCardId(cardId);
        Animation cardInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.card_in);
        setVisibility(View.VISIBLE);
        cardView.startAnimation(cardInAnimation);
    }

    public void hide() {
        setVisibility(View.INVISIBLE);
    }

}
