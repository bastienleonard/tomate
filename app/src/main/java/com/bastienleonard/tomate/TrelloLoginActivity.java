package com.bastienleonard.tomate;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bastienleonard.tomate.trello.TrelloCredentials;

public final class TrelloLoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trello_login_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        WebView webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new Client());
        String url = new Uri.Builder()
                .scheme("https")
                .authority("trello.com")
                .path("1/authorize")
                .appendQueryParameter("key", TrelloCredentials.getAppKey())
                .appendQueryParameter("name", "Tomate")
                .appendQueryParameter("response_type", "token")
                .appendQueryParameter("scope", "read,write")
                .appendQueryParameter("return_url", "https://developers.trello.com")
                .appendQueryParameter("callback_method", "fragment")
                .appendQueryParameter("expiration", "never")
                .build()
                .toString();
        webView.loadUrl(url);
        //"https://trello.com/1/authorize?key=6ea4c21c2d0d193dbfab3c233f4ea1f4&name=Tomate&response_type=token&scope=read%2Cwrite&return_url=https%3A%2F%2Fdevelopers.trello.com&callback_method=fragment&expiration=never"
    }

    private final class Client extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            android.util.Log.e("DEBUG", "URL: " + url);

            if (url.startsWith("https://developers.trello.com/#token=")) {
                String token = url.substring(url.indexOf("=") + 1);
                android.util.Log.e("DEBUG", "Found token " + token);

                if (TrelloCredentials.persistToken(view.getContext(), token)) {
                    TrelloLoginActivity.this.finish();
                } else {
                    // FIXME: show error message
                }
            }

            view.loadUrl(url);
            return true;
        }
    }
}
