package pratik.com.shauryatoday.dewasii_01.UI.categories.Health.MedicalStore;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Pattern;

import pratik.com.shauryatoday.dewasii_01.R;
import pratik.com.shauryatoday.dewasii_01.UI.Category;
import pratik.com.shauryatoday.dewasii_01.UI.categories.Health.FitnessCenter.healthFitnessCenterDetail;
import pratik.com.shauryatoday.dewasii_01.UI.seeOnMap;
import pratik.com.shauryatoday.dewasii_01.adapters.health.MedicalStoreAdapter;
import pratik.com.shauryatoday.dewasii_01.pojos.health.MedicalStore;
import pratik.com.shauryatoday.dewasii_01.utility.Constant;

public class healthAllMedicalStore extends AppCompatActivity {

    private ArrayList<MedicalStore> medicalStoreList = new ArrayList<>();
    private ListView listView;
    private SearchView searchView;
    private MedicalStoreAdapter mMedicalStoreAdapter;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference medicalStoreRef;
    private FloatingActionButton fab, fab_refresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_all_medical_store);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab_show_medical_store_list_on_map);
        fab_refresh = (FloatingActionButton) findViewById(R.id.fab_refresh_medical_store_list);
        fab.setVisibility(View.INVISIBLE);
        fab_refresh.setVisibility(View.INVISIBLE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchView = (SearchView) findViewById(R.id.searchView_health_medical_store_search_by_name);
        searchView.setVisibility(View.INVISIBLE);

        listView = (ListView) findViewById(R.id.listViewAllMedicalStore);
        mMedicalStoreAdapter = new MedicalStoreAdapter(this, R.layout.health_medical_store_list_item, medicalStoreList);
        listView.setAdapter(mMedicalStoreAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), healthMedicalStoreDetail.class);
                intent.putExtra(Constant.INTENT_PASSING_SINGLE_HEALTH_MEDICAL_STORE, (Parcelable) medicalStoreList.get(i));
                startActivity(intent);
            }
        });
        fetchMedicalStoreFromFirebase();
    }

    public void fetchMedicalStoreFromFirebase(){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        medicalStoreRef = mFirebaseDatabase.getReference(Constant.FIREBASE_REFRENCE_HEALTH)
                .child(Constant.FIREBASE_REFRENCE_MEDICAL_STORE);

        medicalStoreRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
//                    Log.e("healthAllHospital "," data exist "+dataSnapshot.getChildrenCount());
                    medicalStoreList.clear();
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()){
                        MedicalStore medicalStore = childDataSnapshot.getValue(MedicalStore.class);
                        medicalStoreList.add(medicalStore);
                    }
                    searchView.setVisibility(View.VISIBLE);
                    fab_refresh.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.VISIBLE);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent =  new Intent(healthAllMedicalStore.this, seeOnMap.class);
                            intent.putParcelableArrayListExtra(Constant.INTENT_PASSING_LIST_0N_MAP, medicalStoreList);
                            intent.putExtra(Constant.INTENT_PASSING_MAP_CLASSNAME, Constant.CLASS_NAME_HEALTH_MEDICAL_STORE);
                            startActivity(intent);
                        }
                    });
                    fab_refresh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            fab.setVisibility(View.INVISIBLE);
                            fab_refresh.setVisibility(View.INVISIBLE);
                            searchView.setVisibility(View.INVISIBLE);
                            fetchMedicalStoreFromFirebase();
                        }
                    });
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            searchMedicalStoreListByName(query);
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
//                            Toast.makeText(getApplicationContext(), "Search for "+newText, Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    });
                    mMedicalStoreAdapter.notifyDataSetChanged();
                } else{
                    Log.e("healthAllHospital "," data not exist ");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void searchMedicalStoreListByName(String name){
        ArrayList<MedicalStore> tempList = new ArrayList<>();
        for(int i = 0; i < medicalStoreList.size(); i++){
            String s1 = medicalStoreList.get(i).getName();
            if(Pattern.compile(Pattern.quote(name), Pattern.CASE_INSENSITIVE).matcher(s1).find()){
//                Log.e("All hospital Search", hospitalList.get(i).getName()+" Contain "+name);
                tempList.add(medicalStoreList.get(i));
            }else{
//                Log.e("All hospital Search", hospitalList.get(i).getName()+" not Contain "+name);
            }
        }
        medicalStoreList.clear();
        medicalStoreList = tempList;
//        Log.e("All hospital Search", "HospitalList size 2 = "+hospitalList.size());
        mMedicalStoreAdapter = new MedicalStoreAdapter(this, R.layout.health_medical_store_list_item, medicalStoreList);
        listView.setAdapter(mMedicalStoreAdapter);
        mMedicalStoreAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_news, menu);
        MenuItem item = menu.findItem(R.id.action_goto_home);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_goto_home) {
            Intent intent = new Intent(getApplicationContext(), Category.class);
            intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
