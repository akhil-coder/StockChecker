package com.appface.akhil.stockchecker.networking;


import com.appface.akhil.stockchecker.model.pojo.ProductDetails;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("stockinventory/retrieveproduct")
    Single<ProductDetails> retrieveproduct(@Query("barcode") String barcode);

}


