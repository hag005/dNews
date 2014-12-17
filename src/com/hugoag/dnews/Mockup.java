package com.hugoag.dnews;

import java.util.List;

import com.hugoag.dnews.model.Noticia;
import com.google.common.collect.Lists;

public class Mockup {

	/**
	 * Singleton
	 */
	public static final Mockup INSTANCE = new Mockup();

	/**
	 * @return the {@link List} of {@link Noticia}.
	 */
	public List<Noticia> buildNoticias() {

		final List<Noticia> noticias = Lists.newArrayList();

		noticias.add(new Noticia()
			.setId(2)
			.setLikes(13)
			.setAutor("Diego P. Urrutia <durrutia@ucn.cl>")
			.setTitulo("Sólo hasta el miércoles hay plazo para postular a rendir la PSU gratis")
			.setResumen("A las 13:00 del 8 de octubre finalizará el proceso para que los escolares "
					+ "antofagastinos reciban la la beca que les permite no cancelar los $27.520 que "
					+ "cuesta la prueba. En la región ya hay más de cinco mil inscritos.") //
			.setImagen("https://33.media.tumblr.com/aa4e5718981cc81cf89a6fbd9b79b659/tumblr_nfj3qbOZm91rb8pu7o1_1280.jpg")
			.setFecha(1402422960L * 1000));

		noticias.add(new Noticia()
			.setId(1)
			.setLikes(4)
			.setAutor("Prensa UCN <prensa@ucn.cl>")
			.setTitulo("Esterilizarán a cuatro mil perros y gatos en Antofagasta gracias a clínica móvil")
			.setResumen("El vehículo recorrerá la ciudad efectuando estos procedimientos que contribuirían "
					+ "en detener la sobrepoblación de canes y felinos en un plazo de cinco años. A contar de "
					+ "hoy y hasta el sábado 25 de octubre la clínica de esterilización móvil se encontrará "
					+ "en la explanada de la Municipalidad.") //
			.setImagen("https://38.media.tumblr.com/5d28f715de7340c261cc2ca3a202f6b5/tumblr_nbyd420pgH1qimrito3_1280.png")
			.setFecha(1402422480L * 1000));

		noticias.add(new Noticia()
			.setId(0)
			.setLikes(12)
			.setAutor("Prensa UCN <prensa@ucn.cl>")
			.setTitulo("Emprendedores antofagastinos podrán potenciar sus negocios en el extranjero")
			.setResumen("Los beneficiarios corresponden al programa de entrenamiento 'Experiencia Global "
					+ "Antofagasta', el cual realizá varias alianzas con organismos internacionales, "
					+ "entre ellos Estados Unidos y Colombia.") //
			.setImagen("https://33.media.tumblr.com/d4cb477d75c1e1586f69df11132305e9/tumblr_ncdoa6DMb31t2c7h9o5_500.jpg")
			.setFecha(1402421400L * 1000));

		return noticias;

	}

}
