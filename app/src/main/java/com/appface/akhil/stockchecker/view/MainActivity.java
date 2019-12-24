package com.appface.akhil.stockchecker.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.appface.akhil.stockchecker.R;
import com.appface.akhil.stockchecker.StockViewModel;
import com.appface.akhil.stockchecker.adapter.StockAdapter;
import com.appface.akhil.stockchecker.model.Stock;
import com.appface.akhil.stockchecker.model.pojo.ProductDetails;
import com.appface.akhil.stockchecker.networking.NetworkModule;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity  {

    @BindView(R.id.btn_scanner)
    FloatingActionButton scanner;
    private static final String TAG = "MainActivity";
    private StockViewModel stockViewModel;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final StockAdapter adapter = new StockAdapter();
        recyclerView.setAdapter(adapter);
        final MediaPlayer beepsound = MediaPlayer.create(this, R.raw.censoredbeep);

        stockViewModel = ViewModelProviders.of(this).get(StockViewModel.class);
        stockViewModel.getAllStocks().observe(this, new Observer<List<Stock>>() {
            @Override
            public void onChanged(List<Stock> stocks) {
                Toast.makeText(MainActivity.this, "View Model started", Toast.LENGTH_SHORT).show();
                adapter.setStocks(stocks);
            }
        });
        Bundle bundle = getIntent().getExtras();
        String scanResult;
        final Dialog dialog = new Dialog(this);

        if (bundle != null) {
            scanResult = bundle.getString("ScanResult");
            Toast.makeText(this, "Item Scanned " + scanResult, Toast.LENGTH_SHORT).show();

            NetworkModule networkModule = NetworkModule.getInstance();

            Single<ProductDetails> productDetailsSingle = networkModule.getApi().retrieveproduct(scanResult);
            productDetailsSingle.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<ProductDetails>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(ProductDetails productDetails) {
                            if(productDetails.getStatusCode() == 200)
                            {
                                Log.d(TAG, "onSuccess: " + productDetails.toString());
                                dialog.setContentView(R.layout.pop_dialog);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.show();
                            } else if(productDetails.getStatusCode() == 400){
                                dialog.setContentView(R.layout.pop_dialog_out_of_stock);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.show();
                                insertStock(scanResult);
                                beepsound.start();
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(MainActivity.this, "Something went wrong with network call. Check your network status", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "onError: Something went wrong with network call ",  e );
                        }
                    });
        }
            final Context context = this;
            scanner = findViewById(R.id.btn_scanner);
            scanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ScannerActivity.class);
                    startActivity(intent);
                }
            });

    }

    void insertStock(String barcode) {

        Stock newstock = new Stock("", "", Long.parseLong(barcode), 0, 0);

        mDisposable.add(stockViewModel.insert(newstock)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                               @Override
                               public void run() throws Exception {
                               }
                           }, throwable -> Log.e(TAG, "Unable to update stock", throwable)));
    }
}