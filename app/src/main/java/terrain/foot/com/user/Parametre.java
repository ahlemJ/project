package terrain.foot.com.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import terrain.foot.com.R;

public class Parametre extends AppCompatActivity {
ImageButton back;
ListView parametre ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parametres);
        back=findViewById(R.id.back);
        parametre= findViewById(R.id.parametre);
        Intent intent = getIntent();

        final String  userID = intent.getStringExtra("userID");
       // Toast.makeText(getApplicationContext(), userID, Toast.LENGTH_SHORT).show();
        final String  photoUser = intent.getStringExtra("photoUser");
        final String[] items = new String[]{"Informations générales", "Changer mot de passe", "Notifications"};
  ParametreAdapter adapter = new ParametreAdapter(this, items);
        parametre.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Parametre.this, FirstActivity.class);
                i.putExtra("userID", userID);
                i.putExtra("photoUser", photoUser);
                // i.putExtra("pos", userID);
                startActivity(i);
                finish();
            }
        });
      //  parametre.getChildAt(1).setBackground(R.drawable.calendar);
parametre.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

       if(position==0) {

           Intent i = new Intent(Parametre.this, InfoEdit.class);
           i.putExtra("userID", userID);
           i.putExtra("photoUser", photoUser);
           startActivity(i);
           finish();
       }
       else if (position==1) {
           Intent i ;

           i = new Intent(Parametre.this, MotDePass.class);
           i.putExtra("userID", userID);
           i.putExtra("photoUser", photoUser);
           startActivity(i);
       }



    }
});

    }
}
