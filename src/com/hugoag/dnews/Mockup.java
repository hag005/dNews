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
			.setTitulo("S�lo hasta el mi�rcoles hay plazo para postular a rendir la PSU gratis")
			.setResumen("A las 13:00 del 8 de octubre finalizar� el proceso para que los escolares "
					+ "antofagastinos reciban la la beca que les permite no cancelar los $27.520 que "
					+ "cuesta la prueba. En la regi�n ya hay m�s de cinco mil inscritos.") //
			.setImagen("https://33.media.tumblr.com/aa4e5718981cc81cf89a6fbd9b79b659/tumblr_nfj3qbOZm91rb8pu7o1_1280.jpg")
			.setFecha(1402422960L * 1000));

		noticias.add(new Noticia()
			.setId(1)
			.setLikes(4)
			.setAutor("Prensa UCN <prensa@ucn.cl>")
			.setTitulo("Esterilizar�n a cuatro mil perros y gatos en Antofagasta gracias a cl�nica m�vil")
			.setResumen("El veh�culo recorrer� la ciudad efectuando estos procedimientos que contribuir�an "
					+ "en detener la sobrepoblaci�n de canes y felinos en un plazo de cinco a�os. A contar de "
					+ "hoy y hasta el s�bado 25 de octubre la cl�nica de esterilizaci�n m�vil se encontrar� "
					+ "en la explanada de la Municipalidad.") //
			.setImagen("https://38.media.tumblr.com/5d28f715de7340c261cc2ca3a202f6b5/tumblr_nbyd420pgH1qimrito3_1280.png")
			.setFecha(1402422480L * 1000));

		noticias.add(new Noticia()
			.setId(0)
			.setLikes(12)
			.setAutor("Prensa UCN <prensa@ucn.cl>")
			.setTitulo("Emprendedores antofagastinos podr�n potenciar sus negocios en el extranjero")
			.setResumen("Los beneficiarios corresponden al programa de entrenamiento 'Experiencia Global "
					+ "Antofagasta', el cual realiz� varias alianzas con organismos internacionales, "
					+ "entre ellos Estados Unidos y Colombia.") //
			.setImagen("https://33.media.tumblr.com/d4cb477d75c1e1586f69df11132305e9/tumblr_ncdoa6DMb31t2c7h9o5_500.jpg")
			.setFecha(1402421400L * 1000));

		return noticias;

	}

}
