package terrain.foot.com.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import terrain.foot.com.R;

public class ReservationAdapter extends ArrayAdapter<NewReservation> {

    FirebaseDatabase database;
    DatabaseReference myRef, myRef1;
    String name;
    TextView stade;
    String key , key1;

    ArrayList<NewReservation> users = new ArrayList<>();
    public ReservationAdapter(Context context, ArrayList<NewReservation> users) {
        super(context, 0, users);
        this.users=users;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");
        myRef1 = database.getReference("simpleusers");
        NewReservation user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_view_layoutuser, parent, false);
        }
        // Lookup view for data population
        // = (TextView) convertView.findViewById(R.id.textView);
        Button annuler = (Button) convertView.findViewById(R.id.annuler);
        TextView temps = (TextView) convertView.findViewById(R.id.textVieww);
        TextView dateDereservation = (TextView) convertView.findViewById(R.id.textViewww);
        // Populate the data into the template view using the data object
       temps.setText(user.getDate());
       dateDereservation.setText(user.getHeure());
       key= user.getIdTerrain();
     //  key1= user.getId();

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getContext(),  key1 + "  id stade ", Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(),  MesReservation.reservationId.get(position) + "  id reser ", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Voulez-vous confirmer l'annulation de cette réservation ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Oui",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                myRef.child(key).child("Newreservation").child(MesReservation.reservationId.get(position)).removeValue();
                                //Toast.makeText(getContext(),  key1 + "  id stade ", Toast.LENGTH_SHORT).show();
                                myRef1.child(MesReservation.userID).child("Myreservation").child(MesReservation.reservationId.get(position)).removeValue();
                              users.remove(position);
                              notifyDataSetChanged();
                                dialog.cancel();
                               Toast.makeText(getContext(), "Annulation effectuée", Toast.LENGTH_LONG).show();
                              //  Intent i = new Intent(ReservationAdapter.this, StadeProfile.class);
                                //i.putExtra("pos", position);
                                //startActivity(i);

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



            }

        });

        return convertView;
    }
    @Override
    public boolean isEnabled(int position)
    {
        return true;
    }

}
