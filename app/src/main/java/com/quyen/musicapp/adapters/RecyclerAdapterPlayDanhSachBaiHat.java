package com.quyen.musicapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quyen.musicapp.R;
import com.quyen.musicapp.activities.PlayNhacActivity;
import com.quyen.musicapp.models.BaiHat;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerAdapterPlayDanhSachBaiHat extends RecyclerView.Adapter<RecyclerAdapterPlayDanhSachBaiHat.ViewHolder> {
    private Context context;
    private ArrayList<BaiHat> baiHatArrayList;

    public RecyclerAdapterPlayDanhSachBaiHat(Context context, ArrayList<BaiHat> baiHatArrayList) {
        this.context = context;
        this.baiHatArrayList = baiHatArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_play_danh_sach_bai_hat, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerAdapterPlayDanhSachBaiHat.ViewHolder holder, int position) {
        BaiHat baiHat = baiHatArrayList.get(position);
        holder.txtCaSi.setText(baiHat.getTenCaSi() );
        holder.txtBaiHat.setText(baiHat.getTenBaiHat());
        holder.txtIndex.setText(String.valueOf(position+1));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return baiHatArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtBaiHat, txtCaSi, txtIndex;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtBaiHat = itemView.findViewById(R.id.itemPlayDanhSachBaiHat_txtBaiHat);
            txtIndex = itemView.findViewById(R.id.itemPlayDanhSachBaiHat_indexBaiHat);
            txtCaSi = itemView.findViewById(R.id.itemPlayDanhSachBaiHat_txtCaSi);
        }
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
