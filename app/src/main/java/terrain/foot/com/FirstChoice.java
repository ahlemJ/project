package terrain.foot.com;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import terrain.foot.com.R;
import terrain.foot.com.foot.Login;
import terrain.foot.com.foot.Welcom;
import terrain.foot.com.user.LoginUser;
import terrain.foot.com.user.WelcomUser;

public class FirstChoice extends AppCompatActivity {
    Button admin, user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_choice);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        admin = findViewById(R.id.admin);
        user=findViewById(R.id.user);

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstChoice.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstChoice.this, LoginUser.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
