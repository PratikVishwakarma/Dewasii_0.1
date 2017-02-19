package pratik.com.shauryatoday.dewasii_01.UI.categories.Health.FitnessCenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
import pratik.com.shauryatoday.dewasii_01.adapters.health.FitnessCenterAdapter;
import pratik.com.shauryatoday.dewasii_01.pojos.health.FitnessCenter;
import pratik.com.shauryatoday.dewasii_01.utility.Constant;

import static android.view.KeyEvent.KEYCODE_B;

public class healthAllFitnessCenter extends AppCompatActivity {

    private ArrayList<FitnessCenter> fitnessCenterList = new ArrayList<>();
    private ListView listView;
    private SearchView searchView;
    private FitnessCenterAdapter mFitnessCenterAdapter;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference fitnessCenterRef;
    private FloatingActionButton fab, fab_refresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_all_fitness_center);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab_refresh = (FloatingActionButton) findViewById(R.id.fab_refresh_fitness_center_list);
        fab.setVisibility(View.INVISIBLE);
        fab_refresh.setVisibility(View.INVISIBLE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchView = (SearchView) findViewById(R.id.searchView_health_fitness_center_search_by_name);
        searchView.setVisibility(View.INVISIBLE);

        listView = (ListView) findViewById(R.id.listViewAllFitnessCenter);
        mFitnessCenterAdapter = new FitnessCenterAdapter(this, R.layout.health_fitness_center_list_item, fitnessCenterList);
        listView.setAdapter(mFitnessCenterAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), healthFitnessCenterDetail.class);
                intent.putExtra(Constant.INTENT_PASSING_SINGLE_HEALTH_FITNESS_CENTER, (Parcelable) fitnessCenterList.get(i));
                startActivity(intent);
            }
        });

        fetchFitnessCentersFromFirebase();
    }

    public void fetchFitnessCentersFromFirebase(){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        fitnessCenterRef = mFirebaseDatabase.getReference(Constant.FIREBASE_REFRENCE_HEALTH).child(Constant.FIREBASE_REFRENCE_FITNESS_CENTER);

        fitnessCenterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
//                    Log.e("healthAllHospital "," data exist "+dataSnapshot.getChildrenCount());
                    fitnessCenterList.clear();
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()){
                        FitnessCenter fitnessCenter = childDataSnapshot.getValue(FitnessCenter.class);
                        fitnessCenterList.add(fitnessCenter);
                    }
                    searchView.setVisibility(View.VISIBLE);
                    fab_refresh.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.VISIBLE);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent =  new Intent(healthAllFitnessCenter.this, seeOnMap.class);
                            intent.putParcelableArrayListExtra(Constant.INTENT_PASSING_LIST_0N_MAP, fitnessCenterList);
                            intent.putExtra(Constant.INTENT_PASSING_MAP_CLASSNAME, Constant.CLASS_NAME_HEALTH_FITNESS_CENTER);
                            startActivity(intent);
                        }
                    });
                    fab_refresh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            fab.setVisibility(View.INVISIBLE);
                            fab_refresh.setVisibility(View.INVISIBLE);
                            searchView.setVisibility(View.INVISIBLE);
                            fetchFitnessCentersFromFirebase();
                        }
                    });
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            searchFitnessCenterListByName(query);
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
//                            Toast.makeText(getApplicationContext(), "Search for "+newText, Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    });
                    mFitnessCenterAdapter.notifyDataSetChanged();
                } else{
                    Log.e("healthAllHospital "," data not exist ");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Toast.makeText(getApplicationContext(),"Back Button Long Pressed ", Toast.LENGTH_SHORT).show();
        }
        return super.onKeyLongPress(keyCode, event);
    }

    public void searchFitnessCenterListByName(String name){
        ArrayList<FitnessCenter> tempList = new ArrayList<>();
        for(int i = 0; i < fitnessCenterList.size(); i++){
            String s1 = fitnessCenterList.get(i).getName();
            if(Pattern.compile(Pattern.quote(name), Pattern.CASE_INSENSITIVE).matcher(s1).find()){
//                Log.e("All hospital Search", hospitalList.get(i).getName()+" Contain "+name);
                tempList.add(fitnessCenterList.get(i));
            }else{
//                Log.e("All hospital Search", hospitalList.get(i).getName()+" not Contain "+name);
            }
        }
        fitnessCenterList.clear();
        fitnessCenterList = tempList;
        mFitnessCenterAdapter = new FitnessCenterAdapter(this, R.layout.health_fitness_center_list_item, fitnessCenterList);
        listView.setAdapter(mFitnessCenterAdapter);
        mFitnessCenterAdapter.notifyDataSetChanged();
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
