package com.example.nadyarossy.janganborosii.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nadyarossy.janganborosii.R;
import com.example.nadyarossy.janganborosii.responses.Data;

import java.util.List;

public class DataItemAdapter extends ArrayAdapter<Data> {

    private List<Data> dataList;
    private Context context;
    private LayoutInflater layoutInflater;

    public DataItemAdapter(@NonNull Context context, List<Data> datas) {
        super(context, R.layout.item_data ,datas);
        this.context=context;
        dataList=datas;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Nullable
    @Override
    public Data getItem(int position) {
        return dataList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=convertView;

        if(view==null){

            view=layoutInflater.inflate(R.layout.item_data,null);
        }
        TextView tanggal=view.findViewById(R.id.tv_tanggal);
        TextView jenisPengeluaran=view.findViewById(R.id.tv_jpeng);
        TextView harga=view.findViewById(R.id.tv_harga);

        Data data=getItem(position);

        //Kasih nilai ke Person_item layout

        tanggal.setText(data.getTanggal());
        jenisPengeluaran.setText(data.getJenisPengeluaran());
        harga.setText(data.getHarga());

        return view;
    }
}
