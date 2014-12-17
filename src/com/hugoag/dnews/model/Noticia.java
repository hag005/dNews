package com.hugoag.dnews.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import android.annotation.SuppressLint;

import com.google.common.collect.Lists;
import com.ocpsoft.pretty.time.PrettyTime;

/*
 * Clase que representa una noticia en el sistema.
 */
public final class Noticia {

	/**
	 * Identificador unico de la noticia.
	 */
	private long id;

	/**
	 * Titulo de la noticia
	 */
	private String titulo;

	/**
	 * Resumen de la noticia
	 */
	private String resumen;

	/**
	 * Contenido de la noticia
	 */
	private String contenido;
	
	/**
	 * Link a la noticia
	 */
	private String link;

	/**
	 * Listado de tags dados por el usuario.
	 */
	private final List<String> tag = Lists.newArrayList();

	/**
	 * Fecha de la noticia, ha sido representada como ticks o milisegundos que han trasncurrido desde el 1 de enero de 1970.
	 */
	private long fecha;

	/**
	 * URL de la imagen de 64x64 que representa la noticia.
	 */
	private String imagen;

	/**
	 * Nombre del autor, deberia incluir el correo electronico por ejemplo: "Diego Urrutia Astorga <durrutia@ucn.cl>".
	 */
	private String autor;

	/**
	 * Numero de likes que ha recibido la noticia.
	 */
	private int likes;

	/**
	 * Constructor vacio para utilizar las bondades de Gson.
	 */
	public Noticia() {

	}

	/**
	 * @return the id
	 */
	public final long getId() {
		return this.id;
	}

	/**
	 * @param id
	 * the id to set
	 */
	public final Noticia setId(final long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the titulo
	 */
	public final String getTitulo() {
		return this.titulo;
	}

	/**
	 * @param titulo
	 * the titulo to set
	 */
	public final Noticia setTitulo(final String titulo) {
		this.titulo = titulo;
		return this;
	}

	/**
	 * @return the resumen
	 */
	public final String getResumen() {
		return this.resumen;
	}

	/**
	 * @param resumen
	 * the resumen to set
	 */
	public final Noticia setResumen(final String resumen) {
		this.resumen = resumen;
		return this;
	}

	/**
	 * @return the contenido
	 */
	public final String getContenido() {
		return this.contenido;
	}

	/**
	 * @param contenido
	 * the contenido to set
	 */
	public final Noticia setContenido(final String contenido) {
		this.contenido = contenido;
		return this;
	}

	/**
	 * @return the fecha
	 */
	public final long getFecha() {
		return this.fecha;
	}

	/**
	 * @param fecha
	 * the fecha to set
	 */
	public final Noticia setFecha(final long fecha) {
		this.fecha = fecha;
		return this;
	}
	
	/**
	 * @param fecha
	 * the fecha to set
	 */
	@SuppressLint("SimpleDateFormat")
	public final Noticia setFecha(final String fecha) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("EE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
			this.fecha = formatter.parse(fecha).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	/**
	 * @return the imagen
	 */
	public final String getImagen() {
		return this.imagen;
	}

	/**
	 * @param imagen
	 * the imagen to set
	 */
	public final Noticia setImagen(final String imagen) {
		this.imagen = imagen;
		return this;
	}

	/**
	 * @return the autor
	 */
	public final String getAutor() {
		return this.autor;
	}

	/**
	 * @param autor
	 * the autor to set
	 */
	public final Noticia setAutor(final String autor) {
		this.autor = autor;
		return this;
	}

	/**
	 * @return the likes
	 */
	public final int getLikes() {
		return this.likes;
	}

	/**
	 * @param likes
	 * the likes to set
	 */
	public final Noticia setLikes(final int likes) {
		this.likes = likes;
		return this;
	}

	/**
	 * @return the tag
	 */
	public final List<String> getTag() {
		return this.tag;
	}
	
	/**
	 * @return the Time
	 */
	public String getTime() {
		PrettyTime p = new PrettyTime();
		return p.format(new Date(this.fecha));
	}
	
	@SuppressLint("SimpleDateFormat")
	public String getFormatFecha() {
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd 'de' MMMM 'del' yyyy 'a las' HH:mm:ss");
		return formatter.format(new Date(this.fecha));
	}
	
	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link
	 * the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @see java.lang.Object#toString()
	 * @see org.apache.commons.lang3.builder.ToStringBuilder#reflectionToString(Object)
	 */
	@Override
	public final String toString() {
		// Implementacion por medio de la libreria commons-lang.
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @see org.apache.commons.lang3.builder.EqualsBuilder#reflectionEquals(Object, Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		// Implementacion por medio de la libreria commons-lang.
		return EqualsBuilder.reflectionEquals(this, obj);
	}

}
