package com.ngopidev.project.androidlatihan6_networking.allViews;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ngopidev.project.androidlatihan6_networking.Const;
import com.ngopidev.project.androidlatihan6_networking.R;
import com.ngopidev.project.androidlatihan6_networking.controlData.HttpHandling;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * created by Irfan Assidiq on 2019-05-28
 * email : assidiq.irfan@gmail.com
 **/
public class TampilData extends AppCompatActivity {
    ArrayList<HashMap<String, String>> dataList;
    ListView lv_;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tampil_data);
        dataList = new ArrayList<>();
        lv_ = findViewById(R.id.lv_);
        FloatingActionButton fab = findViewById(R.id.tambahdata);

        new GetDataFromServer().execute();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TampilData.this,
                                            TambahData.class));
            }
        });

        lv_.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle b = new Bundle();
                b.putString("judul", dataList.get(i).get("judul"));
                Intent inten = new Intent(TampilData.this, DetailData.class);
                inten.putExtras(b) ;
                startActivity(inten);
            }
        });


    }

    private class GetDataFromServer extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandling httpHandling = new HttpHandling();
            String url = Const.BASE_URL+Const.TAMPIL_DATA;

            String jsonStr = httpHandling.PostDataReq(url);
            Log.e("FROM_SERVER", "data dari server :\n"+jsonStr);

            if(jsonStr != null){
                try{
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONArray allthing = jsonObject.getJSONArray("data");

                    for (int i = 0; i < allthing.length(); i++){
                        JSONObject c = allthing.getJSONObject(i);
                        String judul = c.getString("judul");
                        String tanggal = c.getString("tahun_terbit");
                        String penulis = c.getString("penulis");

                        HashMap<String, String> bukuu = new HashMap<>();
                        bukuu.put("judul", judul);
                        bukuu.put("penulis", penulis);
                        bukuu.put("tahun_terbit", tanggal);
                        dataList.add(bukuu);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ListAdapter adapter = new SimpleAdapter(getApplicationContext(),
                    dataList, R.layout.list_item_customs,
                    new String[]{"judul", "penulis", "tahun_terbit"},
                    new int[]{R.id.tv_judul, R.id.tv_penulis, R.id.tv_tanggal});
            lv_.setAdapter(adapter);
        }
    }
}
