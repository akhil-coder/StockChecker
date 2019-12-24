package com.appface.akhil.stockchecker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appface.akhil.stockchecker.R;
import com.appface.akhil.stockchecker.model.Stock;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockHolder> {

    private List<Stock> stocks = new ArrayList<>();

    @NonNull
    @Override
    public StockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stock_item_recyclerview, parent, false);
        return new StockHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockHolder holder, int position) {
        Stock currentstock = stocks.get(position);
        holder.tv_barcode.setText(String.valueOf(currentstock.getBarcode()));
        holder.tv_status.setText("Not in stock");
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }

    public void setStocks(List<Stock> stocks){
        this.stocks = stocks;
        notifyDataSetChanged();
    }

    class StockHolder extends RecyclerView.ViewHolder {
        private TextView tv_barcode;
        private TextView tv_status;

        public StockHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_barcode = itemView.findViewById(R.id.tv_barcode);
            this.tv_status = itemView.findViewById(R.id.tv_status);
        }
    }
}
