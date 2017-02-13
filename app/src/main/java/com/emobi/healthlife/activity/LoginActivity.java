package com.emobi.healthlife.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.emobi.healthlife.R;
import com.emobi.healthlife.pojo.RegisterPOJO;
import com.emobi.healthlife.webservices.WebServiceBase;
import com.emobi.healthlife.webservices.WebServicesCallBack;
import com.emobi.healthlife.webservices.WebServicesUrls;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, WebServicesCallBack {
    private final String TAG=getClass().getSimpleName();
    private final static String LOGIN_API="login_api";

    @BindView(R.id.et_user_id)
    EditText et_user_id;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.tv_forgot_password)
    TextView tv_forgot_password;
    @BindView(R.id.tv_register_user)
    TextView tv_register_user;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");


        btn_login.setOnClickListener(this);
        tv_forgot_password.setOnClickListener(this);
        tv_register_user.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_login:
                LoginUser();
                break;
            case R.id.tv_forgot_password:
                break;
            case R.id.tv_register_user:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
        }
    }

    public void LoginUser() {
        String login_id=et_user_id.getText().toString();
        String password=et_password.getText().toString();
        if(login_id.isEmpty()||password.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please Enter Details Properly",Toast.LENGTH_LONG).show();
        }
        else{
            callLoginAPI(login_id,password);
        }
    }
    public void callLoginAPI(String login_id,String password){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("login_id", login_id));
        nameValuePairs.add(new BasicNameValuePair("password", password));
        new WebServiceBase(nameValuePairs, this, LOGIN_API).execute(WebServicesUrls.LOGIN_URL);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        switch (apicall){
            case LOGIN_API:
                    parseLoginData(response);
                break;
        }
    }
    public void parseLoginData(String response){
        Log.d(TAG,"response:-"+response);
        Gson gson=new Gson();
        RegisterPOJO pojo=gson.fromJson(response,RegisterPOJO.class);
        if(pojo!=null){
            try{
                if(pojo.getSuccess().equals("true")){
                    Toast.makeText(getApplicationContext(),"Login Successfull",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                }
                else{
                    Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_LONG).show();

                }
            }
            catch (Exception e){
                Log.d(TAG,e.toString());
                Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_LONG).show();
            }
        }
    }
}
