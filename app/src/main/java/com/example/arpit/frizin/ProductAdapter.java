package com.example.arpit.frizin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by arpit on 11/3/16.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
    List<Product> list;
    private ImageLoader imageLoader;
    private Context context;


    public ProductAdapter(List<Product> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Product stationary = list.get(position);
        //Loading image from url
        int x;
        final String name=stationary.getName();
        final Long price=stationary.getPrice();
        final Long[] quantity = new Long[list.size()];
        final String desc=stationary.getDesc();
        Log.d("OnBind", "calling on bind");
        imageLoader = Singleton.getInstance().getImageLoader();
        imageLoader.get(stationary.getImgUrl(), ImageLoader.getImageListener(holder.imageView, R.mipmap.ic_launcher, R.drawable.abc_btn_check_material));
        holder.textViewName.setText(stationary.getName());
        holder.textViewPrice.setText(stationary.getPrice().toString());

        holder.imageView.setImageUrl(stationary.getImgUrl(), imageLoader);
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(context,"Arpit",Toast.LENGTH_LONG).show();

            }
        });







    }
    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener, View.OnLongClickListener {
        public NetworkImageView imageView;
        public TextView textViewName;

        public TextView textViewPrice;
        private ItemClickListener clickListener;


        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            context=itemView.getContext();
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageViewHero);
            textViewName = (TextView) itemView.findViewById(R.id.name);

            textViewPrice = (TextView) itemView.findViewById(R.id.price);
            itemView.setOnLongClickListener(this);

        }
        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }


        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getPosition());

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
