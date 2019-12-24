package com.appface.akhil.stockchecker;

import android.app.Application;

import com.appface.akhil.stockchecker.model.Stock;
import com.appface.akhil.stockchecker.model.StockRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import io.reactivex.Completable;

public class StockViewModel extends AndroidViewModel {

    private StockRepository repository;
    private LiveData<List<Stock>> allStocks;

    public StockViewModel(@NonNull Application application) {
        super(application);
        repository = new StockRepository(application);
        allStocks = repository.getAllStocks();
    }

    public Completable insert(Stock stock) {
        return repository.insert(stock);
    }

    public Completable delete(Stock stock)
    {
        return repository.delete(stock);
    }

    public Completable deleteAllStocks()
    {
        return repository.deleteAllStocks();
    }

    public LiveData<List<Stock>> getAllStocks( )
    {
        return repository.getAllStocks();
    }
}
