package pratik.com.shauryatoday.dewasii_01.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.FirebaseDatabase;

import pratik.com.shauryatoday.dewasii_01.R;
import pratik.com.shauryatoday.dewasii_01.UI.categories.Health.healthSubCategories;

public class Category extends AppCompatActivity {

    private CardView cardViewHealth;
    private FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initilizeScreen();

        cardViewHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Category.this, healthSubCategories.class);
                startActivity(intent);
            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();


    }

    public void initilizeScreen(){
        cardViewHealth = (CardView) findViewById(R.id.card_view_health);
    }
}
