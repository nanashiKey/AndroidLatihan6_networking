package com.ngopidev.project.androidlatihan6_networking.allViews;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ngopidev.project.androidlatihan6_networking.Const;
import com.ngopidev.project.androidlatihan6_networking.R;
import com.ngopidev.project.androidlatihan6_networking.controlData.HttpHandling;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * created by Irfan Assidiq on 2019-05-28
 * email : assidiq.irfan@gmail.com
 **/
public class DetailData extends AppCompatActivity {
    TextView tvjudul, tvtanggal, tvpenulis, tvdesc;
    Context ctx;
    String datajudul, urldata, judul, penulis, tahun_terbit, keterangan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_data);
        ctx = this;

        Bundle b = getIntent().getExtras();
        if(b != null){
            datajudul = b.getString("judul");
        }else{
            Toast.makeText(ctx, "data tidak ada", Toast.LENGTH_SHORT).show();
        }
        String judul = datajudul.replaceAll(" ", "%20");
        urldata = Const.BASE_URL+Const.DETAIL_DATA+"?judul="+judul;

        tvjudul = findViewById(R.id.tv_judul);
        tvpenulis = findViewById(R.id.tv_penulis);
        tvtanggal = findViewById(R.id.tv_tanggal);
        tvdesc = findViewById(R.id.tv_desc);

        new GetDetailData().execute();

    }

    private class GetDetailData extends AsyncTask<Void, Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandling httpHandling = new HttpHandling();
            String jsonStr = httpHandling.PostDataReq(urldata);
            Log.e("DETAIL_DATA", "response from server :\n"+ jsonStr);

            if (jsonStr!= null){
                try{
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONArray alldata = jsonObject.getJSONArray("data");
                    for (int i =0; i<alldata.length(); i++){
                        JSONObject c = alldata.getJSONObject(i);
                        judul = c.getString("judul");
                        penulis = c.getString("penulis");
                        tahun_terbit = c.getString("tahun_terbit");
                        keterangan = c.getString("description");
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
            tvjudul.setText(judul); tvpenulis.setText(penulis); tvdesc.setText(keterangan);
            tvtanggal.setText(tahun_terbit);
        }
    }
}
