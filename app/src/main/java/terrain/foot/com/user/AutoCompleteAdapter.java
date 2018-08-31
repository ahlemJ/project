package terrain.foot.com.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;

import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import terrain.foot.com.R;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class AutoCompleteAdapter extends ArrayAdapter<StadeModel> {
    ArrayList<StadeModel> customers5, tempCustomer, suggestions, arraylist;
    LayoutInflater inflater;
    Context mContext;

    public AutoCompleteAdapter(Context context, ArrayList<StadeModel> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        mContext=context;
        this.customers5 = objects;
       // this.tempCustomer = new ArrayList<StadeModel>(objects);
       // this.suggestions = new ArrayList<StadeModel>(objects);
        this.arraylist = new ArrayList<StadeModel>();
        this.arraylist.addAll(customers5);
        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StadeModel customer = getItem(position);
        final Context context = getContext();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.autocomp_item, parent, false);
        }
        TextView txtCustomer = (TextView) convertView.findViewById(R.id.tvCustomer);
        TextView localisation = (TextView) convertView.findViewById(R.id.loc1);
        final CircleImageView ivCustomerImage = (CircleImageView) convertView.findViewById(R.id.ivCustomerImage);
        if (txtCustomer != null)
            txtCustomer.setText(customer.getFirstName());
            localisation.setText(customer.getLocalisation());
        int radius = 30; // corner radius, higher value = more rounded
        int margin = 10;
        //Picasso.with(context).load(customer.getProfilePic()).transform(new CircleTransform()).error(R.drawable.ic_launcher_background).into(ivCustomerImage);
       // Glide.with(context).load(customer.getProfilePic()).apply(new RoundedCornersTransformation()).into(ivCustomerImage);
        MultiTransformation multi = new MultiTransformation(
                new RoundedCornersTransformation(128, 0, RoundedCornersTransformation.CornerType.BOTTOM));
        Glide.with(context).load(customer.getProfilePic())
                .apply(bitmapTransform(multi))
                .into(ivCustomerImage);
        //GlideApp.with(context).load(customer.getProfilePic()).transform(new RoundedCornersTransformation(radius, margin)).into(ivCustomerImage);

        /// / if (ivCustomerImage != null && customer.getProfilePic() != null)
         //   Picasso.with(context).load(customer.getProfilePic()).into(ivCustomerImage);
        /*// Now assign alternate color for rows
        if (position % 2 == 0)
            Toast.makeText(getContext(), "jklj", Toast.LENGTH_LONG).show();
            //convertView.setBackgroundColor(getContext().getColor(R.color.odd));
        else
            //convertView.setBackgroundColor(getContext().getColor(R.color.even));
            Toast.makeText(getContext(), "jklj", Toast.LENGTH_LONG).show();*/
        return convertView;
    }



    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        customers5.clear();
        if (charText.length() == 0) {
            customers5.addAll(arraylist);
        } else {
            for (StadeModel wp : arraylist) {
                if (wp.getFirstName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    customers5.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
   /* @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            StadeModel customer6 = (StadeModel) resultValue;
            return customer6.getFirstName() ;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (StadeModel people : tempCustomer) {
                    if (people.getFirstName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<StadeModel> c = (ArrayList<StadeModel>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (StadeModel cust : c) {
                    add(cust);
                    notifyDataSetChanged();
                }
            }
        }
    };*/
}