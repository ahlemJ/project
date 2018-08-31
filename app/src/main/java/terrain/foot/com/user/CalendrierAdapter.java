package terrain.foot.com.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import terrain.foot.com.R;

public class CalendrierAdapter extends ArrayAdapter<liststate > {

    String name;


    ArrayList<liststate> users = new ArrayList<>();
    public CalendrierAdapter(Context context, ArrayList<liststate> users) {
        super(context, 0, users);
        this.users=users;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

          if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.calendrier_itemuser, parent, false);
        }

        TextView text = (TextView) convertView.findViewById(R.id.time);
        ImageView state=(ImageView)convertView.findViewById(R.id.check_bloc);

        String text1 = users.get(position).getTemps();
        int etat=users.get(position).getEtat();

if (etat==0){state.setImageDrawable(null);}
        else if(etat==1){
             state.setImageResource(R.drawable.check);
         }
         else if(etat==2){
             state.setImageResource(R.drawable.block_helper);
         }

         text.setText(text1);

        return convertView;
    }
}
