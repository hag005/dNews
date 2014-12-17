package com.hugoag.dnews.activities;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.google.common.collect.Lists;
import com.hugoag.dnews.App;
import com.hugoag.dnews.GoogleNewsService;
import com.hugoag.dnews.R;
import com.hugoag.dnews.model.Noticia;
import com.hugoag.dnews.model.gnews.GoogleNews;
import com.hugoag.dnews.model.gnews.Result;
import com.hugoag.dnews.views.NoticiaAdapter;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

/**
 * Clase principal encargada de mostrar el listado de {@link Noticia}.
 */
public final class MainActivity extends Activity implements OnItemClickListener {

	/**
	 * Adaptador de la {@link Noticia} para usar los listados.
	 */
	private NoticiaAdapter adapter;
	
	private String search_word;
	
	private SearchView searchView;

	/**
	 * Logger
	 */
	static final Logger LOG = LoggerFactory.getLogger(MainActivity.class);

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */

	/**
	 * Pull to refresh
	 */
	PullToRefreshListView listView;
	
	/**
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    
	    this.searchView = (SearchView) menu.findItem(R.id.search).getActionView();
	    
	    this.searchView.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				MainActivity.this.search_word = query;
				MainActivity.this.searchView.setIconified(true);
				MainActivity.this.searchView.onActionViewCollapsed();
				MainActivity.this.refresh();
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});

	    return true;
	}

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		
		ActionBar bar = this.getActionBar();
		bar.setIcon(R.drawable.api_icon);

		// Siempre inicializo
		super.onCreate(savedInstanceState);
		
		this.search_word = this.getString(R.string.default_searchWord);

		// Agrego el Layout
		this.setContentView(R.layout.activity_main);

		// ListView de noticias
		this.listView = (PullToRefreshListView) this.findViewById(R.id.pull_to_refresh_listview);

		// Construyo el adaptador de noticias
		this.adapter = new NoticiaAdapter(this);

		// Asigna el adaptador al ListActivity
		this.listView.setAdapter(this.adapter);
		
		// Bloque el scroll mientras esta en refresh
		this.listView.setLockScrollWhileRefreshing(true);

		// Listener de click
		this.listView.setOnItemClickListener(this);

		// Listener de refresh
		this.listView.setOnRefreshListener(new OnRefreshListener() {

			/**
			 * @see eu.erikw.PullToRefreshListView.OnRefreshListener#onRefresh()
			 */
			@Override
			public void onRefresh() {
				MainActivity.this.refresh();
				
				MainActivity.this.listView.onRefreshComplete();
			}
		});

		// Cargar las noticias desde el backend
		final App app = App.getInstance(this);

		if (app != null) {
			try {

				final List<Noticia> noticias = app.readNoticias(new File(this.getCacheDir(), "noticias.json"));
				LOG.debug("Backend: {}", noticias);

				if (noticias != null) {
					this.adapter.addAll(noticias, false);
				}

			} catch (final IOException e) {
				LOG.error("Error al leer", e);
			}
		}
		
		//this.adapter.addAll(Mockup.INSTANCE.buildNoticias());
		
		//Busca si hay noticias nuevas al abrir la aplicación, usando @string/default_searchWord
		refresh();
	}

	/**
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View,
	 * int, long)
	 */
	@Override
	public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {

		LOG.trace("Position: {}. Id: {}", position, id);
		//Toast.makeText(this, "Clicked row " + position, Toast.LENGTH_SHORT).show();

		final Noticia noticia = this.adapter.getItem(position);
		LOG.debug("Noticia: {}", noticia);
		
		this.verNoticia(view, noticia);
	}
	
	public void verNoticia(View view, Noticia noticia) {
		Intent i = new Intent(this, NoticiaActivity.class);
		i.putExtra("titulo", noticia.getTitulo());
		i.putExtra("imagen", noticia.getImagen());
		i.putExtra("autor", noticia.getAutor());
		i.putExtra("fecha", noticia.getFormatFecha());
		i.putExtra("likes", noticia.getLikes());
		i.putExtra("tags", noticia.getTag().toString());
		i.putExtra("contenido", noticia.getContenido());
		i.putExtra("resumen", noticia.getResumen());
		i.putExtra("link", noticia.getLink());
		startActivity(i);
	}

	/**
	 * Obtiene noticias desde Internet
	 */
	private void refresh() {

		final App app = App.getInstance(this);
		if (app == null) {
			return;
		}
		
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo == null || !netInfo.isConnected()) {
			Toast.makeText(this, "El dispositivo no esta conectado a Internet", Toast.LENGTH_SHORT).show();
			return;
		}

		LOG.debug("Loading News ..");
		final GoogleNewsService ns = app.getNewsService();

		/*
		 * CallBack!
		 */
		final Callback<GoogleNews> callback = new Callback<GoogleNews>() {

			/**
			 *
			 * @see retrofit.Callback#failure(retrofit.RetrofitError)
			 */
			@Override
			public void failure(final RetrofitError error) {
				LOG.error("Error: {}", error);
			}

			/**
			 *
			 * @see retrofit.Callback#success(java.lang.Object, retrofit.client.Response)
			 */
			@Override
			public void success(final GoogleNews gnews, final Response response) {
				LOG.debug("GNews: {}", gnews);
				final List<Noticia> news = Lists.newArrayList();

				// http://www.jsonschema2pojo.org/
				for (final Result r : gnews.getResponseData().getResults()) {

					final Noticia noticia = new Noticia();
					noticia.setTitulo(r.getTitleNoFormatting());
					noticia.setAutor(r.getPublisher());
					if (r.getImage() != null) {
						noticia.setImagen(r.getImage().getUrl());
					}
					noticia.setId(r.getTitleNoFormatting().hashCode());
					noticia.setResumen(r.getContent());
					noticia.setContenido(r.getContent());
					noticia.setFecha(r.getPublishedDate());
					noticia.setLink(r.getUnescapedUrl());

					news.add(noticia);

				}

				MainActivity.this.adapter.addAll(news, true);

				// Write to sd
				try {
					app.saveNoticias(news, new File(MainActivity.this.getCacheDir(), "noticias.json"));
				} catch (final IOException e) {

					LOG.error("Error: {}", e);
				}
			}

		};

		// Ejecucion del metodo de busqueda de Noticias de GoogleNews.
		ns.searchGoogleNews(this.search_word, callback);
	}
	
}