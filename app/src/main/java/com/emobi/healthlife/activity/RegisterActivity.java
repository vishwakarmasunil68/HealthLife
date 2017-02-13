package com.emobi.healthlife.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.emobi.healthlife.R;
import com.emobi.healthlife.pojo.RegisterPOJO;
import com.emobi.healthlife.webservices.WebServiceBase;
import com.emobi.healthlife.webservices.WebServicesCallBack;
import com.emobi.healthlife.webservices.WebServicesUrls;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,WebServicesCallBack,DatePickerDialog.OnDateSetListener {
    public static final String REGISTER_API="register_api";
    public final String TAG=getClass().getSimpleName();

    @BindView(R.id.et_login_id)
    EditText et_login_id;
    @BindView(R.id.et_login_name)
    EditText et_login_name;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_mobile)
    EditText et_mobile;
    @BindView(R.id.tv_dob)
    TextView tv_dob;
    @BindView(R.id.radio_sex)
    RadioGroup radio_sex;
    @BindView(R.id.btn_register)
    Button btn_register;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Register");

        btn_register.setOnClickListener(this);
        tv_dob.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.btn_register:
                RegisterUser();
                break;
            case R.id.tv_dob:
                OpenCalendar();
                break;

        }
    }

    public void OpenCalendar(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                RegisterActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    public void RegisterUser(){
        String login_id=et_login_id.getText().toString();
        String name=et_login_name.getText().toString();
        String email=et_email.getText().toString();
        String password=et_password.getText().toString();
        String mobile=et_mobile.getText().toString();
        String dob=tv_dob.getText().toString();

        if(login_id.isEmpty()||name.isEmpty()||email.isEmpty()||password.isEmpty()||
                mobile.isEmpty()||dob.equals("D.O.B")){
            Toast.makeText(getApplicationContext(),"Please Enter Details Properly",Toast.LENGTH_LONG).show();
        }
        else{
            int sex_id=radio_sex.getCheckedRadioButtonId();
            RadioButton radioButton=(RadioButton) findViewById(sex_id);
            Toast.makeText(RegisterActivity.this,
                    radioButton.getText(), Toast.LENGTH_SHORT).show();
            CallRegisterAPI(login_id,name,email,password,mobile,dob,radioButton.getText().toString());
        }
    }
    public void CallRegisterAPI(String login_id,String name,String email,String pass,
                                String mobile,String dob,String sex){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("login_id", login_id));
        nameValuePairs.add(new BasicNameValuePair("login_name", name));
        nameValuePairs.add(new BasicNameValuePair("email", email));
        nameValuePairs.add(new BasicNameValuePair("mobileno", mobile));
        nameValuePairs.add(new BasicNameValuePair("password", pass));
        nameValuePairs.add(new BasicNameValuePair("dob", dob));
        nameValuePairs.add(new BasicNameValuePair("sex", sex));
        new WebServiceBase(nameValuePairs, this, REGISTER_API).execute(WebServicesUrls.REGISTER_URL);
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String resposne=msg[1];
        switch (apicall){
            case REGISTER_API:
                    parseRegisterResponse(resposne);
                break;
        }
    }
    public void parseRegisterResponse(String response){
        Log.d(TAG,"response:-"+response);
        Gson gson=new Gson();
        RegisterPOJO pojo=gson.fromJson(response,RegisterPOJO.class);
        if(pojo!=null){
            try{
                if(pojo.getSuccess().equals("true")){
                    Toast.makeText(getApplicationContext(),"Registration Successfull",Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Registration Failed",Toast.LENGTH_LONG).show();

                }
            }
            catch (Exception e){
                Log.d(TAG,e.toString());
            }
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        tv_dob.setText(date);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
