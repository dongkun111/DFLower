package app.example.phanmembanhoa.ViewHolder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.example.phanmembanhoa.Cart;
import app.example.phanmembanhoa.Database.Database;
import app.example.phanmembanhoa.Home;
import app.example.phanmembanhoa.Model.Flower;
import app.example.phanmembanhoa.Model.Order;
import app.example.phanmembanhoa.R;

public class FlowerDetails extends AppCompatActivity {

    ImageView imageView;
    TextView itemName, itemPrice, itemRating, itemDetail;
    RatingBar ratingBar;

    Button btnCart;
    ImageButton btnBack;
    ElegantNumberButton numberButton;

    String flowerId, name, price, rating, imageUrl, detail;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower_details);

        Intent intent = getIntent();

        ImageButton cart_button = findViewById(R.id.cart_button);
        cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(FlowerDetails.this, Cart.class);
                startActivity(cartIntent);
            }
        });

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(FlowerDetails.this,Home.class);
                startActivity(home);
            }
        });
        numberButton = (ElegantNumberButton)findViewById(R.id.number_button);
        btnCart = (Button)findViewById(R.id.btnCart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Database(getBaseContext()).addToCart(new Order(
                        flowerId,
                        name,
                        numberButton.getNumber(),
                        price,
                        detail
                ));
                Toast.makeText(FlowerDetails.this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
            }
        });


//        if(getIntent()!=null){
//            flowerId = intent.getStringExtra("id");
//        }
        flowerId = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        rating = intent.getStringExtra("rating");
        imageUrl = intent.getStringExtra("image");
        detail = intent.getStringExtra("detail");

        imageView = findViewById(R.id.flower_image);
        itemName = findViewById(R.id.flower_name);
        itemPrice = findViewById(R.id.flower_price);
        itemRating = findViewById(R.id.rating);
        ratingBar = findViewById(R.id.ratingBar);
        itemDetail = findViewById(R.id.flower_details);

        Glide.with(getApplicationContext()).load(imageUrl).into(imageView);
        itemName.setText(name);
        itemPrice.setText(" "+price+"đ");
        itemRating.setText(rating);
        ratingBar.setRating(Float.parseFloat(rating));
        itemDetail.setText(detail);
    }
}