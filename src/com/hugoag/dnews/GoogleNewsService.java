package com.hugoag.dnews;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

import com.hugoag.dnews.model.gnews.GoogleNews;

public interface GoogleNewsService {

	/**
	 * Busqueda de noticias.
	 *
	 * @param query
	 * @param callback
	 */
	@GET("/ajax/services/search/news?hl=es&rsz=8&v=1.0")
	void searchGoogleNews(@Query("q") String query, Callback<GoogleNews> callback);
	
}
