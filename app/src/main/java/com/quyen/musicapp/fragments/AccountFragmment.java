package com.quyen.musicapp.fragments;


import static com.quyen.musicapp.activities.DangNhapBActivity.taikhoanlg;
import static com.quyen.musicapp.services.Utils.sha256Hash;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quyen.musicapp.R;
import com.quyen.musicapp.activities.DangNhapBActivity;
import com.quyen.musicapp.activities.MainActivity;
import com.quyen.musicapp.services.ApiService;
import com.quyen.musicapp.services.DataService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AccountFragmment extends Fragment  {
    private View view;
    TextView change_password_textview,contact_us_textview,app_info_textview,thongtin;
    TextView account_name_textview,account_email_textview;
    EditText old_password_input,new_password_input,confirm_password_input;
    Button change_password_button,btnDangxuat;
    ImageView imgfacebook,imgzalo,imggmail,imgphone;
    RelativeLayout change_password_layout;
    LinearLayout lhct_layout;
    boolean tt=false;
    boolean tt1=false;
    boolean tt2=false;

    public AccountFragmment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhxa(view);

        account_name_textview.setText(taikhoanlg.getUsername());
        account_email_textview.setText(taikhoanlg.getEmail());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_acount, container, false);
        anhxa(view);

        change_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickchangepass();
            }
        });
        btnDangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Xác nhận đăng xuất!")
                        .setMessage("Bạn chắc chứ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getContext(), DangNhapBActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                                Toast.makeText(getContext(),"Đăng xuất thành công",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
            }
        });
        change_password_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tt==false)
                {
                    change_password_layout.setVisibility(view.VISIBLE);
                    tt=!tt;
                } else {
                    change_password_layout.setVisibility(view.GONE);
                    tt=!tt;
                }
            }
        });
        contact_us_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tt1==false)
                {
                    lhct_layout.setVisibility(view.VISIBLE);
                    tt1=!tt1;
                } else {
                    lhct_layout.setVisibility(view.GONE);
                    tt1=!tt1;
                }
            }
        });
        app_info_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tt2!=false)
                {
                    thongtin.setVisibility(view.VISIBLE);
                    tt2=!tt2;
                } else {
                    thongtin.setVisibility(view.GONE);
                    tt2=!tt2;
                }
            }
        });
        imgfacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/quyenquy2"));
                startActivity(intent);
            }
        });
        imgzalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://zaloapp.com/qr/p/nkdudcfsp17o"));
                startActivity(intent);
            }
        });
        imgphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+84339350910"));
                startActivity(intent);
            }
        });
        imggmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = "quyensinhvien19@gmail.com";
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + emailAddress));
                startActivity(intent);
            }
        });
        return view;
    }

    public void anhxa(View view)
    {
        change_password_textview=view.findViewById(R.id.change_password_textview);
        change_password_button=view.findViewById(R.id.change_password_button);
        change_password_layout=view.findViewById(R.id.change_password_layout);
        account_name_textview =view.findViewById(R.id.account_name_textview);
        account_email_textview=view.findViewById(R.id.account_email_textview);
        old_password_input=view.findViewById(R.id.old_password_input);
        new_password_input=view.findViewById(R.id.new_password_input);
        confirm_password_input=view.findViewById(R.id.confirm_password_input);
        contact_us_textview=view.findViewById(R.id.contact_us_textview);
        app_info_textview=view.findViewById(R.id.app_info_textview);
        thongtin=view.findViewById(R.id.thongtin);
        btnDangxuat=view.findViewById(R.id.btnDangxuat);
        lhct_layout=view.findViewById(R.id.lhct_layout);
        imgfacebook=view.findViewById(R.id.imgfacebook);
        imggmail=view.findViewById(R.id.imggmail);
        imgzalo=view.findViewById(R.id.imgzalo);
        imgphone=view.findViewById(R.id.imgphone);
    }

    public void clickchangepass()
    {
        String oldpass=old_password_input.getText().toString();
        String newpass=new_password_input.getText().toString();
        String confirmpass=confirm_password_input.getText().toString();

        if (oldpass.equals("") || newpass.equals("") || confirmpass.equals(""))
        {
            Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            if(sha256Hash(oldpass).equals(taikhoanlg.getPassword()))
            {
                if (newpass.length() >= 8)
                {
                    if (newpass.equals(confirmpass))
                    {
                        udpass(taikhoanlg.getUsername(),sha256Hash(newpass));
                        Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        old_password_input.setText("");
                        new_password_input.setText("");
                        confirm_password_input.setText("");
                    } else {
                        Toast.makeText(getContext(), "Mật khẩu xác nhận không đúng", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Mật khẩu mới phải lớn hơn 8 ký tự", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
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
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //Toast.makeText(DangNhapActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

}