package app.example.phanmembanhoa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Locale;

import app.example.phanmembanhoa.Common.Common;
import app.example.phanmembanhoa.Database.Database;
import app.example.phanmembanhoa.Model.Order;
import app.example.phanmembanhoa.Model.Request;
import app.example.phanmembanhoa.ViewHolder.CartAdapter;
import app.example.phanmembanhoa.ViewHolder.FlowerDetails;
import info.hoang8f.widget.FButton;

import static app.example.phanmembanhoa.R.id.btnPlaceOrder;


public class Cart extends AppCompatActivity  {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;


    TextView txtTotalPrice;
    TextView txtStatus;
    Button btnPlace,btnDelete, btnBack;
    ImageButton btnDelete_1;

    List<Order> cart = new ArrayList<>();

    CartAdapter adapter;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        requests = FirebaseDatabase.getInstance().getReference();

        //Init
        recyclerView = (RecyclerView)findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView)findViewById(R.id.total);

        btnPlace = (Button)findViewById(R.id.btnPlaceOrder);
        btnDelete = (Button)findViewById(R.id.btnDeleteAll);
        btnBack = (Button)findViewById(R.id.btnBack);
        btnDelete_1 = (ImageButton)findViewById(R.id.btnDelete);


        loadListFlower();

        btnPlace.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showAlertDialog();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Database db1 = new Database(getBaseContext());
                db1.cleanCart();

                Toast.makeText(Cart.this, "Đã xóa giở hàng!", Toast.LENGTH_SHORT).show();
                Intent cart = new Intent(Cart.this,Cart.class);
                startActivity(cart);

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(Cart.this, Home.class);
                startActivity(home);
            }
        });
    }





    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("Bước cuối cùng!");
        alertDialog.setMessage("Nhập địa chỉ: ");

        final EditText edtAddress = new EditText(Cart.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        edtAddress.setLayoutParams(lp);
        alertDialog.setView(edtAddress);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Request request = new Request(
                        Common.currentUser.getName(),
                        Common.currentUser.getPhone(),
                        edtAddress.getText().toString(),
                        txtTotalPrice.getText().toString(),
                        cart

                );

                requests.child("Requests").child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);
                //xoa gio hang
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this, "Cảm ơn bạn, đã xác nhận đơn hàng", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();

    }

    private void loadListFlower() {
        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);

        //Tinh tong tien
        int total = 0;
        for(Order order:cart)
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        Locale locale = new Locale("vn","VN");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));

    }


}
