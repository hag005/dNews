package com.hugoag.dnews.activities;

import org.apache.commons.lang3.StringUtils;

import com.hugoag.dnews.R;
import com.hugoag.dnews.views.ViewHolder;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public final class NoticiaActivity extends Activity {
	
	private LayoutInflater mInflater;
	
	protected void onCreate(final Bundle savedInstanceState) {

		// Siempre inicializo
		super.onCreate(savedInstanceState);
		this.getActionBar().hide();
		mInflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.setNoticia();
	}
	
	@SuppressLint("InflateParams")
	public void setNoticia() {

		View convertView = this.mInflater.inflate(R.layout.expand_noticia, null, false);

		final ImageView icon = ViewHolder.get(convertView, R.id.rn_iv_image);
		final Bundle bundle = getIntent().getExtras();

		// Si la noticia tiene una imagen ..
		if (!StringUtils.isEmpty(bundle.getString("imagen"))) {
			// .. uso Picasso to load image
			Picasso.with(convertView.getContext()) // Contexto para obtener las imagenes
			.load(bundle.getString("imagen")) // URL de la imagen
			.placeholder(R.drawable.loading) // Imagen por defecto
			.noFade() // Desabilitar la animacion de carga
			.resize(600, 400) // Cambio el tamanio de la imagen
			//.centerCrop() // Si la imagen no es cuadrada la redimensiono
			.into(icon); // Donde se deja la imagen

		} else {
			// .. no hay imagen, uso una por defecto.
			icon.setImageResource(R.drawable.imagen_no_disponible);
		}

		final TextView tags = ViewHolder.get(convertView, R.id.rn_tv_tags);
		String lista_tags = "Sin Clasificar";
		if (bundle.getString("tags").compareTo("[]") != 0)
			lista_tags = bundle.getString("tags");
		tags.setText(lista_tags);
		
		final TextView likes = ViewHolder.get(convertView, R.id.rn_tv_likes);
		likes.setText(bundle.getInt("likes") + " like(s)");

		final TextView titulo = ViewHolder.get(convertView, R.id.rn_tv_titulo);
		titulo.setText(Html.fromHtml(bundle.getString("titulo")));

		final TextView time = ViewHolder.get(convertView, R.id.rn_tv_time);
		time.setText(bundle.getString("fecha"));

		final TextView resumen = ViewHolder.get(convertView, R.id.rn_tv_resumen);
		resumen.setText(Html.fromHtml(bundle.getString("resumen")));
		
		final TextView contenido = ViewHolder.get(convertView, R.id.rn_tv_contenido);
		contenido.setText(Html.fromHtml(bundle.getString("contenido")));

		final TextView autor = ViewHolder.get(convertView, R.id.rn_tv_autor);
		autor.setText("Publicado por " + bundle.getString("autor"));
		
		final TextView link = ViewHolder.get(convertView, R.id.rn_tv_link);
		link.setText(bundle.getString("link"));
		
		this.setContentView(convertView);
	}
	
}
