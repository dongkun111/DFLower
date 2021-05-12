package app.example.phanmembanhoa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import app.example.phanmembanhoa.Common.Common;

public class Profile extends AppCompatActivity  {

    TextView txtName, txtPhone, txtFb, txtAddress, txtMail;

    ImageView img_profile;

    ImageButton btnBack;

    FirebaseDatabase db;
    DatabaseReference profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtName = (TextView)findViewById(R.id.txt_name);
        txtPhone = (TextView)findViewById(R.id.txt_phone);
        txtFb = (TextView)findViewById(R.id.txt_fb);
        txtAddress = (TextView)findViewById(R.id.txt_address);
        txtMail = (TextView)findViewById(R.id.txt_mail);

        img_profile = (ImageView)findViewById(R.id.img_profile);

        db = FirebaseDatabase.getInstance();
        profile = db.getReference("Account");

        btnBack = (ImageButton)findViewById(R.id.imageButton);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(Profile.this, Home.class);
                startActivity(home);
            }
        });

        txtName.setText(Common.currentUser.getName());
        txtPhone.setText(Common.currentUser.getPhone());
        txtFb.setText("linhhon.dedai");
        txtMail.setText("dongkun111@gmail.com");
        txtAddress.setText("Hòa Lợi, Bến Cát, Bình dương");
    }
}