package com.bastienleonard.tomate;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

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
    }

    private final class Client extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("https://developers.trello.com/#token=")) {
                String token = url.substring(url.indexOf("=") + 1);

                if (!TrelloCredentials.persistToken(view.getContext(), token)) {
                    Toast.makeText(view.getContext(), R.string.error_trello_login, Toast.LENGTH_SHORT).show();
                }

                TrelloLoginActivity.this.finish();
            }

            view.loadUrl(url);
            return true;
        }
    }
}
