package info.androidhive.cardview;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Login extends AppCompatActivity{

    EditText et_username,et_password;
    TextView tv_status;
    RadioGroup radioGroup;
    String Series;
    boolean checked=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_username=(EditText)findViewById(R.id.et_username);
        et_password=(EditText)findViewById(R.id.et_password);
        tv_status=(TextView)findViewById(R.id.tv_status);
        Button button = (Button) findViewById(R.id.login);
        radioGroup=(RadioGroup)findViewById(R.id.rg_credential);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_login:
                        checked=true;
                        tv_status.setText("Current Status : Login");
                        Series="s1e2";
                        break;
                    case R.id.rb_register:
                        checked=true;
                        tv_status.setText("Current Status : Register");
                        Series="s1e1";
                        break;
                }
            }
        });

        // Capture button clicks
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                if(!et_username.getText().toString().isEmpty()&&!et_password.getText().toString().isEmpty()&&checked) {
                    login_authentication(et_username.getText().toString(),et_password.getText().toString(),Series);
                    }
            }
        });
    }

    public void login_authentication(String username,String password,String series){
        SeasonAndEpisode episode=new SeasonAndEpisode(0,username,password,Login.this,series);
        episode.execute();

    }
    int status=0;

    //String url="http://datala.16mb.com/DAcredentials.php


}