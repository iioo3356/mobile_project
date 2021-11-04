package com.example.homework_kimhayeon;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Pattern;

public class SecondActivity extends AppCompatActivity {
    Button signUpBtn;
    EditText signUpId, signUpPassword, signUpName, signUpTel, signUpAddr;
    TextView duplicatedId, wrongPWD;
    RadioButton acceptBtn, declineBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        signUpBtn = (Button) findViewById(R.id.signUpBtn);
        signUpId = (EditText) findViewById(R.id.signUpId);
        signUpPassword = (EditText) findViewById(R.id.signUpPassword);
        signUpName = (EditText) findViewById(R.id.signUpName);
        signUpTel = (EditText) findViewById(R.id.signUpTel);
        signUpAddr = (EditText) findViewById(R.id.signUpAddr);
        declineBtn = (RadioButton) findViewById(R.id.checkDecline);
        acceptBtn = (RadioButton) findViewById(R.id.checkAccept);
        duplicatedId = (TextView) findViewById(R.id.duplicate);
        wrongPWD = (TextView) findViewById(R.id.wrongPwd);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    boolean idChecked = true;
                    boolean pwdChecked = true;
                    boolean acceptChecked = true;
                    SharedPreferences prefs = getSharedPreferences("users", MODE_PRIVATE);
                    String users = prefs.getString("users","");
                    String[] userList = users.split("&&&&&");
                    for (String user : userList){
                        String[] userAttr = user.split("_");
                        if (userAttr[0].equals(signUpId.getText().toString())) {
                            idChecked = false;
                        }

                    }
                    if (!idChecked){
                        duplicatedId.setVisibility(View.VISIBLE);
                    }
                    else{
                        duplicatedId.setVisibility(View.GONE);
                    }
                    if (!Pattern.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[$@$!%*#?]).{8,15}.$",signUpPassword.getText().toString())){
                        wrongPWD.setVisibility(View.VISIBLE);
                        pwdChecked = false;
                    }
                    else{
                        wrongPWD.setVisibility(View.GONE);
                    }
                    if (signUpId.getText().toString().equals("") || signUpPassword.getText().toString().equals("")
                            || signUpName.getText().toString().equals("") || signUpTel.getText().toString().equals("")||
                            signUpAddr.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"모든 항목을 작성해주세요.",Toast.LENGTH_SHORT).show();
                    }
                    if (!acceptBtn.isChecked()){
                        Toast.makeText(getApplicationContext(),"동의 버튼에 체크하지 않으면 넘어갈 수 없습니다.",Toast.LENGTH_SHORT).show();
                        acceptChecked = false;
                    }

                    if (idChecked && pwdChecked && acceptChecked){
                        SharedPreferences.Editor editor = prefs.edit();
                        String input = users + signUpId.getText().toString() +"_" + signUpPassword.getText().toString()+"_"
                                +signUpName.getText().toString()+"_"+signUpTel.getText().toString()+"_"+signUpAddr.getText().toString()+"&&&&&";
                        editor.putString("users", input);
                        editor.apply();
                        Log.i("second","user signUp");
                        Log.i("second","id"+signUpId.getText().toString());
                        Log.i("second","pwd"+signUpPassword.getText().toString());
                        Toast.makeText(getApplicationContext(),"회원가입 성공",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });



    }
}