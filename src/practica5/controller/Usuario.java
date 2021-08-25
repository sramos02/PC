package practica5.controller;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Usuario implements Serializable {
	String id;
	String ip;
	List<Fichero> data;
	
	public Usuario(String id, String ip, List<Fichero> data) {
		this.id = id;
		this.ip = ip;
		this.data = data;
	}
	
	public String getId() {
		return id;
	}

	public String getIp() {
		return ip;
	}
	
	public String getFiles() {
		String ret = "";
		if(data.size() > 0) {
			for(int i = 0; i < data.size() - 1; i++)
				ret += data.get(i).getName() + " ";
			ret += data.get(data.size()-1).getName() + " ";
		}
		return ret;
	}

	public List<Fichero> getData() {
		return data;
	}
	
	public void addFile(Fichero fich) {
		data.add(fich);
	}
	
	
}
