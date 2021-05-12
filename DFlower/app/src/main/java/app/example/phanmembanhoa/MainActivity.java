package app.example.phanmembanhoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import info.hoang8f.widget.FButton;

public class MainActivity extends AppCompatActivity {

    Button btnDn,btnDk;
    TextView txtSlogan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDn = (Button)findViewById(R.id.btnSignActive);
        btnDk = (Button)findViewById(R.id.btnSignup);

        txtSlogan = (TextView)findViewById(R.id.txtSlogan);
        Typeface face =  Typeface.createFromAsset(getAssets(),"fonts/SomeWeatz.ttf");
        txtSlogan.setTypeface(face);
        btnDn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnDk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp =  new Intent(MainActivity.this,SignUp.class);
                startActivity(signUp);

            }
        });

        btnDn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signIn =  new Intent(MainActivity.this,SignActive.class);
                startActivity(signIn);

            }
        });

    }
}
