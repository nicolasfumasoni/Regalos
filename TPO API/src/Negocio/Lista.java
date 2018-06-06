package Negocio;

import java.util.Date;
import java.util.List;

public class Lista {
	public String nombreAgasajado;
	public Date fecha;//Para qu� sirve?
	public List<UsuarioDeLista> participantes;
	public int montoPorParticipante;
	public int montoRecaudado;
	public Date fechaInicio;
	public Date fechaFin;
	public boolean estado;
	public String mail;
	public UsuarioDeLista administrador;
	
	/*Constructor de la lista, deberia decirse quien es el administrador, que fecha
	 * de inicio y fin tiene, monto que tiene que poner cada uno, nombre del agasajado
	 * */
	public Lista(UsuarioDeLista admin, Date FechaInicio, Date FechaFin, String agasajado){
		this.administrador = admin;
		this.fechaInicio = FechaInicio;
		this.fechaFin = FechaFin;
		this.nombreAgasajado = agasajado;
		this.estado = true;//La lista est� activa
		this.mail = agasajado + "@regalo.com";//Hay que crear un mail para la lista
	}
	
	public void agregarParticipante(UsuarioDeLista nuevo){
		this.participantes.add(nuevo);
	}
	
	//El echado que se pase debe estar en la lista
	public void quitarParticipante(UsuarioDeLista echado){
		try {
			this.participantes.remove(echado);
		} catch (Exception e) {
			System.out.println(e.getMessage());//Seria mejor poner un messagebox.
		}
		
	}
	
	public void cerrarLista(){
		this.estado = false;
	}
}