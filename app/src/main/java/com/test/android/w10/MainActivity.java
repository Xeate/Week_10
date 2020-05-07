package com.test.android.w10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

    WebView web;
    String webURL;
    String refreshURL;
    int i = -1;

    ArrayList <String> urls = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        web = findViewById(R.id.webView);
        web.setWebViewClient(new WebViewClient());
        web.getSettings().setJavaScriptEnabled(true);

    }

    //Haetaan annettu nettisivu + jos syöte on "index.html" niin sille tarkoitettu toiminto
    public void haeURL(View view) {

        EditText osoitekentta = (EditText) findViewById(R.id.osoitekentta);
        webURL = osoitekentta.getText().toString();
        if (webURL.equals("index.html")) {

            web.loadUrl("file:///android_asset/index.html");

        } else {

            i++;
            for (int s = urls.size() ; s>i ; s--) {
                    urls.remove(s-1);
            }

            urls.add("https://" + webURL);
            web.loadUrl("https://" + webURL);

            if (i > 9) {
                urls.remove(0);
                i--;
            }

        }

    }

    public void refreshURL(View view) {
        refreshURL = web.getUrl();
        web.loadUrl(refreshURL);

    }

    public void executeJavascript1(View view) {

        web.evaluateJavascript("javascript:shoutOut()", null);
    }

    public void executeJavascript2(View view) {

        web.evaluateJavascript("javascript:initialize()", null);
    }

    public void forwardURL(View view) {
        if (i < 9 && (i+2)<=urls.size()) {
            i++;
            ListIterator<String> liter = literator(i);
            String forwardurl = liter.next();
            web.loadUrl(forwardurl);
            System.out.println("################" + forwardurl);
        } else {
            Toast.makeText(MainActivity.this,
                    "No websites to go forward to",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void returnURL(View view) {
        if(i > 0) {
            i--;
            ListIterator<String> liter = literator(i);
            String returnurl = liter.next();
            web.loadUrl(returnurl);
            System.out.println("################" + returnurl);
        } else {
            Toast.makeText(MainActivity.this,
                    "No previous websites",
                    Toast.LENGTH_SHORT).show();
        }
    }

    //Asettaa iteraattorin indexin haluttuun paikkaan x:n perusteella
    public ListIterator<String> literator(int x) {
            if (x < 0 || urls.size() < x) {
                throw new IndexOutOfBoundsException();
            }

            ListIterator<String> liter = urls.listIterator();

            for (; x > 0; --x) {
                liter.next(); // ignore the first x values
            }
            return liter;
    }
}
