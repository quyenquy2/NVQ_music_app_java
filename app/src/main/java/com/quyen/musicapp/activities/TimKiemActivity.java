package com.quyen.musicapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.quyen.musicapp.R;
import com.quyen.musicapp.adapters.RecyclerAdapterBaiHatYeuThich;
import com.quyen.musicapp.models.BaiHat;
import com.quyen.musicapp.services.ApiService;
import com.quyen.musicapp.services.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimKiemActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText edt;
    Button gohome;
    ArrayList<BaiHat> BaihatArrayList;
    RecyclerAdapterBaiHatYeuThich adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);

        recyclerView = findViewById(R.id.listviewtimkiem);
        edt = findViewById(R.id.timkiem);
        gohome=findViewById(R.id.gohomend);
        getListBaiHatTimKiem("");
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getListBaiHatTimKiem(s.toString());
            }
        });
        gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getListBaiHatTimKiem(String keyword) {
        if ( BaihatArrayList != null) {
            BaihatArrayList.clear();
            recyclerView.setAdapter(null);
        }
        DataService dataService = ApiService.getService();
        Call<List<BaiHat>> callback = dataService.getBaiHatTheoKeyword(keyword);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                BaihatArrayList = (ArrayList<BaiHat>) response.body();
                if (BaihatArrayList.size() > 0) {
                    adapter = new RecyclerAdapterBaiHatYeuThich(TimKiemActivity.this, BaihatArrayList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(TimKiemActivity.this));
                }
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {
                Log.d("tag", "Load data baihat fail");
            }
        });
    }
}