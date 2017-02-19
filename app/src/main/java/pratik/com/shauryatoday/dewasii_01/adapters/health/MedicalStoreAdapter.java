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
import pratik.com.shauryatoday.dewasii_01.pojos.health.FitnessCenter;
import pratik.com.shauryatoday.dewasii_01.pojos.health.MedicalStore;

/**
 * Created by prati on 18-Feb-17.
 */

public class MedicalStoreAdapter extends ArrayAdapter<MedicalStore> {
    private View listItemView;
    private List<MedicalStore> medicalStoreList;

    public MedicalStoreAdapter(Context context, int resource, List<MedicalStore> medicalStoreList) {
        super(context, resource, medicalStoreList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MedicalStore currentMedicalStore = getItem(position);

        listItemView = convertView;
        listItemView = LayoutInflater.from(getContext()).inflate(R.layout.health_medical_store_list_item, parent, false);
        TextView hospitalName = (TextView) listItemView.findViewById(R.id.health_medical_store_name);
        hospitalName.setText(currentMedicalStore.getName());

        return listItemView;
    }
}
