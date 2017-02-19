package pratik.com.shauryatoday.dewasii_01.adapters.health;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pratik.com.shauryatoday.dewasii_01.R;
import pratik.com.shauryatoday.dewasii_01.pojos.health.Hospital;

/**
 * Created by prati on 17-Feb-17.
 */

public class HospitalAdapter extends ArrayAdapter<Hospital> {

    private View listItemView;
    private List<Hospital> hospitalList;

    public HospitalAdapter(Context context, int resource, List<Hospital> hospitalList) {
        super(context, resource, hospitalList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Hospital currentHospital = getItem(position);

        listItemView = convertView;
        listItemView = LayoutInflater.from(getContext()).inflate(R.layout.health_hospital_list_item, parent, false);
        TextView hospitalName = (TextView) listItemView.findViewById(R.id.health_hospital_name);
        hospitalName.setText(currentHospital.getName());

        return listItemView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
}
