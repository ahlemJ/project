package terrain.foot.com.user;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.FileOutputStream;

import terrain.foot.com.R;

public class LoginUser extends AppCompatActivity {
    private EditText log;
    private EditText passwd ;
    private Button connexion ;
    private FirebaseAuth auth;
    private String id = null;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginact);
        log=(EditText) findViewById(R.id.username);
        passwd=(EditText)findViewById(R.id.password);
        connexion=(Button)findViewById(R.id.signin);
        TextView signup;

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        auth = FirebaseAuth.getInstance();







        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String pseudo = log.getText().toString();
                final String password = passwd.getText().toString();

                if (TextUtils.isEmpty(pseudo)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {

                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }


                auth.signInWithEmailAndPassword(pseudo, password)
                        .addOnCompleteListener(LoginUser.this, new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    if (password.length() < 6) {
                                        passwd.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginUser.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {

                                    id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    String Email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
//                                   String photo = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString();
                                    // Toast.makeText(Login.this, Email, Toast.LENGTH_LONG).show();
                                    // String Email1 = log.getText().toString();
                                    setValue(id.length(), "userID");
                                    setValue(Email.length(), "Email");
                                    setvalue(id, "userID");
                                    setvalue(Email, "Email");
                                    Intent intent = new Intent(LoginUser.this,FirstActivity.class);
                                    intent.putExtra("userID", id);
                                    intent.putExtra("Email", log.getText().toString());
                                    startActivity(intent);
                                    finish();


                                }
                            }
                        });



            }

        });
        signup = (TextView)findViewById(R.id.textsignup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginUser.this, SignUpActivityUser.class));
                finish();
            }
        });

    }
    public void setValue(int newValue,String name) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(name, newValue);
        editor.commit();
    }
    public void setvalue(String data,String file){
        try {
            FileOutputStream fOut = openFileOutput(file,MODE_PRIVATE);
            fOut.write(data.getBytes());
            fOut.close();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.moveTaskToFront(getTaskId(), 0);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Do nothing or catch the keys you want to block
        return false;
    }

}