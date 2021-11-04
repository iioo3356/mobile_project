package com.example.homework_kimhayeon;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {
    ArrayList<ViewItem> itemList = new ArrayList<>();
    private ActivityResultLauncher<Intent> resultLauncher;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    Button addBtn;
    Button deleteObject;
    Button showBtn;
    Button goToSignUp2;
    ImageView imgUpload;
    Button objectUpload;
    LinearLayout showUserLayout;
    LinearLayout showNoLayout;
    LinearLayout addObjectLayout;
    TextView showId;
    TextView showName;
    TextView showTel;
    TextView showAddr;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        bindList();

        deleteObject = (Button) findViewById(R.id.deleteObject);
        showBtn = (Button) findViewById(R.id.showBtn);
        goToSignUp2 = (Button) findViewById(R.id.goToSignUp2);
        imgUpload = (ImageView) findViewById(R.id.img_upload);
        objectUpload = (Button) findViewById(R.id.uploadObject);
        addBtn = (Button) findViewById(R.id.addObject);
        deleteObject = (Button) findViewById(R.id.deleteObject);
        showNoLayout = (LinearLayout) findViewById(R.id.noSignUp);
        showUserLayout = (LinearLayout) findViewById(R.id.showUser);
        addObjectLayout = (LinearLayout) findViewById(R.id.addObjectLayout);
        showId = (TextView) findViewById(R.id.showId);
        showName = (TextView) findViewById(R.id.showName);
        showTel = (TextView) findViewById(R.id.showTel);
        showAddr = (TextView) findViewById(R.id.showAddr);

        goToSignUp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            }
        });

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showUserLayout.getVisibility()==View.VISIBLE){
                    showBtn.setBackgroundColor(Color.parseColor("#6200ee"));
                    showUserLayout.setVisibility(View.GONE);
                }
                else if (showNoLayout.getVisibility()==View.VISIBLE){
                    showBtn.setBackgroundColor(Color.parseColor("#6200ee"));
                    showNoLayout.setVisibility(View.GONE);
                }
                else{
                    showBtn.setBackgroundColor(Color.GRAY);
                    addBtn.setBackgroundColor(Color.parseColor("#6200ee"));
                    addObjectLayout.setVisibility(View.GONE);
                    SharedPreferences prefs = getSharedPreferences("users", MODE_PRIVATE);
                    String id = prefs.getString("currentId", "");
                    String tel = prefs.getString("currentTel", "");
                    String name = prefs.getString("currentName", "");
                    String addr = prefs.getString("currentAddr", "");
                    Log.i("third", id);

                    if (id.equals("")) {
                        showUserLayout.setVisibility(View.GONE);
                        showNoLayout.setVisibility(View.VISIBLE);
                    } else {
                        showUserLayout.setVisibility(View.VISIBLE);
                        showNoLayout.setVisibility(View.GONE);
                        showId.setText("아이디 : " + id);
                        showName.setText("이름 : " + name);
                        showTel.setText("전화번호 : " + tel);
                        showAddr.setText("주소 : " + addr);
                    }
                }
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addObjectLayout.getVisibility()==View.GONE){
                    showUserLayout.setVisibility(View.GONE);
                    showNoLayout.setVisibility(View.GONE);
                    addObjectLayout.setVisibility(View.VISIBLE);
                    addBtn.setBackgroundColor(Color.GRAY);
                    showBtn.setBackgroundColor(Color.parseColor("#6200ee"));
                }
                else{
                    addObjectLayout.setVisibility(View.GONE);
                    addBtn.setBackgroundColor(Color.parseColor("#6200ee"));
                }

            }
        });

        imgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                resultLauncher.launch(intent);
            }
        });

        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Log.i("third", "result " + result);
                            Intent intent = result.getData();
                            Log.i("third", "intent " + intent);
                            uri = intent.getData();
                            imgUpload.setImageURI(uri);
                            Log.i("third", "uri " + uri);
                        }
                    }
                }
        );


        objectUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText objectName = (EditText) findViewById(R.id.objectName);
                EditText objectPrice = (EditText) findViewById(R.id.objectPrice);
                if (!objectName.getText().toString().equals("") && !objectPrice.getText().toString().equals("") && uri!=null){
                    Log.i("third", "name " + objectName.getText().toString());
                    Log.i("third", "price " + objectPrice.getText().toString());
                    Log.i("third", "uri " + uri);
                    itemList.add(new ViewItem(
                            itemList.size() + 1, objectName.getText().toString(), uri, Integer.parseInt(objectPrice.getText().toString()),false)
                    );
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(getApplicationContext(), "상품 추가 실패", Toast.LENGTH_SHORT).show();
                }

            }
        });

        deleteObject.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();
                for (int i=1;i<=itemList.size();i++){
                    ArrayList<ViewItem> removes = adapter.getRemoveItems();
                    Log.i("third", "remove item size: "+removes.size());
                    for (ViewItem item:removes){
                        itemList.remove(item);
                        Log.i("third", "remove item name: "+item.getName());
                    }
                    adapter.notifyDataSetChanged();;
                }
            }
        });
    }
    private void bindList() {
        itemList.add(new ViewItem(1, "불닭볶음면", getUri(R.drawable.buldak), 1500,false));
        itemList.add(new ViewItem(2, "튀김우동", getUri(R.drawable.udong), 1500,false));
        itemList.add(new ViewItem(3, "열라면", getUri(R.drawable.yeolramen), 990,false));
        itemList.add(new ViewItem(4, "육개장", getUri(R.drawable.yukgaejang), 1200,false));
        itemList.add(new ViewItem(5, "신라면", getUri(R.drawable.sinramen), 1500,false));
        itemList.add(new ViewItem(6, "짜왕", getUri(R.drawable.jjawang), 1200,false));
        itemList.add(new ViewItem(7, "팔도비빔면", getUri(R.drawable.bibim), 1500,false));

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter(itemList);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }


    public static Uri getUri(int res) {
        return Uri.parse("android.resource://com.example.homework_kimhayeon/" + res);
    }
}