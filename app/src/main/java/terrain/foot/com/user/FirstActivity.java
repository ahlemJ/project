package terrain.foot.com.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import terrain.foot.com.R;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class FirstActivity extends AppCompatActivity {
    String userID;
Button cherchez, reservation ;
Spinner spinner;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ImageView profile_pic;
TextView nameUser;
String photoUser;
static  UserInfos user;
static  String name, lastname;
    private Uri filePath=null;
    private final int PICK_iMAGE_REQUEST = 71 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("simpleusers");
        cherchez = findViewById(R.id.chercher);
        reservation = findViewById(R.id.reservation);
        profile_pic = findViewById(R.id.profile_pic);
        spinner = findViewById(R.id.spinner);
        nameUser = findViewById(R.id.name);
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");

        String[] items = new String[]{"", "Paramètres", "Deconnexion"};

       // ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent)
            {
                View v = null;

                // If this is the initial dummy entry, make it hidden
                if (position == 0) {
                    TextView tv = new TextView(getContext());
                    tv.setHeight(0);
                    tv.setVisibility(View.GONE);
                    v = tv;
                }
                else {
                    // Pass convertView as null to prevent reuse of special case views
                    v = super.getDropDownView(position, null, parent);
                }

                // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling
                parent.setVerticalScrollBarEnabled(false);
                return v;
            }
        };

        spinner.setAdapter(dataAdapter);
       // spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:

                        break;
                    case 1:
                        Intent i = new Intent(FirstActivity.this, Parametre.class);
                        i.putExtra("userID", userID);
                        i.putExtra("photoUser", photoUser);
                        startActivity(i);
                        finish();
                        break;
                    case 2:
                        // Whatever you want to happen when the second item gets selected
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        prefs.edit().remove("userID").commit();
                        prefs.edit().remove("Email").commit();
                        removefile("userID");
                        removefile("Email");
                        FirebaseAuth.getInstance().signOut();
                        i = new Intent(FirstActivity.this, LoginUser.class);
                        startActivity(i);
                        finish();
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        myRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(UserInfos.class);
                // String name = dataSnapshot.child("_name").getValue().toString();
                //String lastname = dataSnapshot.child("_lastname").getValue().toString();
                photoUser = user.get_filephoto();
                if (photoUser != null) {

                    MultiTransformation multi = new MultiTransformation(
                            new RoundedCornersTransformation(128, 0, RoundedCornersTransformation.CornerType.BOTTOM));
                    Glide.with(getApplicationContext()).load(photoUser)
                            .apply(bitmapTransform(multi))
                            .into(profile_pic);
                }
                name = user.get_name();
                lastname = user.get_lastname();
                nameUser.setText(name + " " + lastname);

                //  photoUser = dataSnapshot.child("_filephoto").getValue().toString();
                // Object object=dataSnapshot.child("_filephoto").getValue();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
        ////*****************hné lezmni naaml tabdil l photo ***************////
        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/+");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent.createChooser(intent,"Select Picture"),PICK_iMAGE_REQUEST);
                //  Toast.makeText(getApplicationContext(), "aaa", Toast.LENGTH_SHORT).show();
                profile_pic.setImageURI(filePath);
                // Toast.makeText(getApplicationContext(), "aaa", Toast.LENGTH_SHORT).show();
                MultiTransformation multi = new MultiTransformation(
                        new RoundedCornersTransformation(128, 0, RoundedCornersTransformation.CornerType.BOTTOM));
                Glide.with(getApplicationContext()).load(photoUser)
                        .apply(bitmapTransform(multi))
                        .into(profile_pic);


            }
        });


        //f=FirstActivity.this;

        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FirstActivity.this, MesReservation.class);
                i.putExtra("userID", userID);
                i.putExtra("photoUser", photoUser);
                startActivity(i);
                finish();
            }
        });
        cherchez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // customersF=customers1;
                Intent i = new Intent(FirstActivity.this, MainActivity.class);
                i.putExtra("userID", userID);
                i.putExtra("photoUser", photoUser);
                startActivity(i);
                finish();

            }
        });
    }
    public void removefile(String name){
        String dir = getFilesDir().getAbsolutePath();
        File f0 = new File(dir, name);
        f0.delete();
    }
}