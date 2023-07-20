package com.quyen.musicapp.services;

import static com.quyen.musicapp.activities.DangNhapBActivity.ListYT;
import static com.quyen.musicapp.activities.DangNhapBActivity.taikhoanlg;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.quyen.musicapp.models.BaiHat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Utils {
    public static boolean chechYT(BaiHat baiHat)
    {
        for (BaiHat i: ListYT) {
            if (baiHat.getIdBaiHat().equals(i.getIdBaiHat())) return true;
        }
        return false;
    }
    public static void xoaYT(BaiHat baiHat)
    {
        for (int i=0;i<ListYT.size();i++)
        {
            if (baiHat.getIdBaiHat().equals(ListYT.get(i).getIdBaiHat()))
                ListYT.remove(i);
        }
    }

}
