package pratik.com.shauryatoday.dewasii_01.UI.categories.Health.hospital;

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
import pratik.com.shauryatoday.dewasii_01.utility.Constant;

public class healthHospitalDetail extends AppCompatActivity {

    private Hospital hospital;
    private TextView textViewHospitalName, textViewHospitalAddress, textViewHospitalContact;
    private ImageView imageViewHospitalImage;
    private StorageReference mStorageRef;
    private StorageReference hospitalImagePathRef;
    private FloatingActionButton fab_start_nevigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_hospital_detail);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        Intent intent = getIntent();
        hospital = (Hospital) intent.getParcelableExtra(Constant.INTENT_PASSING_SINGLE_HEALTH_HOSPITAL);

        fab_start_nevigation = (FloatingActionButton) findViewById(R.id.fab_health_hospital_start_nevigation);
        textViewHospitalName = (TextView) findViewById(R.id.textView_hospitalDetail_name);
        textViewHospitalAddress = (TextView) findViewById(R.id.textView_hospitalDetail_address);
        textViewHospitalContact = (TextView) findViewById(R.id.textView_hospitalDetail_contact);
        imageViewHospitalImage = (ImageView) findViewById(R.id.imageView_hospitalDetail_image);

        textViewHospitalName.setText(hospital.getName());
        textViewHospitalAddress.setText(hospital.getAddress());
        textViewHospitalContact.setText(hospital.getContactNo());

        fab_start_nevigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "google.navigation:q="+hospital.getLatitude()+","+hospital.getLongitude();
//                String uri = "geo:0,0?q="+hospital.getLatitude()+","+hospital.getLongitude();
                Uri gmmIntentUri = Uri.parse(uri);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        hospitalImagePathRef = mStorageRef.child(Constant.FIREBASE_STORAGE_REFRENCE_IMAGES)
                .child(Constant.FIREBASE_STORAGE_REFRENCE_HEALTH)
                .child(Constant.FIREBASE_STORAGE_REFRENCE_HOSPITAL)
                .child(hospital.getImage());

        hospitalImagePathRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Uri imageUri = uri;
                Picasso.with(getApplicationContext())
                        .load(uri).placeholder(R.drawable.no_image_available)
                        .error(R.drawable.no_image_available)
                        .into(imageViewHospitalImage);
            }
        });

    }
}
