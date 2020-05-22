package libtiff.cyprus.istay;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.http.SslError;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView webview1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webview1  = (WebView) findViewById(R.id.webview1);
        CookieSyncManager.createInstance(this);
        CookieManager.getInstance().setAcceptCookie(true);

        final Activity activity = this;

        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        webview1.setWebViewClient(new WebViewClient()
        {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
            {
                // DO NOT CALL SUPER METHOD
                onReceivedSslError(view, handler, error);
            }
        });

        if (wifi.isWifiEnabled() || connectivityManager.getActiveNetworkInfo()!= null){
            //wifi/data is enabled
            webview1.getSettings().setSupportZoom(true);
            webview1.getSettings().setBuiltInZoomControls(true);
            webview1.getSettings().setDisplayZoomControls(false);
            webview1.getSettings().setDomStorageEnabled(true);
            webview1.getSettings().setJavaScriptEnabled(true);
            webview1.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webview1.getSettings().setUseWideViewPort(true);
            webview1.getSettings().setLoadWithOverviewMode(true);
            webview1.getSettings().setDefaultTextEncodingName("utf-8");
            webview1 .loadUrl("https://istay.com.cy/");
        }
        else
        {
            Toast.makeText(getApplicationContext(),"No internet connection , please enable your wifi or data connection. Ενεργοποιήστε το ίντερνετ ή τα δεδομένα σας για να μπορέσει να τρέξει η εφαρμογή.",Toast.LENGTH_LONG).show();
            finish();
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webview1.loadUrl("https://istay.com.cy/search-results/?city=&arrive=&depart=&guest=");
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        if (webview1.canGoBack())
        {
            webview1.goBack();
        }
        else
        {
            super.onBackPressed();
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
