package com.quyen.musicapp.adapters;

import static com.quyen.musicapp.activities.DangNhapBActivity.ListYT;
import static com.quyen.musicapp.activities.DangNhapBActivity.taikhoanlg;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.quyen.musicapp.MyApplication;
import com.quyen.musicapp.R;
import com.quyen.musicapp.activities.PlayNhacActivity;
import com.quyen.musicapp.models.BaiHat;
import com.quyen.musicapp.services.ApiService;
import com.quyen.musicapp.services.DataService;
import com.quyen.musicapp.services.MusicService;
import com.quyen.musicapp.services.Utils;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerAdapterBaiHatYeuThich extends RecyclerView.Adapter<RecyclerAdapterBaiHatYeuThich.ViewHolder> {
    private Context context;
    private ArrayList<BaiHat> baiHatArrayList;

    public RecyclerAdapterBaiHatYeuThich(Context context, ArrayList<BaiHat> baiHatArrayList) {
        this.context = context;
        this.baiHatArrayList = baiHatArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_bai_hat_ngau_nhien, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerAdapterBaiHatYeuThich.ViewHolder holder, int position) {
        BaiHat baiHat = baiHatArrayList.get(position);
        holder.txtCaSi.setText(baiHat.getTenCaSi() );
        holder.txtBaiHat.setText(baiHat.getTenBaiHat());

        if (Utils.chechYT(baiHat)) {
            holder.imgLove.setImageResource(R.drawable.iconloved);
        }

        Picasso.get().load(baiHat.getHinhAnhBaiHat()).into(holder.imgBaiHat);

        holder.imgLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.chechYT(baiHat))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Xác nhận bỏ BHYT?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //bỏ yêu thích
                            DataService dataService= ApiService.getService();
                            Call<String> callback=dataService.boYeuThich(taikhoanlg.getUsername(),baiHat.getIdBaiHat());
                            callback.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    Toast.makeText(context, "bỏ thành công", Toast.LENGTH_SHORT).show();
                                    Utils.xoaYT(baiHat);
                                    holder.imgLove.setImageResource(R.drawable.iconlove);
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(context, "bỏ thất bại", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Không làm gì cả
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Xác nhận thêm BHYT?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //thêm yêu thích
                            DataService dataService=ApiService.getService();
                            Call<String> callback=dataService.ThemYeuThich(taikhoanlg.getUsername(),baiHat.getIdBaiHat());
                            callback.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                    ListYT.add(baiHat);
                                    holder.imgLove.setImageResource(R.drawable.iconloved);
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Không làm gì cả
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }

        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.posPlay=position;
                Intent intent = new Intent(context, PlayNhacActivity.class);
                context.startActivity(intent);

                Intent intent1=new Intent(context,MusicService.class);
                intent1.putExtra("position",position);
                intent1.putExtra("baiHat",  baiHat);
                intent1.putExtra("listBaiHat", baiHatArrayList);
                context.startService(intent1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return baiHatArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBaiHat, imgLove;
        TextView txtBaiHat, txtCaSi;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgBaiHat = itemView.findViewById(R.id.itemBaiHatNgauNhien_imgBaiHat);
            imgLove = itemView.findViewById(R.id.itemBaiHatNgauNhien_txtCaSi_icLove);
            txtBaiHat = itemView.findViewById(R.id.itemBaiHatNgauNhien_txtBaiHat);
            txtCaSi = itemView.findViewById(R.id.itemBaiHatNgauNhien_txtCaSi);
        }
    }
}
