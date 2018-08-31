package terrain.foot.com.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import terrain.foot.com.R;

public  class MotDePass  extends AppCompatActivity {
    TextInputEditText pass1;
    TextInputEditText pass2;
    TextInputEditText oldpasss;
    ImageView back;
    Button confirmer, annuler;
    FirebaseUser user;
    Boolean changement = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.motdepass);
        back=(ImageView)findViewById(R.id.back);
        pass1=(TextInputEditText)findViewById(R.id.mdp1);
        pass2=(TextInputEditText)findViewById(R.id.mdp2);
        oldpasss=(TextInputEditText)findViewById(R.id.oldmdp);
        confirmer = findViewById(R.id.confirmer);
        annuler = findViewById(R.id.annuler);
        Intent intent= getIntent();
        final String  userID = intent.getStringExtra("userID");
        final String  photoUser = intent.getStringExtra("photoUser");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setColorFilter(R.color.colorPrimary);
                Intent intent = new Intent(MotDePass.this,Parametre.class);
                intent.putExtra("userID", userID);
                intent.putExtra("photoUser", photoUser);
                startActivity(intent);
                finish();
            }
        });

annuler.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent intent = new Intent(MotDePass.this,Parametre.class);
        intent.putExtra("userID", userID);
        intent.putExtra("photoUser", photoUser);
        startActivity(intent);
        finish();
    }
});
        confirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pass1.getText().toString().length()>0 && pass2.getText().toString().length()>0) {
                    if (!pass1.getText().toString().equals(pass2.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Erreur dans l'ecriture du mot de passe", Toast.LENGTH_SHORT).show();

                    } else if ((pass1.getText().toString().length() < 6)) {
                        Toast.makeText(getApplicationContext(), R.string.minimum_password, Toast.LENGTH_SHORT).show();
                    } else {
                        user = FirebaseAuth.getInstance().getCurrentUser();
                        AuthCredential credential = EmailAuthProvider
                                .getCredential(user.getEmail(), oldpasss.getText().toString());

// Prompt the user to re-provide their sign-in credentials
                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            user.updatePassword(pass1.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(), "Password updated", Toast.LENGTH_SHORT).show();


                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "Error password not updated", Toast.LENGTH_SHORT).show();

                                                    }
                                                }
                                            });
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Error auth failed", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                        changement = true;

                    }
                }
                if(changement){
                    Intent intent = new Intent(MotDePass.this,Parametre.class);
                    intent.putExtra("userID", userID);
                    intent.putExtra("photoUser", photoUser);
                    startActivity(intent);
                    finish();

                }
            }
        });
    }
}
