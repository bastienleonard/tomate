package com.bastienleonard.tomate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.bastienleonard.tomate.trello.TrelloCredentials;
import com.bastienleonard.tomate.trello.loaders.BoardsLoader;
import com.bastienleonard.tomate.trello.models.Board;
import com.bastienleonard.tomate.util.LogUtil;
import com.crashlytics.android.Crashlytics;

import java.util.List;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private static final int BOARDS_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.main_activity);
        setupToolbar();

        String token = TrelloCredentials.getPersistedToken(this);

        if (TextUtils.isEmpty(token)) {
            startActivity(new Intent(this, TrelloLoginActivity.class));
        } else {
            LogUtil.d("DEBUG", "Found persisted token " + token);
            getSupportLoaderManager().initLoader(BOARDS_LOADER_ID, null, new LoaderManager.LoaderCallbacks<List<Board>>() {
                @Override
                public Loader<List<Board>> onCreateLoader(int id, Bundle args) {
                    return new BoardsLoader(MainActivity.this);
                }

                @Override
                public void onLoadFinished(Loader<List<Board>> loader, List<Board> data) {

                }

                @Override
                public void onLoaderReset(Loader<List<Board>> loader) {

                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
