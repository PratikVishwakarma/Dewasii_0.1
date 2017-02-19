package pratik.com.shauryatoday.dewasii_01.adapters.health;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pratik.com.shauryatoday.dewasii_01.R;
import pratik.com.shauryatoday.dewasii_01.pojos.health.*;

/**
 * Created by prati on 18-Feb-17.
 */

public class FitnessCenterAdapter extends ArrayAdapter<FitnessCenter> {
    private View listItemView;
    private List<FitnessCenter> fitnessCenterList;

    public FitnessCenterAdapter(Context context, int resource, List<FitnessCenter> fitnessCenterList) {
        super(context, resource, fitnessCenterList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final FitnessCenter currentFitnessCenter = getItem(position);

        listItemView = convertView;
        listItemView = LayoutInflater.from(getContext()).inflate(R.layout.health_fitness_center_list_item, parent, false);
        TextView hospitalName = (TextView) listItemView.findViewById(R.id.health_fitness_center_name);
        hospitalName.setText(currentFitnessCenter.getName());

        return listItemView;
    }
}
