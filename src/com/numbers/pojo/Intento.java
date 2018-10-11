package com.numbers.pojo;

import java.util.ArrayList;
import java.util.Date;

public class Intento {
	private ArrayList<String> intentos;
	private Date tiempo;
	private int intentoActual;
	private String mensaje;
	
	
	public Intento(ArrayList<String> intentos, Date tiempo, int intentoActual, String mensaje) {
		super();
		this.intentos = intentos;
		this.tiempo = tiempo;
		this.intentoActual = intentoActual;
		this.mensaje = mensaje;
	}
	
	public ArrayList<String> getIntentos() {
		return intentos;
	}
	public void setIntentos(ArrayList<String> intentos) {
		this.intentos = intentos;
	}
	public Date getTiempo() {
		return tiempo;
	}
	public void setTiempo(Date tiempo) {
		this.tiempo = tiempo;
	}
	public int getIntentoActual() {
		return intentoActual;
	}
	public void setIntentoActual(int intentoActual) {
		this.intentoActual = intentoActual;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public String getTotalTime(Date fecha) {
		Date now = new Date();
		int time = (int) ((now.getTime() - fecha.getTime())/1000);
		int acu;
	    String tiempo = "";
	    if(time > 86400){
	    	acu = (int) (Math.floor(time/86400));
	    	tiempo += acu + " días, ";
	    	time -= (acu*86400);
	    }
	    
	    if(time > 3600){
	    	acu = (int) (Math.floor(time/3600));
	    	tiempo += acu + " horas, ";
	    	time -= (acu*86400);
	    }
	    
	    if(time > 60) {
	    	acu = (int) (Math.floor(time/60));
	    	tiempo += acu + " minutos, ";
	    	time -= (acu*60);
	    }
	    
	    if(time > 0){
	    	tiempo += time + " segundos.";
	    	time -= time;
	    }
	    return tiempo;
	}

}
