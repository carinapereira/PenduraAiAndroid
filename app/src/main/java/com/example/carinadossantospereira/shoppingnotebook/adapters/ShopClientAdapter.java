package com.example.carinadossantospereira.shoppingnotebook.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.carinadossantospereira.shoppingnotebook.AddClientActivity;
import com.example.carinadossantospereira.shoppingnotebook.R;
import com.example.carinadossantospereira.shoppingnotebook.models.ShopClient;

import java.util.ArrayList;

/**
 * Created by carinadossantospereira on 09/12/17.
 */

public class ShopClientAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<ShopClient> shopClients;

    public ShopClientAdapter(Context context, ArrayList<ShopClient> shopClients){
        this.context = context;
        this.shopClients = shopClients;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_list_client_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder hold = (ViewHolder) holder;
        ShopClient cli = shopClients.get(position);

        hold.tvName.setText(cli.getName());
        hold.tvPhone.setText(cli.getPhone());

    }

    @Override
    public int getItemCount() {
        return shopClients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final Context context;
        final TextView tvName;
        final TextView tvPhone;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            tvName =  itemView.findViewById(R.id.textViewName);
            tvPhone = itemView.findViewById(R.id.textViewPhone);
        }

        @Override
        public void onClick(View view) {
            openRegisterShopClient(getLayoutPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }

    public void openRegisterShopClient(int position){
        ShopClient shopClient = shopClients.get(position);

        if(!TextUtils.isEmpty(shopClient.getKey().toString())) {
            Intent intent = new Intent(context, AddClientActivity.class);
            intent.putExtra("shopClient", shopClient);
            context.startActivity(intent);
        }
    }
}
