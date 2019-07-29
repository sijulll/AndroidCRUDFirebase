package pnj.ac.id.uas;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import pnj.ac.id.uas.Makanan;

public class ReadDataRealtime extends AppCompatActivity implements AdapterRV.FirebaseDataListener {

    private DatabaseReference database;
    private RecyclerView rv;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Makanan> listmakanan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Mengeset layout
         */
        setContentView(R.layout.read_db);

        /**
         * Inisialisasi RecyclerView & komponennya
         */
        rv = (RecyclerView) findViewById(R.id.rv_main);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

        /**
         * Inisialisasi dan mengambil Firebase Database Reference
         */
        database = FirebaseDatabase.getInstance().getReference();

        /**
         * Mengambil data makanan dari Firebase Realtime DB
         */
        database.child("makanan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                /**
                 * Saat ada data baru, masukkan datanya ke ArrayList
                 */
                listmakanan = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    /**
                     * Mapping data pada DataSnapshot ke dalam object makanan
                     * Dan juga menyimpan primary key pada object makanan
                     * untuk keperluan Edit dan Delete data
                     */
                    Makanan makanan = noteDataSnapshot.getValue(Makanan.class);
                    makanan.setKey(noteDataSnapshot.getKey());

                    /**
                     * Menambahkan object makanan yang sudah dimapping
                     * ke dalam ArrayList
                     */
                    listmakanan.add(makanan);
                }

                /**
                 * Inisialisasi adapter dan data makanan dalam bentuk ArrayList
                 * dan mengeset Adapter ke dalam RecyclerView
                 */
                adapter = new AdapterRV(listmakanan, ReadDataRealtime.this);
                rv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                /**
                 * Kode ini akan dipanggil ketika ada error dan
                 * pengambilan data gagal dan memprint error nya
                 * ke LogCat
                 */
                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());
            }
        });
    }

    public static Intent getActIntent(Activity activity) {
        return new Intent(activity, ReadDataRealtime.class);
    }

    @Override
    public void onDeleteData(Makanan makanan, final int position) {
        if(database!=null){
            database.child("makanan").child(makanan.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ReadDataRealtime.this,"success delete", Toast.LENGTH_LONG).show();
            }
        });

        }
    }
}



