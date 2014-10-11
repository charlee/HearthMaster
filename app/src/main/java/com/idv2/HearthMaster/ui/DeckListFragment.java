package com.idv2.HearthMaster.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.idv2.HearthMaster.R;
import com.idv2.HearthMaster.model.Card;
import com.idv2.HearthMaster.model.CardManager;
import com.idv2.HearthMaster.model.Deck;

import java.util.List;

/**
 * Created by charlee on 2014-09-13.
 */
public class DeckListFragment extends Fragment {

    private Button newDeckButton;
    private ListView deckList;

    private ArrayAdapter<Deck> deckAdapter;
    private CardManager cm;

    private DeckItemView activeItemView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_deck_list, container, false);

        // setup deck list
        deckList = (ListView) view.findViewById(R.id.deck_list);
        deckAdapter = new DeckAdapter(getActivity(), 0);
        deckList.setAdapter(deckAdapter);

        cm = CardManager.getInstance();
        List<Deck> decks = cm.getAllDecks();
        deckAdapter.addAll(decks);
        deckAdapter.notifyDataSetChanged();

        deckList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DeckItemView itemView = (DeckItemView) view;
                if (!itemView.isOperationPaneVisible()) {

                    Deck deck = deckAdapter.getItem(position);
                    if (deck != null) {

                        // start deck builder

                        Bundle bundle = new Bundle();
                        bundle.putInt(DeckBuilderFragment.DECK_ID, deck.id);
                        Fragment fragment = new DeckBuilderFragment();
                        fragment.setArguments(bundle);
                        getFragmentManager().beginTransaction()
                                .replace(R.id.content_frame, fragment)
                                .addToBackStack(null)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit();
                    }
                }
            }
        });

        deckList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (activeItemView != null) {
                    activeItemView.activateItemPane();
                    activeItemView = null;
                }

                activeItemView = (DeckItemView) view;
                activeItemView.activateOperationPane();

                return true;
            }
        });

        deckList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (activeItemView != null) {
                    activeItemView.activateItemPane();
                    activeItemView = null;
                }

            }
        });

        newDeckButton = (Button)view.findViewById(R.id.new_deck);
        newDeckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ClassSelectFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });

        return view;
    }

    public class DeckAdapter extends ArrayAdapter<Deck> implements DeckItemView.OnDeleteListener {

        public DeckAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DeckItemView v = (DeckItemView) convertView;

            if (v == null) {
                v = new DeckItemView(getContext());
                v.setOnDeleteListener(this);
            }

            Deck deck = getItem(position);

            v.setPosition(position);
            v.setDeckName(deck.name);
            v.setDeckClass(deck.classId);

            return v;
        }

        /**
         * Delete a deck
         * @param v
         * @param position
         */
        @Override
        public void OnDelete(View v, int position) {


            Deck deck = this.getItem(position);
            this.remove(deck);

            CardManager cm = CardManager.getInstance();
            cm.deleteDeck(deck.id);

            notifyDataSetChanged();
        }
    }
}

class DeckItemView extends FrameLayout {

    public interface OnDeleteListener {
        public void OnDelete(View v, int position);
    }

    private View operationPane;
    private View itemPane;
    private TextView deckName;
    private TextView deckClass;
    private ImageView deckClassIcon;

    private int position;
    private boolean operationPaneVisible = false;

    private OnDeleteListener onDeleteListener = null;


    public DeckItemView(Context context) {
        super(context);
        init(null, 0);
    }

    public DeckItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public DeckItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        FrameLayout layout = (FrameLayout) inflater.inflate(R.layout.deck_list_item, null);
        addView(layout);

        operationPane = layout.findViewById(R.id.operation_pane);
        itemPane = layout.findViewById(R.id.item_pane);
        deckName = (TextView) layout.findViewById(R.id.deck_name);
        deckClass = (TextView) layout.findViewById(R.id.deck_class);
        deckClassIcon = (ImageView) layout.findViewById(R.id.deck_class_icon);

        ImageView deleteItemButton = (ImageView) layout.findViewById(R.id.delete_item);
        deleteItemButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteListener != null) {
                    onDeleteListener.OnDelete(v, position);
                }
            }
        });
    }

    public void setOnDeleteListener(OnDeleteListener listener) {
        this.onDeleteListener = listener;
    }


    public void activateOperationPane() {

        int duration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        itemPane.animate()
                .alpha(0f)
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        itemPane.setVisibility(INVISIBLE);
                        operationPaneVisible = true;
                    }
                });

        operationPane.setAlpha(0f);
        operationPane.setVisibility(VISIBLE);
        operationPane.animate()
                .alpha(1f)
                .setDuration(duration)
                .setListener(null);
    }

    public void activateItemPane() {

        int duration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        operationPane.animate()
                .alpha(0f)
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        operationPane.setVisibility(INVISIBLE);
                        operationPaneVisible = false;
                    }
                });

        itemPane.setAlpha(0f);
        itemPane.setVisibility(VISIBLE);
        itemPane.animate()
                .alpha(1f)
                .setDuration(duration)
                .setListener(null);
    }

    public void setDeckName(String deckName) {
        this.deckName.setText(deckName);
    }

    public void setDeckClass(int deckClassId) {
        this.deckClass.setText(Card.className.get(deckClassId));
        this.deckClassIcon.setImageResource(Card.classIcon.get(deckClassId));
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }

    public boolean isOperationPaneVisible() {
        return operationPaneVisible;
    }
}

