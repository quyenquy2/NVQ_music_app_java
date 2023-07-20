package com.quyen.musicapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import com.quyen.musicapp.R;
import com.quyen.musicapp.models.TaiKhoan;
import com.quyen.musicapp.services.ApiService;
import com.quyen.musicapp.services.DataService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangKyBActivity extends AppCompatActivity {
    TextInputLayout edtDKTaiKhoan,edtDKMatKhau,edtDKEmail,edtDKLMatKhau;
    Button btnDKDangKy;
    ImageView btnDKDangNhap;
    ArrayList<TaiKhoan> listuser;
    String taikhoan,matkhau,email,lmatkhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_bactivity);
        init();

        btnDKDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DangKyBActivity.this,DangNhapBActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnDKDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taikhoan = edtDKTaiKhoan.getEditText().getText().toString().trim();
                matkhau = edtDKMatKhau.getEditText().getText().toString().trim();
                email = edtDKEmail.getEditText().getText().toString().trim();
                lmatkhau = edtDKLMatKhau.getEditText().getText().toString().trim();
                clickregiter();
            }
        });
    }
    private void init()
    {
        edtDKEmail = findViewById(R.id.emaildangkyB);
        edtDKMatKhau = findViewById(R.id.edtmatkhauB);
        edtDKTaiKhoan = findViewById(R.id.edttaikhoanB);
        edtDKLMatKhau = findViewById(R.id.edtmatkhauxnB);
        btnDKDangKy = findViewById(R.id.btnDKdangKyB);
        btnDKDangNhap = findViewById(R.id.imageClose);
    }
    public void clickregiter()
    {
        String regex="^\\w+[a-z0-9]*@\\w+mail.com$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        DataService dataService = ApiService.getService();
        Call<List<TaiKhoan>> callback = dataService.gealltuser();
        callback.enqueue(new Callback<List<TaiKhoan>>() {
            @Override
            public void onResponse(Call<List<TaiKhoan>> call, Response<List<TaiKhoan>> response) {
                listuser = (ArrayList<TaiKhoan>) response.body();



                TaiKhoan taikhoan1 = new TaiKhoan(taikhoan,matkhau,email);
                if (matkhau.length() < 8) {
                    Toast.makeText(DangKyBActivity.this, "Mật khẩu cần ít nhất 8 ký tự", Toast.LENGTH_SHORT).show();}
                else {
                    if (taikhoan.equals("") || matkhau.equals("") || email.equals("") || lmatkhau.equals("")) {
                        Toast.makeText(DangKyBActivity.this, "Bạn chưa nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        Log.e("Thông báo : ", "Bạn chưa nhập đầy đủ thông tin");
                    } else {
                        if (matcher.find()) {
                            if (matkhau.equals(lmatkhau)) {
                                String datatentaikhoan = null;
                                for (int i=0;i<listuser.size();i++) {
                                    if(listuser.get(i).getUsername().equals(taikhoan))
                                    {datatentaikhoan =taikhoan;break;
                                    }
                                    else {datatentaikhoan = "";}
                                }

                                if (datatentaikhoan.equals(taikhoan)) {
                                    Toast.makeText(DangKyBActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();

                                } else {
                                    String dataemail = null;
                                    for (int i=0;i<listuser.size();i++) {
                                        if(listuser.get(i).getEmail().equals(email))
                                        {dataemail =email;break;
                                        }
                                        else {dataemail = "";}
                                    }
                                    if (dataemail.equals(email)) {
                                        Toast.makeText(DangKyBActivity.this, "Email đã được đăng ký", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        apiregister(taikhoan,matkhau,email);

                                        edtDKTaiKhoan.getEditText().setText("");
                                        edtDKMatKhau.getEditText().setText("");
                                        edtDKEmail.getEditText().setText("");
                                        edtDKLMatKhau.getEditText().setText("");
                                    }

                                }


                            } else {
                                Toast.makeText(DangKyBActivity.this, "Mật khẩu xác nhận không trùng", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(DangKyBActivity.this, "Định dạng mail không đúng", Toast.LENGTH_SHORT).show();
                        }
                        //Nếu đầy đủ thông tin
                    }
                }


                //Toast.makeText(DangKyActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<TaiKhoan>> call, Throwable t) {
                //Toast.makeText(DangKyActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void apiregister(String username,String password,String email)
    {
        DataService dataService = ApiService.getService();
        Call<String> callback = dataService.register(username,password,email);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(DangKyBActivity.this, "Đăng ký thành công ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(DangKyBActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }
}