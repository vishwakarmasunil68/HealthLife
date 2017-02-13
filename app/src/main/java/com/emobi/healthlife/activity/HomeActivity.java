package com.emobi.healthlife.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.emobi.healthlife.R;
import com.emobi.healthlife.webservices.WebServicesCallBack;

public class HomeActivity extends AppCompatActivity implements WebServicesCallBack{
    private final static String PARAGRAPH_API_CALL="paragraph_api_call";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case PARAGRAPH_API_CALL:
                break;
        }
    }
    public void parseResponse(String response){

    }
}
