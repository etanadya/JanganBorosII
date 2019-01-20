package com.example.nadyarossy.janganborosii;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nadyarossy.janganborosii.activities.FormActivity;
import com.example.nadyarossy.janganborosii.adapters.DataItemAdapter;
import com.example.nadyarossy.janganborosii.responses.ApiEndPoint;
import com.example.nadyarossy.janganborosii.responses.Data;
import com.example.nadyarossy.janganborosii.responses.ReadResponse;
import com.example.nadyarossy.janganborosii.responses.StatusResponse;
import com.example.nadyarossy.janganborosii.services.ApiClient;

import java.security.SecureRandom;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;
    @BindView(R.id.lv_data_list)
    ListView lvDataList;

    List<Data> dataItemList;
    DataItemAdapter dataItemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Masukkan adapter ke listView
        dataItemAdapter = new DataItemAdapter(this, dataItemList);
        registerForContextMenu(lvDataList);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FormActivity.class);
                startActivity(i);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        getAllData(generateToken());
        dataItemAdapter.notifyDataSetChanged();
    }


    private String generateToken() {

        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        return bytes.toString();
    }

    public void getAllData(final String token) {

        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<ReadResponse> call = apiEndPoint.readJBRequest(token);

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

                                dataItemList = readResponse.getDatas();

                                lvDataList.setAdapter(new DataItemAdapter(getApplicationContext(), dataItemList));
                                dataItemAdapter.notifyDataSetChanged();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tambah_data, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int getId = item.getItemId();
        switch (getId){
            case R.id.menu_create:
                startActivity(new Intent(this, FormActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int contextMenuId=menuInfo.position;
        System.out.println("Menu Id" +contextMenuId);
        String dataId=dataItemList.get(contextMenuId).getId();
        int menuId = item.getItemId();
        switch (menuId) {
            case R.id.menu_edit:
                Intent in=new Intent(this, FormActivity.class);
                in.putExtra("id",dataId);
                startActivity(in);
                break;
            case R.id.menu_hapus:
                deleteJBData(Integer.parseInt(dataId));
                break;
        }
        return super.onContextItemSelected(item);
    }
    public void deleteJBData(final int id) {

        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<StatusResponse> call = apiEndPoint.deleteJBRequest(id);

        call.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {

                final StatusResponse StatusResponse = response.body();

                if (StatusResponse != null) {
                    Log.d("Response Data ", "Total Data" + StatusResponse.getStatus());
                    if (StatusResponse.getStatus()) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                                getAllData(generateToken());
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
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();

            }
        });
    }

}