package com.ngopidev.project.androidlatihan6_networking.allViews;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.ngopidev.project.androidlatihan6_networking.Const;
import com.ngopidev.project.androidlatihan6_networking.R;
import com.ngopidev.project.androidlatihan6_networking.controlData.HttpHandling;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


/**
 * created by Irfan Assidiq on 2019-05-28
 * email : assidiq.irfan@gmail.com
 **/
public class TambahData extends AppCompatActivity implements View.OnClickListener {

    EditText et_judul, et_penulis, et_tanggal, et_desc;
    Button btn;
    String judul, penulis, tanggal, desc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_data);
        et_judul = findViewById(R.id.et_judul);
        et_penulis = findViewById(R.id.et_penulis);
        et_tanggal = findViewById(R.id.et_tanggal);
        et_desc = findViewById(R.id.et_keterangan);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(this);

        //membuat pilihan dari calendar
        et_tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentDate = Calendar.getInstance();
                final int[] mYear = {mcurrentDate.get(Calendar.YEAR)};
                final int[] mMonth = {mcurrentDate.get(Calendar.MONTH)};
                final int[] mDay = {mcurrentDate.get(Calendar.DAY_OF_MONTH)};

                DatePickerDialog mDatePicker = new DatePickerDialog(
                    TambahData.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, i);
                        myCalendar.set(Calendar.MONTH, i1);
                        myCalendar.set(Calendar.DAY_OF_MONTH, i2);
                        String myFormat = "yyyy-MM-dd";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
                        et_tanggal.setText(sdf.format(myCalendar.getTime()));

                        mDay[0] = i2;
                        mMonth[0] = i1;
                        mYear[0] = i;
                    }
                }, mYear[0], mMonth[0], mDay[0]);
                mDatePicker.show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        judul = et_judul.getText().toString();
        tanggal = et_tanggal.getText().toString();
        penulis = et_penulis.getText().toString();
        desc = et_desc.getText().toString();

        if(judul == null || judul.equals("") ||
            penulis == null || penulis.equals("") ||
            tanggal == null || tanggal.equals("") ||
            desc == null || desc.equals("")) {
            Toast.makeText(TambahData.this, "Please fill all data",
                    Toast.LENGTH_SHORT).show();
        }else{
            //eksekusi methode penambah data
            new AddNewData().execute();
        }

    }

    private class AddNewData extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            String judul_, tanggal_, penulis_, desc_;
            judul_ = judul.replaceAll(" ", "%20");
            tanggal_ = tanggal.replaceAll(" ", "%20");
            penulis_ = penulis.replaceAll(" ", "%20");
            desc_ = desc.replaceAll(" ", "%20");

            String url = Const.BASE_URL+Const.TAMBAH_DATA+"?" +
                    "judul="+judul_+
                    "&tanggal="+tanggal_+
                    "&penulis="+penulis_+
                    "&desc="+desc_;

            HttpHandling handling = new HttpHandling();
            String jsonStr = handling.PostDataReq(url);
            Log.e("DATA_TERKIRIM", " data :\n"+jsonStr);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            startActivity(new Intent(TambahData.this, TampilData.class));
            finish();
        }
    }
}
