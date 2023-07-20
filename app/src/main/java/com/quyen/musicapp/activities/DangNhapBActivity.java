package com.quyen.musicapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.quyen.musicapp.R;
import com.quyen.musicapp.models.BaiHat;
import com.quyen.musicapp.models.TaiKhoan;
import com.quyen.musicapp.services.ApiService;
import com.quyen.musicapp.services.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangNhapBActivity extends AppCompatActivity {
    TextInputLayout edtTaiKhoan, edtMatKhau;
    Button btnDangNhap, btnDangKy;
    TextView fgpass;
    public static TaiKhoan taikhoanlg;
    public static ArrayList<BaiHat> ListYT=new ArrayList<>();
    ArrayList<TaiKhoan> listuser;

    private static final int TIME_INTERVAL = 2000; // time between two back presses
    private long mBackPressed;

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            finishAffinity();
            return;
        } else {
            Toast.makeText(this, "Nhấn lần nữa để thoát!", Toast.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap_bactivity);
        init();

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=edtTaiKhoan.getEditText().getText().toString().trim();
                String password=edtMatKhau.getEditText().getText().toString().trim();
                if (username.equals("") || password.equals("")){
                    Toast.makeText(DangNhapBActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else
                {
                    clicklogin(username,password);
                    //onclicklogin(username,password);
                }

            }
        });
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DangNhapBActivity.this,DangKyBActivity.class);
                startActivity(intent);
            }
        });
        fgpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DangNhapBActivity.this,FgpassActivity.class);
                startActivity(intent);
            }
        });
    }
    public void init()
    {
        edtTaiKhoan=findViewById(R.id.edttkdn);
        edtMatKhau=findViewById(R.id.edtmkdn);
        btnDangNhap=findViewById(R.id.btndn);
        btnDangKy=findViewById(R.id.btndk);
        fgpass=findViewById(R.id.textViewquenmatkhau);
    }
    public void clicklogin(String username,String password)
    {
        DataService dataService = ApiService.getService();
        Call<List<TaiKhoan>> callback = dataService.getuser(username);
        callback.enqueue(new Callback<List<TaiKhoan>>() {
            @Override
            public void onResponse(Call<List<TaiKhoan>> call, Response<List<TaiKhoan>> response) {
                listuser = (ArrayList<TaiKhoan>) response.body();
                if (listuser != null && listuser.size() > 0) {
                    taikhoanlg=listuser.get(0);

                    if (taikhoanlg.getPassword().equals(password)) {
                            //udpass(username, password);
                            getDataYT(taikhoanlg);
                            Toast.makeText(DangNhapBActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(DangNhapBActivity.this,MainActivity.class);
                            startActivity(intent);
                    } else {
                        Toast.makeText(DangNhapBActivity.this, "Tài khoản hoặc mật khẩu sai!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DangNhapBActivity.this, "Tài khoản hoặc mật khẩu sai!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TaiKhoan>> call, Throwable t) {
                Toast.makeText(DangNhapBActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void udpass(String username,String password)
    {
        DataService dataService = ApiService.getService();
        Call<String> callback = dataService.updateuser(username,password);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //Toast.makeText(DangNhapActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //Toast.makeText(DangNhapActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getDataYT(TaiKhoan tk){
        DataService dataService = ApiService.getService();
        Call<List<BaiHat>> callback = dataService.getYeuThich(tk.getUsername());
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                ListYT = (ArrayList<BaiHat>) response.body();
                System.out.println("Load data Yeu Thich thành công");
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {
                System.out.println("Load data Yeu Thich fail");

            }
        });
    }
}