package com.example.nadyarossy.janganborosii.responses;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiEndPoint {

    @FormUrlEncoded
    @POST("readJB.php")
    Call<ReadResponse> readJBRequest(@Field("key") String key);

    @FormUrlEncoded
    @POST("readDataJB.php")
    Call<ReadResponse> readDataJBRequest(@Field("key") String key,
                                         @Field("id") int id);

    @FormUrlEncoded
    @POST("createJB.php")
    Call<StatusResponse> createJBRequest(@Field("tanggal") String tanggal,
                                         @Field("jenis_pengeluaran") String jenis_pengeluaran,
                                         @Field("harga") String harga);

    @FormUrlEncoded
    @POST("updateJB.php")
    Call<StatusResponse> updateJBRequest(@Field("id") int id,
                                         @Field("tanggal") String tanggal,
                                         @Field("jenis_pengeluaran") String jenis_pengeluaran,
                                         @Field("harga") String harga);
    @FormUrlEncoded
    @POST("deleteJB.php")
    Call<StatusResponse> deleteJBRequest(@Field("id") int id);
}
