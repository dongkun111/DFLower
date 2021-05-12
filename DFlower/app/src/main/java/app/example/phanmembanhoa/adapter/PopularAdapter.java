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

import app.example.phanmembanhoa.Model.Flower;
import app.example.phanmembanhoa.R;
import app.example.phanmembanhoa.ViewHolder.FlowerDetails;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {


    FirebaseDatabase database;
    private Context context;
    private List<Flower> popularList;

    public PopularAdapter(Context context, List<Flower> popularList){
        this.context = context;
        this.popularList = popularList;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.popular_recycler_items, parent, false);

        return new PopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, final int position) {

        holder.popularName.setText(popularList.get(position).getName());


        Glide.with(context).load(popularList.get(position).getImgUrl()).into(holder.popularImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, FlowerDetails.class);
                i.putExtra("name", popularList.get(position).getName());
                i.putExtra("price", popularList.get(position).getPrice().toString());
                i.putExtra("rating", popularList.get(position).getStar().toString());
                i.putExtra("detail",popularList.get(position).getDetail());
                i.putExtra("image", popularList.get(position).getImgUrl());

                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularList.size();
    }

    public static class PopularViewHolder extends RecyclerView.ViewHolder{

        ImageView popularImage;
        TextView popularName;

        public PopularViewHolder(@NonNull View itemView){
            super(itemView);

            popularName = itemView.findViewById(R.id.all_menu_name);
            popularImage = itemView.findViewById(R.id.all_menu_image);
        }
    }

}
