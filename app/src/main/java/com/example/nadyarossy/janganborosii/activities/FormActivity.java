package com.example.nadyarossy.janganborosii.activities;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nadyarossy.janganborosii.R;
import com.example.nadyarossy.janganborosii.responses.ApiEndPoint;
import com.example.nadyarossy.janganborosii.responses.Data;
import com.example.nadyarossy.janganborosii.responses.ReadResponse;
import com.example.nadyarossy.janganborosii.responses.StatusResponse;
import com.example.nadyarossy.janganborosii.services.ApiClient;

import java.security.SecureRandom;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormActivity extends AppCompatActivity {
    @BindView(R.id.et_tanggal)
    EditText etTanggal;
    @BindView(R.id.et_jpengeluaran)
    EditText etJenisPengeluaran;
    @BindView(R.id.et_harga)
    EditText etHarga;
    @BindView(R.id.btn_simpan)
    Button btnSimpan;

    int dataId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        ButterKnife.bind(this);
        dataId=getIntent().getIntExtra("id",0);

        if (dataId > 0) {
            getData(generateToken(), dataId);

        }

    }


    @OnClick(R.id.btn_simpan)
    public void onClick(View view) {
        int getId = view.getId();
        switch (getId) {
            case R.id.btn_simpan:
                if (dataId > 0) {
                    updateJBData(dataId, etTanggal.getText().toString(), etJenisPengeluaran.getText().toString(), etHarga.getText().toString());
                } else {
                    setData(etTanggal.getText().toString(), etJenisPengeluaran.getText().toString(), etHarga.getText().toString());
                }
                break;

        }
    }

    public void setData(String tanggal, String jenisPengeluaran, String harga) {

        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<StatusResponse> call = apiEndPoint.createJBRequest(tanggal, jenisPengeluaran, harga);

        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {

                final StatusResponse statusResponse = response.body();

                if (statusResponse != null) {
                    Log.d("Response Data ", "Total Data" + statusResponse.getStatus());
                    if (statusResponse.getStatus()) {
                        finish();
                        Toast.makeText(getApplicationContext(), "Data Berhasil Di Tambah", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Data Kosong", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.d("Login : ", "Data Null");
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private String generateToken() {

        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        return bytes.toString();
    }

    public void getData(final String token, int id) {

        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<ReadResponse> call = apiEndPoint.readDataJBRequest(token, id);

        call.enqueue(new Callback<ReadResponse>() {
            @Override
            public void onResponse(Call<ReadResponse> call, Response<ReadResponse> response) {

                final ReadResponse readResponse = response.body();

                if (readResponse != null) {
                    Log.d("Response Data ", "Total Data" + readResponse.getStatus());
                    if (readResponse.getStatus()) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                List<Data> data = readResponse.getDatas();

                                etTanggal.setText(data.get(0).getTanggal());
                                etJenisPengeluaran.setText(data.get(0).getJenisPengeluaran());
                                etHarga.setText(data.get(0).getHarga());
                            }
                        });

                    } else {
                        Toast.makeText(getApplicationContext(), "Data Kosong", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.d("Login : ", "Data Null");
                }
            }

            @Override
            public void onFailure(Call<ReadResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void updateJBData(int id, String tanggal, String jenisPengeluaran, String harga) {

        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<StatusResponse> call = apiEndPoint.updateJBRequest(id, tanggal, jenisPengeluaran, harga);

        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {

                final StatusResponse statusResponse = response.body();

                if (statusResponse != null) {
                    Log.d("Response Data ", "Total Data" + statusResponse.getStatus());
                    if (statusResponse.getStatus()) {
                        finish();
                        Toast.makeText(getApplicationContext(), "Data Berhasil Di Ubah", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Data Kosong", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.d("Login : ", "Data Null");
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
