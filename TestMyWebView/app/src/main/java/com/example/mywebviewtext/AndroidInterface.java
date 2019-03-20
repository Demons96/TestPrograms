package com.example.mywebviewtext;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.just.agentweb.AgentWeb;

public class AndroidInterface {

    private AgentWeb agent;
    private Context context;

    public AndroidInterface(AgentWeb agent, Context context) {
        this.agent = agent;
        this.context = context;
    }

    @JavascriptInterface
    public void goToAndroid(){
        Toast.makeText(context, "充值", Toast.LENGTH_LONG).show();
    }

}
