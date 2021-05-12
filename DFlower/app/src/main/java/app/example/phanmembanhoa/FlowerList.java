package app.example.phanmembanhoa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import app.example.phanmembanhoa.Interface.ItemClickListener;
import app.example.phanmembanhoa.Model.Category;
import app.example.phanmembanhoa.Model.Flower;
import app.example.phanmembanhoa.ViewHolder.FlowerDetails;
import app.example.phanmembanhoa.adapter.AllMenuAdapter;
import app.example.phanmembanhoa.adapter.CategoryAdapter;

public class FlowerList extends AppCompatActivity {

    RecyclerView  categoryRecyclerView,allMenuRecyclerView;
    CategoryAdapter categoryAdapter;
    AllMenuAdapter allMenuAdapter;
    List<Flower> lstL = new ArrayList<>();

    ImageButton btnBack, btnCart;

    String categoryId = "";

    List<String> suggestList = new ArrayList<>();

    FirebaseDatabase database;
    DatabaseReference flowerList;
    MaterialSearchBar materialSearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower_list);

        getCategory(Home.lstCate);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);
        getListFlowerinCate(id);
        getAllFlower(lstL);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(FlowerList.this,Home.class);
                startActivity(home);
            }
        });

        ImageButton btnCart = findViewById(R.id.cart_button);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(FlowerList.this,Cart.class);
                startActivity(cartIntent);
            }
        });

    }



    void getListFlowerinCate(int id)
    {
        List<Flower> temp = Home.lstF;
        for (int i = 0;i<temp.size();i++)
        {
            if(temp.get(i).getIdCategory() == id)
            {
                lstL.add(temp.get(i));
            }
        }
    }
    private void getCategory(List<Category> lstCate){
        categoryRecyclerView = findViewById(R.id.category_recycler);
        categoryAdapter = new CategoryAdapter(this, lstCate);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }
    private void getAllFlower(List<Flower> flowers){

        allMenuRecyclerView = findViewById(R.id.flower_list_recycler);
        allMenuAdapter = new AllMenuAdapter(this, flowers);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        allMenuRecyclerView.setLayoutManager(layoutManager);
        allMenuRecyclerView.setAdapter(allMenuAdapter);
        allMenuAdapter.notifyDataSetChanged();

    }
}
