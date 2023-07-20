package com.quyen.musicapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.quyen.musicapp.R;
import com.quyen.musicapp.models.TaiKhoan;
import com.quyen.musicapp.services.ApiService;
import com.quyen.musicapp.services.DataService;
import com.quyen.musicapp.services.OtpTimer;
import com.quyen.musicapp.services.SendMail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FgpassActivity extends AppCompatActivity {
    EditText edtUsername,edtotp;
    Button btnReset,btngoback,btnsendotp;
    private String otp;
    EditText new_password_input,confirm_password_input;
    TaiKhoan taikhoan;
    OtpTimer otpTimer = new OtpTimer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fgpass);
        anhxa();

        btnsendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkreset(edtUsername.getText().toString());
            }
        });
        btngoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FgpassActivity.this,DangNhapBActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otpTimer.isTimerRunning()) {
                        if (edtotp.getText().toString().equals(otp)) {
                        showdialogupdatepass();} else {
                            Toast.makeText(FgpassActivity.this, "Mã Otp không chính xác!", Toast.LENGTH_SHORT).show();
                        }
                } else {
                    Toast.makeText(FgpassActivity.this, "Mã Otp đã hết hạn!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showdialogupdatepass() {
        AlertDialog.Builder builder = new AlertDialog.Builder(FgpassActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_resetpass, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        Button change_password_button = dialogView.findViewById(R.id.change_password_button);
        new_password_input=dialogView.findViewById(R.id.new_password_input);
        confirm_password_input=dialogView.findViewById(R.id.confirm_password_input);


        change_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickchangepass();
            }
        });


        dialog.show();
    }
    public void clickchangepass()
    {
        String newpass=new_password_input.getText().toString();
        String confirmpass=confirm_password_input.getText().toString();

        if (newpass.equals("") || confirmpass.equals(""))
        {
            Toast.makeText(FgpassActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
                if (newpass.length() >= 8)
                {
                    if (newpass.equals(confirmpass))
                    {
                        udpass(taikhoan.getUsername(),newpass);

                    } else {
                        Toast.makeText(FgpassActivity.this, "Mật khẩu xác nhận không đúng", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(FgpassActivity.this, "Mật khẩu mới phải lớn hơn 8 ký tự", Toast.LENGTH_SHORT).show();
                }
        }
    }

    public void udpass(String username,String password)
    {
        DataService dataService = ApiService.getService();
        Call<String> callback = dataService.updateuser(username,password);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //Toast.makeText(DangNhapActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                Toast.makeText(FgpassActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(FgpassActivity.this,DangNhapBActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //Toast.makeText(DangNhapActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void checkreset(String username)
    {
        DataService dataService = ApiService.getService();
        Call<TaiKhoan> callback = dataService.fgpass(username);
        callback.enqueue(new Callback<TaiKhoan>() {
            @Override
            public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
                taikhoan=(TaiKhoan) response.body();
                otp=generateRandomString();
                SendMail sm = new SendMail(FgpassActivity.this, taikhoan.getEmail(),"OTP Reset Password", "Mã xác minh của bạn là: "+otp);
                sm.execute();
                otpTimer.startTimer();
                //sendmail(taikhoan.getEmail());
            }

            @Override
            public void onFailure(Call<TaiKhoan> call, Throwable t) {
                Toast.makeText(FgpassActivity.this, "Tài khoản không tồn tại!", Toast.LENGTH_SHORT).show();
            }
        });
        
    }

    public String generateRandomString() {
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    private void anhxa()
    {
        edtUsername = findViewById(R.id.edtUsername);
        btnsendotp=findViewById(R.id.btnsendotp);
        btnReset =findViewById(R.id.btnreset);
        btngoback=findViewById(R.id.btngoback);
        edtotp=findViewById(R.id.edtotp);
    }



}