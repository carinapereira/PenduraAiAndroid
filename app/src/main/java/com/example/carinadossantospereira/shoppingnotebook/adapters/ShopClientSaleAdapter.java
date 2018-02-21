package com.example.carinadossantospereira.shoppingnotebook.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.carinadossantospereira.shoppingnotebook.AddValueActivity;
import com.example.carinadossantospereira.shoppingnotebook.R;
import com.example.carinadossantospereira.shoppingnotebook.models.ShopClient;

import java.util.ArrayList;

/**
 * Created by carinadossantospereira on 15/02/2018.
 */

public class ShopClientSaleAdapter extends RecyclerView.Adapter  {
    private Context context;
    private ArrayList<ShopClient> shopClients;

    public ShopClientSaleAdapter(Context context, ArrayList<ShopClient> shopClients){
        this.context = context;
        this.shopClients = shopClients;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_list_cliente_sales_item,parent,false);
        ShopClientSaleAdapter.ViewHolder holder = new ShopClientSaleAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ShopClientSaleAdapter.ViewHolder hold = (ShopClientSaleAdapter.ViewHolder) holder;
        ShopClient cli = shopClients.get(position);

        hold.tvName.setText(cli.getName());
        hold.tvPhone.setText(cli.getPhone());
        hold.tvCredito.setText(cli.getCreditLimit().toString());
    }

    @Override
    public int getItemCount() {
        return shopClients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final Context context;
        final TextView tvName;
        final TextView tvPhone;
        final TextView tvCredito;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);
            tvName =  itemView.findViewById(R.id.tvNameSale);
            tvPhone = itemView.findViewById(R.id.tvPhoneSale);
            tvCredito = itemView.findViewById(R.id.tvCreditSale);
        }

        @Override
        public void onClick(View view) {
            openRegisterShopClient(getLayoutPosition());
        }

    }

    public void openRegisterShopClient(int position){
        ShopClient shopClient = shopClients.get(position);

        if(!TextUtils.isEmpty(shopClient.getKey().toString())) {
            Intent intent = new Intent(context, AddValueActivity.class);
            intent.putExtra("shopClient", shopClient);
            context.startActivity(intent);
        }
    }
}
