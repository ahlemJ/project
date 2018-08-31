package terrain.foot.com.user;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import terrain.foot.com.R;

public class ParametreAdapter extends ArrayAdapter<String> {

    String[] items;
    public ParametreAdapter(@NonNull Context context, String[] items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.items=items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Context context = getContext();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.parametre_item, parent, false);
        }

        TextView txt = (TextView) convertView.findViewById(R.id.time);

        txt.setText(items[position]);
        ImageView icone =convertView.findViewById(R.id.icone);
        switch (position) {
            case 0 :
               icone.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.settings));
                break;
            case 1 :
                icone.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.security));
                break;
            case 2 :
                icone.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.notification));
                break;


        }
return convertView;
    }
}
