package com.simbora.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.simbora.R;


/**
 * An activity representing a list of Eventos. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link EventoDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link EventoListFragment} and the item details
 * (if present) is a {@link EventoDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link EventoListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class EventoListActivity extends FragmentActivity
        implements EventoListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    //atributo que indica se é pra ir a tela principal automaticamente ou não
    private static boolean goToTodos=true;

    public boolean isGoToTodos() {
        return goToTodos;
    }

    public static void  setGoToTodos(boolean goToTodos) {
        EventoListActivity.goToTodos = goToTodos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_list);

        if (findViewById(R.id.evento_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((EventoListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.evento_list))
                    .setActivateOnItemClick(true);
        }
        if(isGoToTodos()){
            onItemSelected("1");
            setGoToTodos(false);
        }


        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link EventoListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(EventoDetailFragment.ARG_ITEM_ID, id);
            EventoDetailFragment fragment = new EventoDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.evento_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, EventoDetailActivity.class);
            detailIntent.putExtra(EventoDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
