package app.example.phanmembanhoa;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import app.example.phanmembanhoa.Common.Common;
import app.example.phanmembanhoa.Interface.ItemClickListener;
import app.example.phanmembanhoa.Model.Category;
import app.example.phanmembanhoa.Model.Flower;
import app.example.phanmembanhoa.Model.Order;
import app.example.phanmembanhoa.Model.User;
import app.example.phanmembanhoa.Service.ListenOrder;
import app.example.phanmembanhoa.adapter.AllMenuAdapter;
import app.example.phanmembanhoa.adapter.CategoryAdapter;
import app.example.phanmembanhoa.adapter.PopularAdapter;
import app.example.phanmembanhoa.adapter.RecommendedAdapter;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {


    RecyclerView popularRecyclerView, allMenuRecyclerView, categoryRecyclerView;
    PopularAdapter popularAdapter;

    RecyclerView recommendedRecyclerView;
    RecommendedAdapter recommendedAdapter;
    CategoryAdapter categoryAdapter;


    TextView txtFullName;
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    AllMenuAdapter allMenuAdapter;

    public static ArrayList<Flower> lstF = new ArrayList<>();
    public static ArrayList<Category> lstCate = new ArrayList<>();
    ArrayList<Flower> lstR = new ArrayList<>();
    ArrayList<Flower> lstP = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference db, dt, dtb;
    Button cartButton;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        database = FirebaseDatabase.getInstance();
        db = database.getReference();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        ImageButton cart_button = findViewById(R.id.cart_button);
        cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(Home.this,Cart.class);
                startActivity(cartIntent);
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        GetAllFlower(new FirebaseCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCallBack(ArrayList<Flower> list) {
                lstF = list;
                //     SetTable();
                getAllFlower(lstF); // Đây
                RandomList();
                GetPopular();
                getRecommendedData(lstR);
                getPopularData(lstP);
            }
        });
        GetCategory();
        getCategory(lstCate);

        //Service
        Intent service = new Intent(Home.this, ListenOrder.class);
        startService(service);
    }

    private void RandomList() {

        Random rd = new Random();
        for(int i = 0; i < 6; i++){
            lstR.add(lstF.get(rd.nextInt(lstF.size())));
        }
    }

    private void GetCategory(){
        database = FirebaseDatabase.getInstance();
        dt = database.getReference("Category");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Category ct = ds.getValue(Category.class);
                    lstCate.add(ct);
                }
                getCategory(lstCate);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        dt.addListenerForSingleValueEvent(valueEventListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void GetPopular(){
        Comparator<Flower> compareById = (Flower o1, Flower o2) -> o1.getHeart().compareTo(o2.getHeart());
        Collections.sort(lstF, compareById);
        Collections.sort(lstF, compareById.reversed());
        int d = 0;
        for(int i = lstF.size() - 1; i > 0; i--){
            if(d < 6){
                lstP.add((lstF.get(i)));
                d++;
            }
        }
    }

    private void getPopularData(List<Flower> popularList){
        popularRecyclerView = findViewById(R.id.popular_recycler);
        popularAdapter = new PopularAdapter(this, popularList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        popularRecyclerView.setLayoutManager(layoutManager);
        popularRecyclerView.setAdapter(popularAdapter);
    }

    private void getCategory(List<Category> lstCate){
        categoryRecyclerView = findViewById(R.id.category_recycler);
        categoryAdapter = new CategoryAdapter(this, lstCate);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void getRecommendedData(List<Flower> recommendedList){
        recommendedRecyclerView = findViewById(R.id.recommended_recycler);
        recommendedAdapter = new RecommendedAdapter(this, recommendedList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recommendedRecyclerView.setLayoutManager(layoutManager);
        recommendedRecyclerView.setAdapter(recommendedAdapter);
    }

    private void getAllFlower(List<Flower> flowers){

        allMenuRecyclerView = findViewById(R.id.all_menu_recycler);
        allMenuAdapter = new AllMenuAdapter(this, flowers);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        allMenuRecyclerView.setLayoutManager(layoutManager);
        allMenuRecyclerView.setAdapter(allMenuAdapter);
        allMenuAdapter.notifyDataSetChanged();

    }
    private void GetAllFlower(final Home.FirebaseCallBack firebaseCallBack)
    {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {

                    Flower tam = ds.getValue(Flower.class);
                    lstF.add(tam);
                }
                firebaseCallBack.onCallBack(lstF);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Lỗi kết nối mạng",Toast.LENGTH_SHORT).show();
            }
        };
        db.child("Flower").addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item){

        return  super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id =menuItem.getItemId();

        if (id == R.id.nav_menu) {

        }
        else if (id == R.id.nav_profile) {
            Intent profileIntent = new Intent(Home.this,Profile.class);
            startActivity(profileIntent);

        }else if (id == R.id.nav_cart) {
            Intent cartIntent = new Intent(Home.this,Cart.class);
            startActivity(cartIntent);

        } else if (id == R.id.nav_order) {
            Intent orderIntent = new Intent(Home.this,OrderStatus.class);
            startActivity(orderIntent);

        } else if (id == R.id.nav_log_out) {
            Intent signOut = new Intent(Home.this, MainActivity.class);
            signOut.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signOut);

        }
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private interface  FirebaseCallBack{

        void onCallBack(ArrayList<Flower> list);
    }


}
