package pnj.ac.id.uas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {


    CardView cv_additem , cv_listitem, cv_logout;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cv_additem = findViewById(R.id.cv_additem);
        cv_listitem = findViewById(R.id.cv_listitem);
        cv_logout = findViewById(R.id.cv_logout);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

//        PD = new ProgressDialog(this);
//        PD.setMessage("Wait a minute...");
//        PD.setCancelable(true);
//        PD.setCanceledOnTouchOutside(false);

        cv_additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,AddItemActivity.class);
                startActivity(i);
            }
        });
        cv_listitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ReadDataRealtime.class);
                startActivity(i);
            }
        });
        cv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });



    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.option_menu, menu);
//        return true;
//    }
    @Override
    protected void onResume() {
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        super.onResume();
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId())
//        {
//            case R.id.op_logout:
//                FirebaseAuth.getInstance().signOut();
//                finish();
//                startActivity(new Intent(this, LoginActivity.class));
//                break;
//        }
//
//        return true;
//    }
}
