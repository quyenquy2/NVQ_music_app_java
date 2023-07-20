package com.quyen.musicapp.fragments;

import static com.quyen.musicapp.activities.DangNhapBActivity.taikhoanlg;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class DeXuatFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private RecyclerAdapterBaiHatYeuThich recyclerAdapterBaiHatYeuThich;
    private ArrayList<BaiHat> baiHatArrayList;

    public DeXuatFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_de_xuat, container, false);
        recyclerView = view.findViewById(R.id.fmBaiHatNgauNhien_recyclerView);
        getData();
        return view;
    }

    private void getData(){
        DataService dataService = ApiService.getService();
        Call<List<BaiHat>> callback = dataService.getDeXuat(taikhoanlg.getUsername());
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                baiHatArrayList = (ArrayList<BaiHat>) response.body();
                recyclerAdapterBaiHatYeuThich = new RecyclerAdapterBaiHatYeuThich(getActivity(), baiHatArrayList);
                LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
                recyclerView.setAdapter(recyclerAdapterBaiHatYeuThich);
                recyclerView.setLayoutManager(linearLayout);
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {
                Log.d("tag", "Load data Yeu Thich fail");

            }
        });
    }
}