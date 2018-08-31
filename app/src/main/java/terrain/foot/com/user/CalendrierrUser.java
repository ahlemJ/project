package terrain.foot.com.user;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import terrain.foot.com.R;

import terrain.foot.com.foot.liststate;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class CalendrierrUser extends AppCompatActivity {
  ArrayList<terrain.foot.com.user.liststate> list;
   ListView listview ;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");
    DatabaseReference myRef1 = database.getReference("simpleusers");
    ChildEventListener value;
    String date,Email;
    ImageView back,refresh;
    String userID, position;
    CalendrierAdapter adapter;
   String idStade , photoUrl;
    Calendar myCalendar=null;
    String[] values;
    protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.calendrierr_user);
   setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

       Intent intent = getIntent();
         userID = getIntent().getStringExtra("userID");
        photoUrl = getIntent().getStringExtra("photoUser");
         position = getIntent().getStringExtra("pos");
         idStade=position;
        final ImageView profile_pic= findViewById(R.id.profile_pic);
        final MultiTransformation multi = new MultiTransformation(
                new RoundedCornersTransformation(128, 0, RoundedCornersTransformation.CornerType.BOTTOM));
       if(photoUrl!=null) {
           Glide.with(this).load(photoUrl)
                   .apply(bitmapTransform(multi))
                   .into(profile_pic);
       }
       else { myRef1.child(userID).child("_filephoto").addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               photoUrl= dataSnapshot.getValue().toString();
               Glide.with(getApplicationContext()).load(photoUrl)
                       .apply(bitmapTransform(multi))
                       .into(profile_pic);
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });}
        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendrierrUser.this, FirstActivity.class);
                intent.putExtra("userID", userID);
                intent.putExtra("photoUser", photoUrl);
                // i.putExtra("pos", userID);
                startActivity(intent);
                finish();
            }
        });
        back=(ImageView)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setColorFilter(R.color.colorPrimaryDark);
                Intent intent = new Intent(CalendrierrUser.this,StadeProfile.class);
                intent.putExtra("pos", position);
                intent.putExtra("userID", userID);
                intent.putExtra("photoUser", photoUrl);
                startActivity(intent);
                finish();
            }
        });
        refresh=(ImageView)findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh.setColorFilter(R.color.colorPrimaryDark);
                Intent intent = new Intent(CalendrierrUser.this,CalendrierrUser.class);
                intent.putExtra("pos", position);
                intent.putExtra("userID", userID);
                intent.putExtra("photoUser", photoUrl);
                startActivity(intent);
                finish();
            }
        });
listview = (ListView) findViewById(R.id.timelist);
      values = new String[] { "00:00-01:30", "01:30-03:00", "03:00-04:30",
                "04:30-06:00", "06:00-07:30", "07:30-09:00", "09:00-10:30", "10:30-12:00",
                "12:00-13:30", "13:30-15:00", "15:00-16:30", "16:30-18:00", "18:00-19:30", "19:30-21:00",
                "21:00-22:30", "22:30-00:00" };
        list = new ArrayList<>();


        for (int i = 0; i < values.length; ++i) {
            list.add(new terrain.foot.com.user.liststate(0,values[i]));
        }
        adapter = new CalendrierAdapter(getApplicationContext(), list);
        listview.setAdapter(adapter);

        mDisplayDate = (TextView) findViewById(R.id.Date);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CalendrierrUser.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("Calendrier", "onDateSet: mm-dd-yyy: " + day + "-" + month + "-" + year);

                date = year+"-"+"0"+month+"-"+day;

                mDisplayDate.setText(date);
                restart();
                adapterlist();



            }
        };

        init_list();

    }
    public  void  adapterlist(){
        Query query=myRef.child(position).child("Newreservation").orderByChild("date").equalTo(date);


        value = new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                list.get(Integer.parseInt(dataSnapshot.child("etat").getValue().toString())).setEtat(1);

                adapter.notifyDataSetChanged();

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
               list.get(Integer.parseInt(dataSnapshot.child("etat").getValue().toString())).setEtat(0);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        query.addChildEventListener(value);

        myRef.child(position).child("tempBloquer").child(date).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {

                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        list.get(Integer.parseInt(data.getKey())).setEtat(2);

                   }adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
    public  void restart(){
        for(int i=0;i<list.size();i++){
list.get(i).setEtat(0);
        }
        adapter.notifyDataSetChanged();

    }
    public void init_list()
    {
        final ImageView[] im = new ImageView[1];
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, final long id1) {
               if(date!=null){
                if(list.get(position).getEtat()==0)
                {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(CalendrierrUser.this);
                    builder1.setMessage("Voulez-vous effectuer une réservation pour cette heure?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Oui",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                     myCalendar = Calendar.getInstance();
                                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                                    String dateExact = df.format(myCalendar.getTime());
                                    //DateFormat df1 = new SimpleDateFormat("HH:mm");
                                   // String heure = df1.format(myCalendar.getTime());
                                    writeNewReservation(myRef,myRef1,userID,date,values[position], position, dateExact);
                                    Toast.makeText(CalendrierrUser.this,"Bien enregistrée" , Toast.LENGTH_LONG).show();

                                    //Intent i = new Intent(getBaseContext(), StadeProfile.class);
                                    //i.putExtra("pos", pos);
                                    //startActivity(i);
                                    //finish();
                                    dialog.cancel();


                                }
                            });

                    builder1.setNegativeButton(
                            "Non",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                }else if (list.get(position).getEtat()==2){

                    Toast.makeText(getApplicationContext(), "Non disponible, Désactivé par l'administrateur", Toast.LENGTH_SHORT).show(); }


               }else {           Toast.makeText(getApplicationContext(), "choisissez une date d'abord", Toast.LENGTH_SHORT).show();
               }
            }
        });


    }
    public  void writeNewReservation(final DatabaseReference databaseReference, DatabaseReference myRef1, String userID, String date, String heure, int etat, String DateDeDemande) {

        NewReservation newReservation = new NewReservation( date, heure, Integer.toString(etat),DateDeDemande, photoUrl,userID);
       NewReservation newReservation1 = new NewReservation( date, heure, Integer.toString(etat),DateDeDemande, idStade);
        //Toast.makeText(Calendrierr.this,photoUrl , Toast.LENGTH_LONG).show();

        String key = databaseReference.child(idStade).child("Newreservation").push().getKey().toString();

        databaseReference.child(idStade).child("Newreservation").child(key).setValue(newReservation);
        myRef1.child(userID).child("Myreservation").child(key).setValue(newReservation1);



        databaseReference.child(idStade).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ii=dataSnapshot.child("numberres").getValue().toString();
                int i = Integer.parseInt( ii);
                if(ii!=null) { databaseReference.child(idStade).child("numberres").setValue(i+1);}
                else{databaseReference.child(idStade).child("numberres").setValue("1");}

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
