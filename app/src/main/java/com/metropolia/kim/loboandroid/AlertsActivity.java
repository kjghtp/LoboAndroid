package com.metropolia.kim.loboandroid;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;

/**
 * Created by kimmo on 27/04/2016.
 */
public class AlertsActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor> {
    RadioGroup catRadioGroup;
    RadioGroup recRadioGroup;
    RadioGroup hisRadioGroup;
    ListView alertHistoryView;

    int range = -1;

    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        catRadioGroup = (RadioGroup) findViewById(R.id.catRadioGroup);
        recRadioGroup = (RadioGroup) findViewById(R.id.recRadioGroup);
        hisRadioGroup = (RadioGroup) findViewById(R.id.hisRadioGroup);
        alertHistoryView = (ListView) findViewById(R.id.alertHistoryView);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    public void sendAlert () {
        //Get input
        int alertCat = catRadioGroup.getCheckedRadioButtonId();
        int alertRec = recRadioGroup.getCheckedRadioButtonId();
    }

    public void getAlertHistory () {
        //Get input
        int rangeInput = hisRadioGroup.getCheckedRadioButtonId();

        switch (rangeInput) {
            case R.id.hisRadio1: //5
                range = 5;
                break;
            case R.id.hisRadio2: //10
                range = 10;
                break;
            case R.id.hisRadio3: //All
                range = 0;
                break;
            default:
                Log.d("ALERT", "Invalid history range -1");
                break;
        }

        String[] params = {"resources/Alerts", "alert"};
        NetworkingTask networkTask = new NetworkingTask(this);
        networkTask.execute(params);
        fillData();
    }

    private void fillData() {
        String[] fromColumns = {"topic", "postname", "currenttime", "receivergroup"}; // from which COLUMNS
        int[] toViews = {R.id.alertTopic, R.id.alertSender, R.id.alertTimestamp, R.id.alertReceiverGroup}; // TO which VIEWS

        // initializing the CursorLoader
        getLoaderManager().initLoader(0, null, this);

        // creating and binding binding adapter
        this.adapter = new SimpleCursorAdapter(this, R.layout.alerts_list_item, null, fromColumns, toViews, 0);
        alertHistoryView.setAdapter(this.adapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d("ALERT","onCreateLoader()");
        String[] projection = {"_id", "topic", "postname", "currenttime", "receivergroup"};
        Uri uri = Uri.parse(ChatProvider.URL + "alerts/range/" + range); //RANGE HERE
        return new CursorLoader(this, uri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d("ALERT","onCreateLoader()");
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
