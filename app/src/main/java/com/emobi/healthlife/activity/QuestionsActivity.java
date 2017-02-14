package com.emobi.healthlife.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.emobi.healthlife.R;
import com.emobi.healthlife.pojo.ParagraphResultPOJO;
import com.emobi.healthlife.webservices.WebServicesCallBack;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionsActivity extends AppCompatActivity implements WebServicesCallBack{
    private final static String RESULT_API="result_api";
    private final String TAG=getClass().getSimpleName();
    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    @BindView(R.id.tv_time_msg)
    TextView tv_time_msg;
    @BindView(R.id.view_pager)
    ViewPager view_pager;

    ParagraphResultPOJO data;
    boolean[] correct_ans;
//    int total_view_pager_count
    int wpm=0;
    int time_taken=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        ButterKnife.bind(this);
        data= (ParagraphResultPOJO) getIntent().getSerializableExtra("paragraph_class");
        if(data!=null){
            Bundle bundle=getIntent().getExtras();
            if(bundle!=null){
                long start_time=bundle.getLong("start_time");
                long lapsed_time=bundle.getLong("lapsed_time");
                long end_time=bundle.getLong("end_time");
                Log.d(TAG,"start_time:-"+start_time);
                Log.d(TAG,"end_time:-"+end_time);
                Log.d(TAG,"lapsed time:-"+lapsed_time);
//                time_taken=lapsed_time;
                tv_start_time.setText("You Start Time ="+start_time);
                tv_end_time.setText("You End Time ="+end_time);
                SimpleDateFormat sdf=new SimpleDateFormat("hh:mm:ss a");
                Date start_date=new Date(start_time);
                Date end_date=new Date(end_time);

                try {
                    int words= Integer.parseInt(data.getWords());
                    Log.d("sunil","words:-"+words);
                    Log.d("sunil","lapsed_times:-"+lapsed_time);
                    int sec= (int) (lapsed_time/1000);
                    Log.d("sunil","sec:-"+sec);
                    time_taken=sec;
                    wpm=words/sec;
                    wpm=wpm*60;
                    Log.d("sunil","wpm:-"+wpm);
                }
                catch (Exception e){
                    Log.d(TAG,"error:-"+e.toString());
                }
                tv_time_msg.setText("You started reading the paragraph containing " + data.getWords()+
                        " words at "+sdf.format(start_date)+" and finished at "+sdf.format(end_date)+", taking "+
                        " "+time_taken+" seconds.Hence your reading speed was "+wpm+" words per minute." +
                        "Now answer the following questions by selecting best options to know your level of " +
                        "comprehension.");

//                Log.d(TAG,"list_questions:-"+getListOfQuestions(data).toString());
                InitializeViewPager(getListOfQuestions(data),wpm);
            }
        }
        else{
            finishAffinity();
        }
    }
    public void callResultAPI(int noofquestion){
        int correct_int=0;
        for(boolean bol:correct_ans){
            if(bol) {
                correct_int++;
            }
        }
        int percentage=(int)(correct_int*100)/noofquestion;
        Log.d(TAG,"ssno:-"+data.getPno());
        Log.d(TAG,"percentage:-"+percentage);
        Log.d(TAG,"ttaken:-"+time_taken);
        Log.d(TAG,"wpm:-"+wpm);
        Intent intent=new Intent(QuestionsActivity.this,ResultActivity.class);
        intent.putExtra("ssno",data.getPno());
        intent.putExtra("percentage",String.valueOf(percentage));
        intent.putExtra("time_taken",String.valueOf(time_taken));
        intent.putExtra("wpm",String.valueOf(wpm));
        intent.putExtra("noofwords",data.getWords());
        startActivity(intent);

//        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//        nameValuePairs.add(new BasicNameValuePair("login_id", Pref.GetStringPref(getApplicationContext(), StringUtil.login,"")));
//        nameValuePairs.add(new BasicNameValuePair("ssno",data.getPno()));
//        if(percentage>=80){
//        nameValuePairs.add(new BasicNameValuePair("finished","1"));
//        nameValuePairs.add(new BasicNameValuePair("passed","1"));
//        }
//        else{
//            nameValuePairs.add(new BasicNameValuePair("finished","0"));
//            nameValuePairs.add(new BasicNameValuePair("passed","0"));
//        }
//        nameValuePairs.add(new BasicNameValuePair("ttaken",String.valueOf(time_taken)));
//        nameValuePairs.add(new BasicNameValuePair("spreed",String.valueOf(wpm)));
//        new WebServiceBase(nameValuePairs, this, RESULT_API).execute(WebServicesUrls.RESULT_URL);
    }

    public void changeViewPagerPage(boolean status){
            if(status) {
                view_pager.setCurrentItem(view_pager.getCurrentItem() + 1);
            }
        else{
                view_pager.setCurrentItem(view_pager.getCurrentItem() - 1);
            }
    }

    public void InitializeViewPager(List<Map<String,Object>> list_questions,int wpm){
        int noofpages=list_questions.size();
        if(noofpages>0) {
            correct_ans=new boolean[noofpages];
            view_pager.setOffscreenPageLimit(noofpages);
            CustomSwipeAdapter adapter = new CustomSwipeAdapter(this, noofpages, list_questions,wpm);
            view_pager.setAdapter(adapter);
            view_pager.setOnTouchListener(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    return true;
                }
            });
        }else{
            Toast.makeText(getApplicationContext(),"no timing has been added yet",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case RESULT_API:
                Log.d(TAG,"result _response:-"+response);
                parseResultResponse(response);
                break;
        }
    }
    public void parseResultResponse(String response){
        try {
            JSONObject object=new JSONObject(response);
            if(object.optString("success").equals("true")){
//                startActivity(new Intent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    class CustomSwipeAdapter extends PagerAdapter {
        Activity context;
        int noofpages;
        LayoutInflater layoutInflater;
        List<Map<String,Object>> list_questions;
        int wpm;
        public CustomSwipeAdapter(Activity context,int noofpages,List<Map<String,Object>> list_questions,int wpm){
            this.context=context;
            this.noofpages=noofpages;
            this.list_questions=list_questions;
            this.wpm=wpm;
        }
        @Override
        public int getCount() {
            return noofpages;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View item_view=layoutInflater.inflate(R.layout.inflate_question_layout,container,false);
            TextView tv_question= (TextView) item_view.findViewById(R.id.tv_question);
            final TextView tv_radio_id= (TextView) item_view.findViewById(R.id.tv_radio_id);
            final RadioGroup rg_question= (RadioGroup) item_view.findViewById(R.id.rg_question);
            RadioButton radio_q1= (RadioButton) item_view.findViewById(R.id.radio_q1);
            RadioButton radio_q2= (RadioButton) item_view.findViewById(R.id.radio_q2);
            RadioButton radio_q3= (RadioButton) item_view.findViewById(R.id.radio_q3);
            RadioButton radio_q4= (RadioButton) item_view.findViewById(R.id.radio_q4);
            Button btn_next= (Button) item_view.findViewById(R.id.btn_next);
            Button btn_previous= (Button) item_view.findViewById(R.id.btn_previous);
            try {
                final List<String> list_options = (List<String>) list_questions.get(position).get("options");
                final String ans= (String) list_questions.get(position).get("ans");
                radio_q1.setText(list_options.get(0));
                radio_q2.setText(list_options.get(1));
                radio_q3.setText(list_options.get(2));
                radio_q4.setText(list_options.get(3));
                if((position+1)==list_questions.size()){
                    btn_next.setText("finish");
                }
                if(position==0){
                    btn_previous.setVisibility(View.GONE);
                }

                tv_question.setText((String)list_questions.get(position).get("question"));
                final RadioGroup rg=rg_question;

                rg_question.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i){
                            case R.id.radio_q1:
                                tv_radio_id.setText("0");
                                break;
                            case R.id.radio_q2:
                                tv_radio_id.setText("1");
                                break;
                            case R.id.radio_q3:
                                tv_radio_id.setText("2");
                                break;
                            case R.id.radio_q4:
                                tv_radio_id.setText("3");
                                break;

                        }
                    }
                });
                btn_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(tv_radio_id.getText().toString().isEmpty()){
                            Toast.makeText(getApplicationContext(),"Please Select the Answer first",Toast.LENGTH_LONG).show();
                        }
                        else {
                            int id=Integer.parseInt(tv_radio_id.getText().toString());
                            if(ans.equals(list_options.get(id))){
                                correct_ans[position]=true;
                            }
                            for(boolean bol:correct_ans) {
                                Log.d(TAG, "correct ans:-" + bol);
                            }
                            Toast.makeText(getApplicationContext(),
                                    list_options.get(id), Toast.LENGTH_SHORT).show();
                            QuestionsActivity activity = (QuestionsActivity) context;
                            if ((position + 1) != list_questions.size()) {
                                activity.changeViewPagerPage(true);
                            }
                            else{
                                activity.callResultAPI(list_questions.size());
                            }
                        }
                    }
                });
                btn_previous.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        QuestionsActivity activity = (QuestionsActivity) context;
                        if ((position) != 0) {
                            activity.changeViewPagerPage(false);
                        }
                    }
                });

            }
            catch (Exception e){
                Log.d(TAG,"error:-"+e.toString());
            }


            container.addView(item_view);
            return item_view;
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view==(LinearLayout)object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout)object);
        }
    }


    public List<Map<String,Object>> getListOfQuestions(ParagraphResultPOJO pojo){
        List<Map<String,Object>> list_questions=new ArrayList<>();
        if(pojo!=null){
            String question1=pojo.getQ1();
            String question2=pojo.getQ2();
            String question3=pojo.getQ3();
            String question4=pojo.getQ4();
            String question5=pojo.getQ5();
            String question6=pojo.getQ6();
            String question7=pojo.getQ7();
            String question8=pojo.getQ8();
            String question9=pojo.getQ9();
            String question10=pojo.getQ10();

            if(!question1.isEmpty()){
                Map<String,Object> mp=new ArrayMap<>();
                mp.put("question",question1);
                List<String> list_options=new ArrayList<>();
                list_options.add(pojo.getOp11());
                list_options.add(pojo.getOp12());
                list_options.add(pojo.getOp13());
                list_options.add(pojo.getOp14());
                mp.put("options",list_options);
                mp.put("ans",pojo.getCop1());
                list_questions.add(mp);
            }

            if(!question2.isEmpty()){
                Map<String,Object> mp=new ArrayMap<>();
                mp.put("question",question2);
                List<String> list_options=new ArrayList<>();
                list_options.add(pojo.getOp21());
                list_options.add(pojo.getOp22());
                list_options.add(pojo.getOp23());
                list_options.add(pojo.getOp24());
                mp.put("options",list_options);
                mp.put("ans",pojo.getCop2());
                list_questions.add(mp);
            }

            if(!question3.isEmpty()){
                Map<String,Object> mp=new ArrayMap<>();
                mp.put("question",question3);
                List<String> list_options=new ArrayList<>();
                list_options.add(pojo.getOp31());
                list_options.add(pojo.getOp32());
                list_options.add(pojo.getOp33());
                list_options.add(pojo.getOp34());
                mp.put("options",list_options);
                mp.put("ans",pojo.getCop3());
                list_questions.add(mp);
            }

            if(!question4.isEmpty()){
                Map<String,Object> mp=new ArrayMap<>();
                mp.put("question",question4);
                List<String> list_options=new ArrayList<>();
                list_options.add(pojo.getOp41());
                list_options.add(pojo.getOp42());
                list_options.add(pojo.getOp43());
                list_options.add(pojo.getOp44());
                mp.put("options",list_options);
                mp.put("ans",pojo.getCop4());
                list_questions.add(mp);
            }

            if(!question5.isEmpty()){
                Map<String,Object> mp=new ArrayMap<>();
                mp.put("question",question5);
                List<String> list_options=new ArrayList<>();
                list_options.add(pojo.getOp51());
                list_options.add(pojo.getOp52());
                list_options.add(pojo.getOp53());
                list_options.add(pojo.getOp54());
                mp.put("options",list_options);
                mp.put("ans",pojo.getCop5());
                list_questions.add(mp);
            }

            if(!question6.isEmpty()){
                Map<String,Object> mp=new ArrayMap<>();
                mp.put("question",question6);
                List<String> list_options=new ArrayList<>();
                list_options.add(pojo.getOp61());
                list_options.add(pojo.getOp62());
                list_options.add(pojo.getOp63());
                list_options.add(pojo.getOp64());
                mp.put("options",list_options);
                mp.put("ans",pojo.getCop6());
                list_questions.add(mp);
            }

            if(!question7.isEmpty()){
                Map<String,Object> mp=new ArrayMap<>();
                mp.put("question",question7);
                List<String> list_options=new ArrayList<>();
                list_options.add(pojo.getOp71());
                list_options.add(pojo.getOp72());
                list_options.add(pojo.getOp73());
                list_options.add(pojo.getOp74());
                mp.put("options",list_options);
                mp.put("ans",pojo.getCop7());
                list_questions.add(mp);
            }

            if(!question8.isEmpty()){
                Map<String,Object> mp=new ArrayMap<>();
                mp.put("question",question8);
                List<String> list_options=new ArrayList<>();
                list_options.add(pojo.getOp81());
                list_options.add(pojo.getOp82());
                list_options.add(pojo.getOp83());
                list_options.add(pojo.getOp84());
                mp.put("options",list_options);
                mp.put("ans",pojo.getCop8());
                list_questions.add(mp);
            }


            if(!question9.isEmpty()){
                Map<String,Object> mp=new ArrayMap<>();
                mp.put("question",question9);
                List<String> list_options=new ArrayList<>();
                list_options.add(pojo.getOp91());
                list_options.add(pojo.getOp92());
                list_options.add(pojo.getOp93());
                list_options.add(pojo.getOp94());
                mp.put("options",list_options);
                mp.put("ans",pojo.getCop9());
                list_questions.add(mp);
            }

            if(!question10.isEmpty()){
                Map<String,Object> mp=new ArrayMap<>();
                mp.put("question",question10);
                List<String> list_options=new ArrayList<>();
                list_options.add(pojo.getOp101());
                list_options.add(pojo.getOp102());
                list_options.add(pojo.getOp103());
                list_options.add(pojo.getOp104());
                mp.put("options",list_options);
                mp.put("ans",pojo.getCop10());
                list_questions.add(mp);
            }


        }
        return list_questions;
    }

    @Override
    public void onBackPressed() {
        
    }
}
