package pnj.ac.id.uas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnSuccessListener;

import pnj.ac.id.uas.Makanan;
import static android.text.TextUtils.isEmpty;

public class AddItemActivity extends AppCompatActivity {
    Button btn_add;
    EditText et_itemname, et_desc;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //inisialisasi
        et_itemname = (EditText) findViewById(R.id.et_itemname);
        et_desc = (EditText) findViewById(R.id.et_desc);
        btn_add = (Button) findViewById(R.id.btn_additem);

        //ambil reference ke Db Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEmpty(et_itemname.getText().toString()) && !isEmpty(et_desc.getText().toString()))
                submitItem(new Makanan(et_itemname.getText().toString(), et_desc.getText().toString()));


                else
                    Snackbar.make(findViewById(R.id.btn_additem), "Data barang tidak boleh kosong", Snackbar.LENGTH_LONG).show();

                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        et_itemname.getWindowToken(), 0);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void submitItem(Makanan makanan) {
        /**
         * Ini adalah kode yang digunakan untuk mengirimkan data ke Firebase Realtime Database
         * dan juga kita set onSuccessListener yang berisi kode yang akan dijalankan
         * ketika data berhasil ditambahkan
         */
        databaseReference.child("makanan").push().setValue(makanan).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                et_itemname.setText("");
                et_desc.setText("");
                Snackbar.make(findViewById(R.id.btn_additem), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
            }
        });
    }
    private void updateBarang(Makanan makanan) {
        /**
         * Baris kode yang digunakan untuk mengupdate data barang
         * yang sudah dimasukkan di Firebase Realtime Database
         */
        databaseReference.child("makanan") //akses parent index, ibaratnya seperti nama tabel
                .child(makanan.getKey()) //select barang berdasarkan key
                .setValue(makanan) //set value barang yang baru
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        /**
                         * Baris kode yang akan dipanggil apabila proses update barang sukses
                         */
                        Snackbar.make(findViewById(R.id.btn_additem), "Data berhasil diupdatekan", Snackbar.LENGTH_LONG).setAction("Oke", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        }).show();
                    }
                });
    }
    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, AddItemActivity.class);
    }
}
