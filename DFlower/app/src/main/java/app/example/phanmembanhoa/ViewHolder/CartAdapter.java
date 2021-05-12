package app.example.phanmembanhoa.ViewHolder;


import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import app.example.phanmembanhoa.Cart;
import app.example.phanmembanhoa.Common.Common;
import app.example.phanmembanhoa.Database.Database;
import app.example.phanmembanhoa.Interface.ItemClickListener;
import app.example.phanmembanhoa.Model.Order;
import app.example.phanmembanhoa.R;

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_cart_name,txt_price;
    public ImageView img_cart_count;
    public ImageButton btn_delete1;


    private ItemClickListener itemClickListener;

    public void setTxt_cart_name(TextView txt_cart_name) {
        this.txt_cart_name = txt_cart_name;
    }



    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_cart_name = (TextView)itemView.findViewById(R.id.cart_item_name);
        txt_price = (TextView)itemView.findViewById(R.id.cart_item_price);
        img_cart_count = (ImageView)itemView.findViewById(R.id.cart_item_count);
        btn_delete1 = (ImageButton)itemView.findViewById(R.id.btnDelete);
    }

    @Override
    public void onClick(View v) {

    }


}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Order> listData = new ArrayList<>();
    private Context context;


    public CartAdapter (List<Order> listData, Context context){
        this.listData = listData;
        this.context= context;
    }
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout,parent,false);
        return new CartViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(""+listData.get(position).getQuantity(), Color.RED);
        holder.img_cart_count.setImageDrawable(drawable);

        Locale locale = new Locale("vn","VN");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt(listData.get(position).getPrice()))*(Integer.parseInt(listData.get(position).getQuantity()));
        holder.txt_price.setText(fmt.format(price));
        int le = listData.size();
        holder.btn_delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getItemCount() > 0)
                {
                    listData.remove(position);
                    new Database(context).deleteItemCart(position);
                    new Database(context).cleanCart();

                    final List<Order> lstBill = ReloadBill();
                    LoadBill(listData);
                    context.startActivity(new Intent(context,Cart.class));
                    notifyDataSetChanged();
                }
            }
        });
        holder.txt_cart_name.setText(listData.get(position).getProductName());



    }
    public List<Order> ReloadBill()
    {
        return  new Database(context).getCarts();
    }
    public void LoadBill(List<Order> lstbl)
    {
        for(int i = 0 ;i<lstbl.size();i++)
        {
            new Database(context).addToCart(lstbl.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
