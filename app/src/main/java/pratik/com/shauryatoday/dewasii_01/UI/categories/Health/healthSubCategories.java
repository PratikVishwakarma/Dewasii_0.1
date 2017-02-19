package pratik.com.shauryatoday.dewasii_01.UI.categories.Health;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import pratik.com.shauryatoday.dewasii_01.R;
import pratik.com.shauryatoday.dewasii_01.UI.categories.Health.FitnessCenter.healthAllFitnessCenter;
import pratik.com.shauryatoday.dewasii_01.UI.categories.Health.MedicalStore.healthAllMedicalStore;
import pratik.com.shauryatoday.dewasii_01.UI.categories.Health.hospital.healthAllHospital;

public class healthSubCategories extends AppCompatActivity {

    private ImageView imageViewHospital, imageViewFitnessCenter, imageViewMedicalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_sub_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initilizeScreen();
        imageViewHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(healthSubCategories.this, healthAllHospital.class);
                startActivity(intent);
            }
        });
        imageViewFitnessCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(healthSubCategories.this, healthAllFitnessCenter.class);
                startActivity(intent);
            }
        });
        imageViewMedicalStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(healthSubCategories.this, healthAllMedicalStore.class);
                startActivity(intent);
            }
        });
    }

    public void initilizeScreen(){
        imageViewHospital = (ImageView) findViewById(R.id.imageView_health_hospital);
        imageViewFitnessCenter = (ImageView) findViewById(R.id.imageView_health_fitness_center);
        imageViewMedicalStore = (ImageView) findViewById(R.id.imageView_health_medical_store);
    }
}
