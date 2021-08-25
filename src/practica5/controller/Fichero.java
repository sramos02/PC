package practica5.controller;

import java.util.Date;

import practica5.controller.CONSTANTES;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 * Student.java
 * This class represents a Student
 *
 * @author www.codejava.net
 */

@SuppressWarnings("serial")
public class  Fichero implements Serializable {

	private String name;

	public Fichero(String str) {
		this.name = str;
		createFile(str);
	}


	public Fichero(String str, boolean notNew) {
		this.name = CONSTANTES.RUTA + str;
	}
	
	private void createFile(String str) {
		try {
			FileWriter wr = new FileWriter(str);
			BufferedWriter b = new BufferedWriter(wr);
			b.write("Aquí va la información del fichero " + str.substring(32, str.length()));
			b.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Ha habido un problema creando el fichero");
		}
	}

	public String getName() {
		return this.name.substring(32,name.length());
	}

}
