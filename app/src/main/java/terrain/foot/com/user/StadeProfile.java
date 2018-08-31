package terrain.foot.com.user;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import terrain.foot.com.R;
import terrain.foot.com.user._sliders.FragmentSlider;
import terrain.foot.com.user._sliders.SliderIndicator;
import terrain.foot.com.user._sliders.SliderPagerAdapter;
import terrain.foot.com.user._sliders.SliderView;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class StadeProfile extends AppCompatActivity {
    TextView nameprofile, localisation, phoneNumber;
    FirebaseDatabase database;
    DatabaseReference myRef;

    AutoCompleteTextView autoCompleteTextView;
    AutoCompleteAdapterAct adapter = null;
    ImageView photo;
  static   String profile ;
    Button  list;
    LinearLayout reserve, evenement ;
    Button indiv;
    private SliderPagerAdapter mAdapter;
    private SliderIndicator mIndicator;
    private SliderView sliderView;
    private LinearLayout mLinearLayout;
    ImageButton back, feedback;
    ImageButton search1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile2);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Intent intent = getIntent();
        final String id = intent.getStringExtra("pos");
        final String  userID = intent.getStringExtra("userID");
        final String  photoUrl = intent.getStringExtra("photoUser");
        nameprofile = findViewById(R.id.namestade);
        localisation= findViewById(R.id.localisation);
        reserve = findViewById(R.id.reserve);
        search1=findViewById(R.id.search1);
       // indiv= findViewById(R.id.indiv);
        back = findViewById(R.id.back);
        feedback = findViewById(R.id.feedback);
       // list = findViewById(R.id.list);
        photo= findViewById(R.id.profile_pic);
        evenement = findViewById(R.id.evenement);
        phoneNumber=findViewById(R.id.phone);
      //  Toast.makeText(StadeProfile.this, userID , Toast.LENGTH_SHORT).show();
        MultiTransformation multi = new MultiTransformation(
                new RoundedCornersTransformation(128, 0, RoundedCornersTransformation.CornerType.BOTTOM));
        Glide.with(this).load(photoUrl)
                .apply(bitmapTransform(multi))
                .into(photo);
       photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StadeProfile.this, FirstActivity.class);
                intent.putExtra("userID", userID);
                intent.putExtra("photoUser", photoUrl);
                // i.putExtra("pos", userID);
                startActivity(intent);
                finish();
            }
        });


       //final  String id1 =MainActivity.customers.get(position).getId();

        database = FirebaseDatabase.getInstance();
        myRef=database.getReference("users");

        final List<Fragment> fragments = new ArrayList<>();

        sliderView = (SliderView) findViewById(R.id.sliderView);
        mLinearLayout = (LinearLayout) findViewById(R.id.pagesContainer);
        sliderView.setDurationScroll(800);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getBaseContext(), MainActivity.class);
                i.putExtra("pos", id);
                i.putExtra("userID", userID);
                i.putExtra("photoUser", photoUrl);
                startActivity(i);
                finish();
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getBaseContext(), Feedback.class);
                i.putExtra("pos", id);
                i.putExtra("userID", userID);
                i.putExtra("photoUser", photoUrl);
                startActivity(i);
                finish();
            }
        });
        search1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                i.putExtra("pos", id);
                i.putExtra("userID", userID);
                i.putExtra("photoUser", photoUrl);
                startActivity(i);
                finish();
            }
        });

        myRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //if(dataSnapsho)
                if(dataSnapshot.exists()) {
                    profile = dataSnapshot.child("firstName").getValue().toString();
                    nameprofile.setText(profile);
                    localisation.setText("Situé à "+dataSnapshot.child("localisation").getValue().toString());
                    phoneNumber.setText("Tél : "+dataSnapshot.child("phoneNumber").getValue().toString());
                    for (DataSnapshot snapshot : dataSnapshot.child("photoScroll").getChildren()) {

                        fragments.add(FragmentSlider.newInstance(snapshot.getValue().toString()));


                    }
                    mAdapter = new SliderPagerAdapter(getSupportFragmentManager(), fragments);
                    sliderView.setAdapter(mAdapter);
                    mIndicator = new SliderIndicator(getApplicationContext(), mLinearLayout, sliderView, R.drawable.indicator);
                    mIndicator.setPageCount(fragments.size());
                    mIndicator.show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StadeProfile.this, CalendrierrUser.class);
                intent.putExtra("pos", id);
                intent.putExtra("userID", userID);
                intent.putExtra("photoUser", photoUrl);

                startActivity(intent);
            }
        });



        evenement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StadeProfile.this, EvenementUser.class);
                intent.putExtra("pos", id);
                intent.putExtra("userID", userID);
                intent.putExtra("photoUser", photoUrl);
                startActivity(intent);
            }
        });


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
