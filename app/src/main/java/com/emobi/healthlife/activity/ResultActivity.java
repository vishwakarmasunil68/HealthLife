package com.emobi.healthlife.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.emobi.healthlife.R;
import com.emobi.healthlife.utility.Pref;
import com.emobi.healthlife.utility.StringUtil;
import com.emobi.healthlife.webservices.WebServiceBase;
import com.emobi.healthlife.webservices.WebServicesCallBack;
import com.emobi.healthlife.webservices.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultActivity extends AppCompatActivity implements WebServicesCallBack{
    private final static String RESULT_API="result_api";
    private final String TAG=getClass().getSimpleName();
    @BindView(R.id.tv_result_analysis)
    TextView tv_result_analysis;
    @BindView(R.id.tv_result_msg)
    TextView tv_result_msg;
    @BindView(R.id.btn_ok)
    Button btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            String ssno=bundle.getString("ssno");
            String percentage=bundle.getString("percentage");
            String time_taken=bundle.getString("time_taken");
            String wpm=bundle.getString("wpm");
            String noofwords=bundle.getString("noofwords");
//            int per=Integer.parseInt(percentage);
            tv_result_msg.setText("Paragraph number "+ssno+" No of words "+noofwords+
            " Reading speed "+wpm+" Accuracy "+percentage+" %");
//            callResultAPI(ssno,percentage,time_taken,wpm);
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ResultActivity.this,HomeActivity.class));
                    finishAffinity();
                }
            });
        }
    }
    public void callResultAPI(String ssno,String percentage,String time_taken,String wpm){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("login_id", Pref.GetStringPref(getApplicationContext(), StringUtil.login, "")));
        nameValuePairs.add(new BasicNameValuePair("ssno", ssno));
        int per=Integer.parseInt(percentage);
        if (per>= 80) {
            nameValuePairs.add(new BasicNameValuePair("finished", "1"));
            nameValuePairs.add(new BasicNameValuePair("passed", "1"));
        } else {
            nameValuePairs.add(new BasicNameValuePair("finished", "0"));
            nameValuePairs.add(new BasicNameValuePair("passed", "0"));
        }
        nameValuePairs.add(new BasicNameValuePair("ttaken", time_taken));
        nameValuePairs.add(new BasicNameValuePair("spreed", wpm));
        new WebServiceBase(nameValuePairs, this, RESULT_API).execute(WebServicesUrls.RESULT_URL);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case RESULT_API:
                parseResultResponse(response);
                break;
        }
    }
    public void parseResultResponse(String response){
        try {
            JSONObject jsonObject=new JSONObject(response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ResultActivity.this,HomeActivity.class));
        finishAffinity();
    }
}
