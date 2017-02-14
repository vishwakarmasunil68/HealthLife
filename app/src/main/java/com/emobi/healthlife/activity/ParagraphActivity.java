package com.emobi.healthlife.activity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.emobi.healthlife.R;
import com.emobi.healthlife.pojo.ParagraphPOJO;
import com.emobi.healthlife.pojo.ParagraphResultPOJO;
import com.emobi.healthlife.utility.Pref;
import com.emobi.healthlife.utility.StringUtil;
import com.emobi.healthlife.webservices.WebServiceBase;
import com.emobi.healthlife.webservices.WebServicesCallBack;
import com.emobi.healthlife.webservices.WebServicesUrls;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParagraphActivity extends AppCompatActivity implements WebServicesCallBack,View.OnClickListener{
    private final static String PARAGRAPH_API_CALL="paragraph_api_call";
    private final String TAG=getClass().getSimpleName();
    @BindView(R.id.tv_welcome)
    TextView tv_welcome;
    @BindView(R.id.tv_paragraph_no)
    TextView tv_paragraph_no;
    @BindView(R.id.tv_starting_time)
    TextView tv_starting_time;
    @BindView(R.id.tv_paragraph)
    TextView tv_paragraph;
    @BindView(R.id.btn_done)
    Button btn_done;
    long lapsed_time=0;
    boolean is_proceeded=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paragraph);
        ButterKnife.bind(this);
        callParagraphAPI();
        tv_welcome.setText("Welcome "+Pref.GetStringPref(getApplicationContext(), StringUtil.login,""));
    }

    public void callParagraphAPI(){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("login_id", Pref.GetStringPref(getApplicationContext(), StringUtil.login,"")));
        new WebServiceBase(nameValuePairs, this, PARAGRAPH_API_CALL).execute(WebServicesUrls.PARAGRAPH_URL);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case PARAGRAPH_API_CALL:
                    parseParagraphResponse(response);
                break;
        }
    }
    public void parseParagraphResponse(String response){
        Log.d(TAG,"paragraph response:-"+response);
        Gson gson=new Gson();
        final ParagraphPOJO pojo=gson.fromJson(response,ParagraphPOJO.class);
        if(pojo!=null){
            try{
                if(pojo.getSuccess().equals("true")){
                    final ParagraphResultPOJO resultPOJO=pojo.getList_pojo().get(0);
                    tv_paragraph_no.setText("Starting with paragraph no "+resultPOJO.getPno());
                    tv_paragraph.setText(resultPOJO.getParagraph());
                    final long time=System.currentTimeMillis();
                    Date d=new Date(time);
                    SimpleDateFormat sdf=new SimpleDateFormat("hh:mm:ss a");
                    tv_starting_time.setText(sdf.format(d));
                    final long timer_end_time=20000;
                    new CountDownTimer(timer_end_time,1000){

                        @Override
                        public void onTick(long l) {
                            Log.d(TAG,"time:-"+l);
                            lapsed_time=timer_end_time-l;
                            Log.d(TAG,"timing:-"+lapsed_time);
                            if(l<3000){
                                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                                toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 1000);
                            }
                            else {
                                if (l < 5000) {
                                    ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                                    toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 500);
                                } else {
                                    if (l < 10000) {
                                        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                                        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFinish() {
                            if(!is_proceeded) {
                                Intent intent = new Intent(ParagraphActivity.this, QuestionsActivity.class);
                                intent.putExtra("paragraph_class", resultPOJO);
                                intent.putExtra("start_time", time);
                                intent.putExtra("end_time", time + timer_end_time);
                                intent.putExtra("lapsed_time", timer_end_time);
                                is_proceeded=true;
                                startActivity(intent);
                            }
                        }
                    }.start();

                    btn_done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(!is_proceeded) {
                                Intent intent = new Intent(ParagraphActivity.this, QuestionsActivity.class);
                                intent.putExtra("paragraph_class", resultPOJO);
                                intent.putExtra("start_time", time);
                                intent.putExtra("end_time", time + lapsed_time);
                                intent.putExtra("lapsed_time", lapsed_time);
                                is_proceeded=true;
                                startActivity(intent);
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.btn_done:

                break;
        }
    }
}
