package com.hugoag.dnews.views;

import java.util.Collection;
import java.util.SortedMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.hugoag.dnews.App;
import com.hugoag.dnews.R;
import com.hugoag.dnews.model.Noticia;
import com.squareup.picasso.Picasso;

public class NoticiaAdapter extends BaseAdapter{

	static final Logger LOG = LoggerFactory.getLogger(NoticiaAdapter.class);
	private LayoutInflater mInflater;

	/**
	 * Noticias a mostrar
	 */
	private final SortedMap<Long, Noticia> noticias = Maps.newTreeMap(java.util.Collections.reverseOrder());


	public NoticiaAdapter(Context context) {
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * Metodo para agregar una coleccion de noticias al adaptador.
	 *
	 * @param noticias
	 */
	public void addAll(final Collection<Noticia> noticias, boolean mensajes) {

		// Verificacion de nulidad
		Preconditions.checkNotNull(noticias, "Noticias a agregar es null");

		int counter = 0;
		final SortedMap<Long, Noticia> idNoticias = Maps.newTreeMap(java.util.Collections.reverseOrder());
		
		for (final Noticia n : this.noticias.values()) {
			idNoticias.put(n.getId(), n);
		}

		for (final Noticia n : noticias) {

			// Agrego la noticia como la tupla (id, noticia)
			// Si existiera ya una noticia con ese id no se ingresa.
			//Podria haber un problema si hay dos noticias diferentes con exactamente la misma fecha, solo se agregaria una de las dos.
			if (idNoticias.put(n.getId(), n) == null && this.noticias.put(n.getFecha(), n) == null) {
				counter++;
			}
		}

		LOG.info("Se han agregado {} noticias.", counter);

		// Notifico que se realizo algun cambio
		App app = App.getInstance(mInflater.getContext());
		if (counter != 0) {
			if (counter > 1)
				if (mensajes) Toast.makeText(app, "Se agregaron " + counter + " noticias nuevas", Toast.LENGTH_SHORT).show();
			else
				if (mensajes) Toast.makeText(app, "Se agrego 1 noticia nueva", Toast.LENGTH_SHORT).show();
			this.notifyDataSetChanged();
		} else {
			if (mensajes) Toast.makeText(app, "No hay noticias nuevas", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return this.noticias.size();
	}

	/**
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(final int position) {
		return (Long) this.noticias.keySet().toArray()[position];
	}

	/**
	 * Dada una posicion, obtiene la Noticia en esa posicion.
	 *
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Noticia getItem(final int position) {

		// Obtengo el identificador de la noticia que se encuentra en la position.
		final Long id = (Long) this.noticias.keySet().toArray()[position];

		// Retorno la noticia en esa posicion.
		return this.noticias.get(id);
	}

	/**
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {

		// Si es una fila no existente ..
		if (convertView == null) {

			LOG.debug("Inflating {}", position);

			// La inflo!
			convertView = this.mInflater.inflate(R.layout.row_noticia, parent, false);
		}

		// Obtengo la noticia en esa posicion
		final Noticia noticia = this.getItem(position);

		/*
		final ImageView image = ViewHolder.get(convertView, R.id.rn_iv_image);
		String url = this.getItem(position).getImagen();
		Picasso.with(convertView.getContext()).load(url).resize(64,64).into(image);
		*/

		final ImageView icon = ViewHolder.get(convertView, R.id.rn_iv_image);

		// Si la noticia tiene una imagen ..
		if (!StringUtils.isEmpty(noticia.getImagen())) {
			// .. uso Picasso to load image
			Picasso.with(convertView.getContext()) // Contexto para obtener las imagenes
			.load(noticia.getImagen()) // URL de la imagen
			.placeholder(R.drawable.loading_icon) // Imagen por defecto
			.noFade() // Desabilitar la animacion de carga
			.resize(100, 100) // Cambio el tamanio de la imagen
			.centerCrop() // Si la imagen no es cuadrada la redimensiono
			.into(icon); // Donde se deja la imagen

		} else {
			// .. no hay imagen, uso una por defecto.
			icon.setImageResource(R.drawable.icono_no_disponible);
		}

		final TextView tags = ViewHolder.get(convertView, R.id.rn_tv_tags);
		String lista_tags = "Sin Clasificar";
		boolean first = true;
		for( final String tag : noticia.getTag() ){
			if (first) {
				lista_tags = tag;
				first = false;
			} else
				lista_tags += "\n" + tag;
		}
		tags.setText(lista_tags);

		final TextView titulo = ViewHolder.get(convertView, R.id.rn_tv_titulo);
		titulo.setText(Html.fromHtml(noticia.getTitulo()));

		final TextView time = ViewHolder.get(convertView, R.id.rn_tv_time);
		time.setText(noticia.getTime());

		final TextView resumen = ViewHolder.get(convertView, R.id.rn_tv_resumen);
		resumen.setText(Html.fromHtml(noticia.getResumen()));

		final TextView autor = ViewHolder.get(convertView, R.id.rn_tv_autor);
		autor.setText("Publicado por " + noticia.getAutor());

		return convertView;

	}

}