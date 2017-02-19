package pratik.com.shauryatoday.dewasii_01.UI.categories.Health.hospital;

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
import pratik.com.shauryatoday.dewasii_01.UI.seeOnMap;
import pratik.com.shauryatoday.dewasii_01.adapters.health.HospitalAdapter;
import pratik.com.shauryatoday.dewasii_01.pojos.health.Hospital;
import pratik.com.shauryatoday.dewasii_01.utility.Constant;

public class healthAllHospital extends AppCompatActivity {

    private ArrayList<Hospital> hospitalList = new ArrayList<>();
    private ListView listView;
    private SearchView searchView;
    private HospitalAdapter mHospitalAdapter;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference hospitalRef;
    private FloatingActionButton fab, fab_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_all_hospital);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab_refresh = (FloatingActionButton) findViewById(R.id.fab_refresh_hospital_list);
        fab.setVisibility(View.INVISIBLE);
        fab_refresh.setVisibility(View.INVISIBLE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchView = (SearchView) findViewById(R.id.searchView_healthHospital_search_by_name);
        searchView.setVisibility(View.INVISIBLE);

        listView = (ListView) findViewById(R.id.listViewAllHospital);
        mHospitalAdapter = new HospitalAdapter(this, R.layout.health_hospital_list_item, hospitalList);
        listView.setAdapter(mHospitalAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), healthHospitalDetail.class);
                intent.putExtra(Constant.INTENT_PASSING_SINGLE_HEALTH_HOSPITAL, (Parcelable) hospitalList.get(i));
                startActivity(intent);
            }
        });
        fetchHospitalsFromFirebase();
    }

    public void fetchHospitalsFromFirebase(){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        hospitalRef = mFirebaseDatabase.getReference(Constant.FIREBASE_REFRENCE_HEALTH).child(Constant.FIREBASE_REFRENCE_HOSPITAL);

        hospitalRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Log.e("healthAllHospital "," data exist "+dataSnapshot.getChildrenCount());
                    hospitalList.clear();
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()){
                        Hospital hospital = childDataSnapshot.getValue(Hospital.class);
                        hospitalList.add(hospital);
                    }
                    searchView.setVisibility(View.VISIBLE);
                    fab_refresh.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.VISIBLE);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent =  new Intent(healthAllHospital.this, seeOnMap.class);
                            intent.putParcelableArrayListExtra(Constant.INTENT_PASSING_LIST_0N_MAP, hospitalList);
                            intent.putExtra(Constant.INTENT_PASSING_MAP_CLASSNAME, Constant.CLASS_NAME_HEALTH_HOSPITAL);
                            startActivity(intent);
                        }
                    });
                    fab_refresh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            fab.setVisibility(View.INVISIBLE);
                            fab_refresh.setVisibility(View.INVISIBLE);
                            searchView.setVisibility(View.INVISIBLE);
                            fetchHospitalsFromFirebase();
                        }
                    });
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            searchHospitalListByName(query);
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
//                            Toast.makeText(getApplicationContext(), "Search for "+newText, Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    });
                    mHospitalAdapter.notifyDataSetChanged();
                } else{

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void searchHospitalListByName(String name){
        ArrayList<Hospital> tempList = new ArrayList<>();
        for(int i = 0; i < hospitalList.size(); i++){
            String s1 = hospitalList.get(i).getName();
            if(Pattern.compile(Pattern.quote(name), Pattern.CASE_INSENSITIVE).matcher(s1).find()){
                Log.e("All hospital Search", hospitalList.get(i).getName()+" Contain "+name);
                tempList.add(hospitalList.get(i));
            }else{
                Log.e("All hospital Search", hospitalList.get(i).getName()+" not Contain "+name);
            }
        }
        Log.e("All hospital Search", "HospitalList size = "+hospitalList.size());
        Log.e("All hospital Search", "Templist size = "+tempList.size());
        hospitalList.clear();
        hospitalList = tempList;
        Log.e("All hospital Search", "HospitalList size 2 = "+hospitalList.size());
        mHospitalAdapter = new HospitalAdapter(this, R.layout.health_hospital_list_item, hospitalList);
        listView.setAdapter(mHospitalAdapter);
        mHospitalAdapter.notifyDataSetChanged();
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
