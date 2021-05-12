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


import java.util.List;

import app.example.phanmembanhoa.Model.Flower;
import app.example.phanmembanhoa.R;
import app.example.phanmembanhoa.ViewHolder.FlowerDetails;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.RecommendedViewHolder> {

    private Context context;
    private List<Flower> recommendedList;

    public RecommendedAdapter(Context context, List<Flower> recommendedList){
        this.context = context;
        this.recommendedList = recommendedList;
    }




    @NonNull
    @Override
    public RecommendedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recommended_recycler_items, parent, false);
        return new RecommendedViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedViewHolder holder,final int position) {
        holder.recommendedName.setText(recommendedList.get(position).getName());
        holder.recommendedRating.setText(recommendedList.get(position).getStar().toString());
        holder.recommendedCharges.setText("Hoa đẹp lắm");
        holder.recommendedDeliveryTime.setText("1 Giờ");
        holder.recommendedPrice.setText(" "+recommendedList.get(position).getPrice().toString()+"đ");

        Glide.with(context).load(recommendedList.get(position).getImgUrl()).into(holder.recommendedImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, FlowerDetails.class);
                i.putExtra("name", recommendedList.get(position).getName());
                i.putExtra("price", recommendedList.get(position).getPrice().toString());
                i.putExtra("rating", recommendedList.get(position).getStar().toString());
                i.putExtra("detail",recommendedList.get(position).getDetail());
                i.putExtra("image", recommendedList.get(position).getImgUrl());

                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recommendedList.size();
    }

    public static class RecommendedViewHolder extends RecyclerView.ViewHolder {

        ImageView recommendedImage;
        TextView recommendedName, recommendedRating, recommendedDeliveryTime, recommendedCharges, recommendedPrice;

        public RecommendedViewHolder(@NonNull View itemView) {
            super(itemView);

            recommendedImage = itemView.findViewById(R.id.recommended_image);
            recommendedName = itemView.findViewById(R.id.recommended_name);
            recommendedRating = itemView.findViewById(R.id.recommended_rating);
            recommendedDeliveryTime = itemView.findViewById(R.id.recommended_delivery_time);
            recommendedCharges = itemView.findViewById(R.id.delivery_type);
            recommendedCharges = itemView.findViewById(R.id.delivery_type);
            recommendedPrice = itemView.findViewById(R.id.recommended_price);

        }
    }
}
