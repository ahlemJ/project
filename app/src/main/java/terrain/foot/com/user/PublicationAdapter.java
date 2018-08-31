package terrain.foot.com.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import terrain.foot.com.R;

public class PublicationAdapter extends ArrayAdapter<PublicationUser> {
    FirebaseDatabase database;
    DatabaseReference myRef, myRef1;
    String name;
    TextView stade;
    String key , key1;
     Boolean zoomOut = false ;
    ArrayList<PublicationUser> users = new ArrayList<>();
    public PublicationAdapter(Context context, ArrayList<PublicationUser> users) {
        super(context, 0, users);
        this.users=users;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position


        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.feedback_item, parent, false);
          // convertView = LayoutInflater.
            //        from(parent.getContext()).
              //      inflate(R.layout.evenment_item, parent, false);
        }
        // Lookup view for data population
        TextView titre= (TextView) convertView.findViewById(R.id.titre);
        TextView text = (TextView) convertView.findViewById(R.id.description);
        ImageView profile = convertView.findViewById(R.id.profile_pic);
       // profile.setImageDrawable();

        String text1 = users.get(position).getText();
        String titre1 = users.get(position).getTitre();
        String image1 = users.get(position).getImg();
        final ImageView image = convertView.findViewById(R.id.image);
             if (image1!=null) {

                Glide.with(getContext()).load(image1)
                         .into(image);
                 Glide.with(getContext()).load(image1)
                         .into(profile);
                 }
                 else {image.setVisibility(View.GONE);}
             if (titre1!=null) {  titre.setText(titre1);}
             if (text1!=null) {  text.setText(text1);}


        return convertView;
    }

}
