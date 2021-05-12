package app.example.phanmembanhoa.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import app.example.phanmembanhoa.FlowerList;
import app.example.phanmembanhoa.Model.Category;
import app.example.phanmembanhoa.Model.Flower;
import app.example.phanmembanhoa.R;
import app.example.phanmembanhoa.ViewHolder.FlowerDetails;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    FirebaseDatabase database;
    private Context context;
    private List<Category> lstCate;

    public CategoryAdapter(Context context, List<Category> lstCate) {
        this.context = context;
        this.lstCate = lstCate;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.category_recycler_items, parent, false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, final int position) {

        holder.categoryName.setText(lstCate.get(position).getName());


        Glide.with(context).load(lstCate.get(position).getImgUrl()).into(holder.categoryImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, FlowerList.class);
                i.putExtra("id", position);

                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstCate.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{

        ImageView categoryImage;
        TextView categoryName;

        public CategoryViewHolder(@NonNull View itemView){
            super(itemView);

            categoryName = itemView.findViewById(R.id.category_name);
            categoryImage = itemView.findViewById(R.id.category_image);
        }
    }

}
