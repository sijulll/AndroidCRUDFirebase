package pnj.ac.id.uas;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText et_email,et_password;
    Button btn_signin,btn_forgotpassword,btn_signup;
    CheckBox cb_password;
    //TextView
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    RelativeLayout rellay1, rellay2;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAuth = FirebaseAuth.getInstance();
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_signup = findViewById(R.id.btn_signup);
        btn_signin = findViewById(R.id.btn_signin);
        cb_password = findViewById(R.id.cb_password);
        btn_forgotpassword = findViewById(R.id.btn_forgotpassword);
        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);
        handler.postDelayed(runnable, 2000);

        cb_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_password.isChecked())
                {
                    cb_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    cb_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

                if ( mFirebaseUser != null)
                {
                    Toast.makeText(LoginActivity.this, "Youre Logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Please Login First", Toast.LENGTH_SHORT).show();
                }
            }
        };

        btn_signin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();

                String password = et_password.getText().toString();
                if (email.isEmpty())
                {
                    et_email.setError("Please Fill the email");
                    et_email.requestFocus();
                }

                else if (password.isEmpty())
                {
                    et_password.setError("Please fill the password");
                    et_password.requestFocus();
                }
                else if (email.isEmpty() && password.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "fields are empty." , Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty()&& password.isEmpty()))
                {
                    mFirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(LoginActivity.this , "Login Failed, Please try again" , Toast.LENGTH_SHORT);
                            }
                            else
                            {
                                Intent  goMain =  new Intent(LoginActivity.this , MainActivity.class);
                                startActivity(goMain);
                            }

                        }
                    });
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent goRegister = new Intent(LoginActivity.this ,RegisterActivity.class);
                startActivity(goRegister);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
