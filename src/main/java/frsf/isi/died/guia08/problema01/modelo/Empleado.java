package frsf.isi.died.guia08.problema01.modelo;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Empleado {

	public enum Tipo { CONTRATADO,EFECTIVO}
	
	private Integer cuil;
	private String nombre;
	private Tipo tipo;
	private Double costoHora;
	private List<Tarea> tareasAsignadas;
	
	private Function<Tarea, Double> calculoPagoPorTarea;		
	private Predicate<Tarea> puedeAsignarTarea;

	public Empleado(int cuil,String nombre,String tipo,double costoHora) {
		this.cuil=cuil;
		this.nombre=nombre;
		this.costoHora=costoHora;
		switch(tipo) {
		case "CONTRATADO":
			this.tipo=Tipo.CONTRATADO;
			this.puedeAsignarTarea=u->(u.getEmpleadoAsignado()==null)&&(tareasAsignadas.stream().filter(t->t.getFechaFin()==null).count()<5);
			this.calculoPagoPorTarea= (t)->((t.getFechaFin()
					.toEpochSecond(ZoneOffset.UTC)-
					t.getFechaInicio()
					.toEpochSecond(ZoneOffset.UTC))/86400*4<t
			.getDuracionEstimada())? t.getDuracionEstimada()*costoHora*1.3 : ((t.getFechaFin().toEpochSecond(ZoneOffset.UTC)-t.getFechaInicio().toEpochSecond(ZoneOffset.UTC))/86400*4>t
			.getDuracionEstimada())? t.getDuracionEstimada()*costoHora*0.75 : t.getDuracionEstimada()*costoHora;
			break;
		case "EFECTIVO":
			this.tipo=Tipo.EFECTIVO;
			this.puedeAsignarTarea=u->u.getEmpleadoAsignado()==null&&(tareasAsignadas.stream().filter(t->t.getFechaFin()==null).mapToInt((t)->t.getDuracionEstimada())).sum()<15;
			this.calculoPagoPorTarea= (t)->((t.getFechaFin()
					.toEpochSecond(ZoneOffset.UTC)-
					t.getFechaInicio()
					.toEpochSecond(ZoneOffset.UTC))/86400*4<t
			.getDuracionEstimada())? t.getDuracionEstimada()*costoHora*1.2 :
				t.getDuracionEstimada()*costoHora;
			break;
		}
		
		this.tareasAsignadas=new ArrayList<Tarea>();
		
	}
	
	public Double salario() {
		// cargar todas las tareas no facturadas
		// calcular el costo
		// marcarlas como facturadas.
		double cobro;
		switch(tipo) {
		case CONTRATADO:
		cobro= tareasAsignadas.stream()
				.filter(t->t.getFacturada()!=true)
				.mapToDouble(g->calculoPagoPorTarea.apply(g))
				.sum();
				break;
		case EFECTIVO:
		cobro= tareasAsignadas.stream()
				.filter(t->t.getFacturada()!=true)
				.mapToDouble(g->calculoPagoPorTarea.apply(g))
				.sum();
				break;
		default:
			return 0.0;
		}
		tareasAsignadas.forEach(t->t.setFacturada(true));
		return cobro;
	}
	
	/**
	 * Si la tarea ya fue terminada nos indica cuaal es el monto según el algoritmo de calculoPagoPorTarea
	 * Si la tarea no fue terminada simplemente calcula el costo en base a lo estimado.
	 * @param t
	 * @return
	 */
	public Double costoTarea(Tarea t) {
		
		return (t.getFechaFin()==null)? t.getDuracionEstimada()*costoHora : calculoPagoPorTarea.apply(t);
	}
		
	public Boolean asignarTarea(Tarea t) {
		try {
		if(puedeAsignarTarea.test(t)) {
			t.asignarEmpleado(this);
			tareasAsignadas.add(t);
			return true;
		}
		else {
			if(t.getEmpleadoAsignado() != null) throw new Exception();
			return false;
		}
		}
		catch(Exception e){
			System.out.println("Tarea invalida, por favor seleccione otra tarea para asignar");
			return false;
			
		}
	}
	
	public void comenzar(Integer idTarea) {
		// busca la tarea en la lista de tareas asignadas 
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de inicio la fecha y hora actual
		try {
			if(tareasAsignadas.stream().noneMatch((t->t.getId()==idTarea))) {
				throw new Exception("Tarea no encontrada");
			}
			tareasAsignadas.stream().filter(t->t.getId()==idTarea).forEach(t->t.setFechaInicio(LocalDateTime.now()));
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void finalizar(Integer idTarea) {
		// busca la tarea en la lista de tareas asignadas 
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de finalizacion la fecha y hora actual
		try {
			if(tareasAsignadas.stream().noneMatch((t->t.getId()==idTarea))) {
				throw new Exception("Tarea no encontrada");
			}
			tareasAsignadas.stream().filter(t->t.getId()==idTarea).forEach(t->t.setFechaFin(LocalDateTime.now()));
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void comenzar(Integer idTarea,String fecha) {
		// busca la tarea en la lista de tareas asignadas 
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de finalizacion la fecha y hora actual
		try {
			if(tareasAsignadas.stream().noneMatch((t->t.getId()==idTarea))) {
				throw new Exception("Tarea no encontrada");
			}
			tareasAsignadas.stream().filter(t->t.getId()==idTarea).forEach(t->t.setFechaInicio(LocalDateTime.parse(fecha,DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm"))));
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void finalizar(Integer idTarea,String fecha) {
		// busca la tarea en la lista de tareas asignadas 
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de finalizacion la fecha y hora actual
		try {
			if(tareasAsignadas.stream().noneMatch((t->t.getId()==idTarea))) {
				throw new Exception("Tarea no encontrada");
			}
			tareasAsignadas.stream().filter(t->t.getId()==idTarea).forEach(t->t.setFechaFin(LocalDateTime.parse(fecha,DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm"))));
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public int getCuil() {
		return cuil;
	}
	public List<Tarea> getTareas(){
		return tareasAsignadas;
	}
	public String getNombre() {
		return nombre;
	}
}
