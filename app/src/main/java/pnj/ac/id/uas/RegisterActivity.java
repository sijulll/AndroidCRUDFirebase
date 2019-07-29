package pnj.ac.id.uas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
 EditText et_email,et_password;
 Button btn_signup,btn_haveaccount;
 CheckBox cb_showpass;
 //TextView
    FirebaseAuth mFirebaseAuth;




    @Override    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        cb_showpass = findViewById(R.id.cb_showpass);
        btn_signup = findViewById(R.id.btn_signup);
        btn_haveaccount = findViewById(R.id.btn_haveaccount);

        cb_showpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_showpass.isChecked())
                {
                    cb_showpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    cb_showpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                if (email.isEmpty())
                {
                    et_email.setError("Please Fill the email");
                    et_email.requestFocus();
                }

                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    et_email.setError("Valid Email Required");
                    et_email.requestFocus();
                }
                else if (password.isEmpty() || password.length() < 6 )
                {
                    et_password.setError("6 Char password required");
                    et_password.requestFocus();
                }

                else if (password.isEmpty())
                {
                    et_password.setError("Please fill the password");
                    et_password.requestFocus();
                }
                else if (email.isEmpty() && password.isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, "fields are empty." , Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty()&& password.isEmpty()))
                {
                    mFirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(RegisterActivity.this, "SignUp Unsuccessfull, Please try again", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class
                                ));
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn_haveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }
}