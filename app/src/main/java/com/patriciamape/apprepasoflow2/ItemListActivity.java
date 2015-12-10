package com.patriciamape.apprepasoflow2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ItemListFragment} and the item details
 * (if present) is a {@link ItemDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link ItemListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ItemListActivity extends AppCompatActivity
        implements ItemListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_app_bar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getIntent();

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;


            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((ItemListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.item_list))
                    .setActivateOnItemClick(true);
        }
            /*
                Button button = (Button) findViewById(R.id.botonresult);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(ItemListActivity.this, ItemDetailActivity.class);
                        startActivityForResult(intent1, 1); //REQUEST CODE: identificador del numero de llamada del activity
                    }
                });
            */



        girar();

        // TODO: If exposing deep links into your app, handle intents here.
    }


    public void girar(){
        FrameLayout framelayout = (FrameLayout) findViewById(R.id.item_detail_container);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        if (mTwoPane) {
            //Metodo seekbar
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    TextView textProgreso = (TextView) findViewById(R.id.progreso);
                    textProgreso.setText(String.valueOf(progress));
                    Toast.makeText(ItemListActivity.this, "" + progress, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
    }
    /**
     * Callback method from {@link ItemListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, id);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, ItemDetailActivity.class);
            detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }

    public void botonLimpiar(View view){
        TextView textview1 = (TextView) findViewById(R.id.item_detail);
        TextView textview2 = (TextView) findViewById(R.id.progreso);
        if (textview1 != null)
            textview1.setText(" ");
        textview2.setText(" ");
    }

    public void botonResultado(View view){
        Intent intent1 = new Intent(ItemListActivity.this, ItemDetailActivity.class);
        startActivityForResult(intent1,1); //REQUEST CODE: identificador del numero de llamada del activity
    }

    //Cuando cerremos la segunda activity se lanza este metodo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intentData) {//REQUEST CODE, RESULT_OK, intentResultado

        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                String dato = intentData.getStringExtra("resultado");
                Toast.makeText(ItemListActivity.this, dato, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
