package pratik.com.shauryatoday.dewasii_01.UI.categories.Health.MedicalStore;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import pratik.com.shauryatoday.dewasii_01.R;
import pratik.com.shauryatoday.dewasii_01.pojos.health.Hospital;
import pratik.com.shauryatoday.dewasii_01.pojos.health.MedicalStore;
import pratik.com.shauryatoday.dewasii_01.utility.Constant;

public class healthMedicalStoreDetail extends AppCompatActivity {

    private MedicalStore medicalStore;
    private TextView textViewMedicalStoreName, textViewMedicalStoreAddress, textViewMedicalStoreContact;
    private ImageView imageViewMedicalStoreImage;
    private StorageReference mStorageRef;
    private StorageReference medicalStoreImagePathRef;
    private FloatingActionButton fab_start_nevigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_medical_store_detail);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        Intent intent = getIntent();
        medicalStore = (MedicalStore) intent.getParcelableExtra(Constant.INTENT_PASSING_SINGLE_HEALTH_MEDICAL_STORE);

        fab_start_nevigation = (FloatingActionButton) findViewById(R.id.fab_health_medical_store_start_nevigation);
        textViewMedicalStoreName = (TextView) findViewById(R.id.textView_medical_store_Detail_name);
        textViewMedicalStoreAddress = (TextView) findViewById(R.id.textView_medical_store_Detail_address);
        textViewMedicalStoreContact = (TextView) findViewById(R.id.textView_medical_store_Detail_contact);
        imageViewMedicalStoreImage = (ImageView) findViewById(R.id.imageView_medical_store_Detail_image);

        textViewMedicalStoreName.setText(medicalStore.getName());
        textViewMedicalStoreAddress.setText(medicalStore.getAddress());
        textViewMedicalStoreContact.setText(medicalStore.getContactNo());


        fab_start_nevigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "google.navigation:q="+medicalStore.getLatitude()+","+medicalStore.getLongitude();
//                String uri = "geo:0,0?q="+hospital.getLatitude()+","+hospital.getLongitude();
                Uri gmmIntentUri = Uri.parse(uri);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        medicalStoreImagePathRef = mStorageRef.child(Constant.FIREBASE_STORAGE_REFRENCE_IMAGES)
                .child(Constant.FIREBASE_STORAGE_REFRENCE_HEALTH)
                .child(Constant.FIREBASE_STORAGE_REFRENCE_MEDICAL_STORE)
                .child(medicalStore.getImage());

        medicalStoreImagePathRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Uri imageUri = uri;
                Picasso.with(getApplicationContext())
                        .load(uri).placeholder(R.drawable.no_image_available)
                        .error(R.drawable.no_image_available)
                        .into(imageViewMedicalStoreImage);
            }
        });

    }
}
