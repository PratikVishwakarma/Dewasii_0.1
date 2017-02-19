package pratik.com.shauryatoday.dewasii_01.UI.categories.Health.FitnessCenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import pratik.com.shauryatoday.dewasii_01.R;
import pratik.com.shauryatoday.dewasii_01.pojos.health.FitnessCenter;
import pratik.com.shauryatoday.dewasii_01.utility.Constant;

public class healthFitnessCenterDetail extends AppCompatActivity {

    private FitnessCenter fitnessCenter;
    private TextView textViewFitnessCenterName, textViewFitnessCenterAddress, textViewFitnessCenterContact;
    private ImageView imageViewFitnessCenterImage;
    private StorageReference mStorageRef;
    private StorageReference fitnessCenterImagePathRef;
    private FloatingActionButton fab_start_nevigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_fitness_center_detail);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        Intent intent = getIntent();
        fitnessCenter = (FitnessCenter) intent.getParcelableExtra(Constant.INTENT_PASSING_SINGLE_HEALTH_FITNESS_CENTER);

        fab_start_nevigation = (FloatingActionButton) findViewById(R.id.fab_health_fitness_center_start_nevigation);
        textViewFitnessCenterName = (TextView) findViewById(R.id.textView_fitnessCenterDetail_name);
        textViewFitnessCenterAddress = (TextView) findViewById(R.id.textView_fitnessCenterDetail_address);
        textViewFitnessCenterContact = (TextView) findViewById(R.id.textView_fitnessCenterDetail_contact);
        imageViewFitnessCenterImage = (ImageView) findViewById(R.id.imageView_fitnessCenterDetail_image);

        textViewFitnessCenterName.setText(fitnessCenter.getName());
        textViewFitnessCenterAddress.setText(fitnessCenter.getAddress());
        textViewFitnessCenterContact.setText(fitnessCenter.getContactNo());

        fab_start_nevigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "google.navigation:q="+fitnessCenter.getLatitude()+","+fitnessCenter.getLongitude();
//                String uri = "geo:0,0?q="+hospital.getLatitude()+","+hospital.getLongitude();
                Uri gmmIntentUri = Uri.parse(uri);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

//        hospitalImagePathRef = mStorageRef.child(Constant.FIREBASE_STORAGE_REFRENCE_IMAGES)
//                .child(Constant.FIREBASE_STORAGE_REFRENCE_HEALTH)
//                .child(Constant.FIREBASE_STORAGE_REFRENCE_FITNESS_CENTER)
//                .child(fitnessCenter.getImage());
//
//        fitnessCenterImagePathRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Uri imageUri = uri;
//                Picasso.with(getApplicationContext())
//                        .load(uri).placeholder(R.drawable.no_image_available)
//                        .error(R.drawable.no_image_available)
//                        .into(imageViewFitnessCenterImage);
//            }
//        });
        Picasso.with(getApplicationContext())
                .load("d").placeholder(R.drawable.no_image_available)
                .error(R.drawable.no_image_available)
                .into(imageViewFitnessCenterImage);
    }
}
