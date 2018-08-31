package terrain.foot.com.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import terrain.foot.com.R;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public  class EvenementUser extends AppCompatActivity {
    ImageButton search, back;
    TextView nameprofile;

    DatabaseReference myRef;
    ListView publications;
    ImageView image;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef1 = database.getReference("simpleusers");
    ArrayList<PublicationUser> pub= new ArrayList<>();
    ArrayAdapter<PublicationUser> adapter;
    String photoUrl;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evenement_user);
        image = findViewById(R.id.profile_pic);
        search=findViewById(R.id.search1);
        back=findViewById(R.id.back);

        nameprofile=findViewById(R.id.namestade);
        publications = findViewById(R.id.pub);
        adapter = new PublicationAdapter(this, pub);
       Intent intent = getIntent();
        final String id =intent.getStringExtra("pos");
        photoUrl = intent.getStringExtra("photoUser");
        final String  userID = intent.getStringExtra("userID");
       // final  String id1 = MainActivity.customers.get(position).getId();
        //nameprofile.setText(MainActivity.customers.get(position).getFirstName());
        final MultiTransformation multi = new MultiTransformation(
                new RoundedCornersTransformation(128, 0, RoundedCornersTransformation.CornerType.BOTTOM));
        if(photoUrl!=null)
        { Glide.with(this).load(photoUrl)
                .apply(bitmapTransform(multi))
                .into(image);}
                else
                    {
                       myRef1.child(userID).child("_filephoto") .addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(DataSnapshot dataSnapshot) {
                               photoUrl= dataSnapshot.getValue().toString();
                               Glide.with(getApplicationContext()).load(photoUrl)
                                       .apply(bitmapTransform(multi))
                                       .into(image);
                           }

                           @Override
                           public void onCancelled(DatabaseError databaseError) {

                           }
                       });
                    }
        nameprofile.setText(StadeProfile.profile);
        database = FirebaseDatabase.getInstance();
        myRef=database.getReference("users");
        myRef.child(id).child("publication").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pub.clear();
              if(dataSnapshot.exists()) {

                  for (DataSnapshot data : dataSnapshot.getChildren()) {
                      pub.add( data.getValue(PublicationUser.class));
                      }

                  Collections.reverse(pub);
              }

          publications.setAdapter(adapter);

              }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getBaseContext(), StadeProfile.class);
                i.putExtra("pos", id);
                i.putExtra("photoUser", photoUrl);
                i.putExtra("userID", userID);
                startActivity(i);
                finish();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getBaseContext(), FirstActivity.class);
                i.putExtra("pos", id);
                i.putExtra("photoUser", photoUrl);
                i.putExtra("userID", userID);
                startActivity(i);
                finish();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),MainActivity.class);

                i.putExtra("photoUser", photoUrl);
                i.putExtra("userID", userID);
                startActivity(i);
                finish();
            }
        });

    }

}
