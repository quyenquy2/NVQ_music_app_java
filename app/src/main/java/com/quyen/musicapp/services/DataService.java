package com.quyen.musicapp.services;

import com.quyen.musicapp.models.Album;
import com.quyen.musicapp.models.BaiHat;
import com.quyen.musicapp.models.Playlist;
import com.quyen.musicapp.models.QuangCao;
import com.quyen.musicapp.models.TaiKhoan;
import com.quyen.musicapp.models.TheLoai;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {

    @GET("bai-hat-banner.php")
    Call<List<QuangCao>> getDataBanner();

    @GET("playlist.php")
    Call<List<Playlist>> getPlaylist();

    @GET("theloai.php")
    Call<List<TheLoai>> getTheLoai();

    @GET("album.php")
    Call<List<Album>> getAlbum();

    @GET("getalluser.php")
    Call<List<TaiKhoan>> gealltuser();

    @FormUrlEncoded
    @POST("boYT.php")
    Call<String> boYeuThich(@Field("Username") String username ,@Field("IdBaiHat") String IdBaiHat);

    @FormUrlEncoded
    @POST("themYT.php")
    Call<String> ThemYeuThich(@Field("Username") String username ,@Field("IdBaiHat") String IdBaiHat);

    @FormUrlEncoded
    @POST("getyeuthich.php")
    Call<List<BaiHat>> getYeuThich(@Field("Username") String username);

    @FormUrlEncoded
    @POST("getdexuat.php")
    Call<List<BaiHat>> getDeXuat(@Field("Username") String username);

    @FormUrlEncoded
    @POST("dangky.php")
    Call<String> register(@Field("Username") String username,@Field("Password") String password,@Field("Email") String Email);

    @FormUrlEncoded
    @POST("timuser.php")
    Call<List<TaiKhoan>> getuser(@Field("Username") String username);

    @FormUrlEncoded
    @POST("fgpass.php")
    Call<TaiKhoan> fgpass(@Field("Username") String username);

    @FormUrlEncoded
    @POST("dangnhap.php")
    Call<TaiKhoan> login(@Field("Username") String username,@Field("Password") String password);

    @FormUrlEncoded
    @POST("uptaikhoan.php")
    Call<String> updateuser(@Field("Username") String username,@Field("Password") String password);

    @FormUrlEncoded
    @POST("danh-sach-bai-hat.php")
    Call<List<BaiHat>> getBaiHatTheoId(@Field("idBaiHat") String idBaiHat);

    @FormUrlEncoded
    @POST("danh-sach-bai-hat.php")
    Call<List<BaiHat>> getBaiHatTheoIdAlbum(@Field("idAlbum") String idAlbum);

    @FormUrlEncoded
    @POST("danh-sach-bai-hat.php")
    Call<List<BaiHat>> getBaiHatTheoIdTheLoai(@Field("idTheLoai") String idTheLoai);

    @FormUrlEncoded
    @POST("danh-sach-bai-hat.php")
    Call<List<BaiHat>> getBaiHatTheoIdPlaylist(@Field("idPlaylist") String idPlaylist);

    @FormUrlEncoded
    @POST("tim-bai-hat.php")
    Call<List<BaiHat>> getBaiHatTheoKeyword(@Field("keyword") String keyword);
}
