package com.appface.akhil.stockchecker.model;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;
import io.reactivex.Completable;

public class StockRepository {

    private StockDao stockDao;
    private LiveData<List<Stock>> allStocks;

    public StockRepository(Application application) {
        StockDatabase stockDatabase = StockDatabase.getInstance(application);
        stockDao = stockDatabase.stockdao();
        allStocks = stockDao.getAllStocks();
    }

    public Completable insert(final Stock stock) {
        return stockDao.insert(stock);
    }

    public Completable delete(Stock stock) {
        return stockDao.delete(stock);
    }

    public Completable deleteAllStocks() {
        return stockDao.deleteAllLocalStockInfo();
    }

    public LiveData<List<Stock>> getAllStocks() {
        return allStocks;
    }

}
