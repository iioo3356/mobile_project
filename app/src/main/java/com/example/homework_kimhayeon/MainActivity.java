package com.example.homework_kimhayeon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button loginBtn, goToSignUpPage, goToProductPage;
    EditText loginId, loginPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        goToSignUpPage = (Button) findViewById(R.id.goToSignUp);
        goToProductPage = (Button) findViewById(R.id.goToProduct);
        loginId = (EditText) findViewById(R.id.loginID);
        loginPwd = (EditText) findViewById(R.id.loginPWD);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences("users", 0);
                String users = prefs.getString("users","");
                if (loginId.getText().toString().equals("")||loginPwd.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"아이디와 비밀번호를 입력하세요",Toast.LENGTH_SHORT).show();
                }
                else{
                    if (!users.equals("")) {
                        String[] userList = users.split("&&&&&");
                        Log.i("main", userList[0]);
                        for (int i = 0; i < userList.length; i++) {
                            String[] userAttr = userList[i].split("_");
                            Log.i("main", userAttr[0]);
                            Log.i("main", userAttr[1]);
                            if (i == userList.length - 1) {
                                if (userAttr[0].equals(loginId.getText().toString())) {
                                    if (userAttr[1].equals(loginPwd.getText().toString())) {
                                        SharedPreferences.Editor editor = prefs.edit();
                                        editor.putString("currentId", userAttr[0]);
                                        editor.putString("currentPwd", userAttr[1]);
                                        editor.putString("currentName", userAttr[2]);
                                        editor.putString("currentTel", userAttr[3]);
                                        editor.putString("currentAddr", userAttr[4]);
                                        editor.apply();

                                        Log.i("main", "user login");
                                        Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "비밀번호가 틀렸습니다", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else if (userAttr[0].equals(loginId.getText().toString())) {
                                Toast.makeText(getApplicationContext(), "기존에 가입한 회원 id 입니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        }});

        goToSignUpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            }
        });
        goToProductPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        SharedPreferences prefs = getSharedPreferences("users", 0);

        SharedPreferences.Editor editor= prefs.edit();
        editor.putString("currentId", "");
        editor.putString("currentPwd", "");
        editor.putString("currentName", "");
        editor.putString("currentTel", "");
        editor.putString("currentAddr", "");
        editor.apply();

    }
}