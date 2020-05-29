package frsf.isi.died.guia08.problema01;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import frsf.isi.died.guia08.problema01.modelo.Empleado;
import frsf.isi.died.guia08.problema01.modelo.Tarea;

public class AppRRHH {

	private List<Empleado> empleados=new ArrayList<Empleado>();
	
	public void agregarEmpleadoContratado(Integer cuil,String nombre,Double costoHora) {
		// crear un empleado
		// agregarlo a la lista
		empleados.add(new Empleado(cuil, nombre, "CONTRATADO", costoHora));
	}
	
	public void agregarEmpleadoEfectivo(Integer cuil,String nombre,Double costoHora) {
		// crear un empleado
		// agregarlo a la lista
		empleados.add(new Empleado(cuil, nombre, "EFECTIVO", costoHora));
	}
	
	public void asignarTarea(Integer cuil,Integer idTarea,String descripcion,Integer duracionEstimada) {
		// crear un empleado
		// con el método buscarEmpleado() de esta clase
		// agregarlo a la lista	
		Tarea g= new Tarea();
		g.setId(idTarea);
		g.setDescripcion(descripcion);
		g.setDuracionEstimada(duracionEstimada);
		g.setFacturada(false);
		buscarEmpleado(t->t.getCuil()==cuil).ifPresent(t->t.asignarTarea(g));
	}
	
	public void empezarTarea(Integer cuil,Integer idTarea) {
		// busca el empleado por cuil en la lista de empleados
		// con el método buscarEmpleado() actual de esta clase
		// e invoca al método comenzar tarea
		buscarEmpleado(t->t.getCuil()==cuil).ifPresent(t->t.comenzar(idTarea));
		
		
		
	}
	
	public void terminarTarea(Integer cuil,Integer idTarea) {
		// crear un empleado
		// agregarlo a la lista
		buscarEmpleado(t->t.getCuil()==cuil).ifPresent(t->t.finalizar(idTarea));
	}

	public void cargarEmpleadosContratadosCSV(String nombreArchivo) {
		// leer datos del archivo
		// por cada fila invocar a agregarEmpleadoContratado
		try {
			FileReader lector= new FileReader(nombreArchivo);
			BufferedReader buffer= new BufferedReader(lector);
			String linea;
			while((linea=buffer.readLine()) != null) {
				String[] empleado= linea.split(";");
				agregarEmpleadoContratado(Integer.parseInt(empleado[0]),empleado[1],Double.parseDouble(empleado[2]));
			}
			lector.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("No se encontro el archivo: "+nombreArchivo);
		}
		catch(IOException e) {
			System.out.println("Ocurrio un error en la lectura del archivo");
		}
	}

	public void cargarEmpleadosEfectivosCSV(String nombreArchivo) {
		// leer datos del archivo
		// por cada fila invocar a agregarEmpleadoContratado
		try {
			FileReader lector= new FileReader(nombreArchivo);
			BufferedReader buffer= new BufferedReader(lector);
			String linea;
			while((linea=buffer.readLine()) != null) {
				String[] empleado= linea.split(";");
				agregarEmpleadoEfectivo(Integer.parseInt(empleado[0]),empleado[1],Double.parseDouble(empleado[2]));
			}
			lector.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("No se encontro el archivo: "+nombreArchivo);
		}
		catch(IOException e) {
			System.out.println("Ocurrio un error en la lectura del archivo");
		}
	}

	public void cargarTareasCSV(String nombreArchivo) {
		// leer datos del archivo
		// cada fila del archivo tendrá:
		// cuil del empleado asignado, numero de la tarea, descripcion y duración estimada en horas.
		try {
			FileReader lector= new FileReader(nombreArchivo);
			BufferedReader buffer= new BufferedReader(lector);
			String linea;
			while((linea=buffer.readLine()) != null) {
				String[] tarea= linea.split(";");
				asignarTarea(Integer.parseInt(tarea[3]),Integer.parseInt(tarea[0]),tarea[1],Integer.parseInt(tarea[2]));
			}
			lector.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("No se encontro el archivo: "+nombreArchivo);
		}
		catch(IOException e) {
			System.out.println("Ocurrio un error en la lectura del archivo");
		}
		
		
	}
	
	private void guardarTareasTerminadasCSV() {
		// guarda una lista con los datos de la tarea que fueron terminadas
		// y todavía no fueron facturadas
		// y el nombre y cuil del empleado que la finalizó en formato CSV 
		
		try {
		FileWriter escritor= new FileWriter("ListaTareasTerminadas.csv");
		BufferedWriter buffer= new BufferedWriter(escritor);
		List<Tarea> tareas= new ArrayList<Tarea>();
		for(Empleado e : empleados) {
			tareas.addAll(e.getTareas());
		}
		tareas.removeIf(t->t.getFechaFin()==null||t.getFacturada());
		for(Tarea t : tareas) {
			buffer.write(Integer.toString(t.getId())+";"+t.getDescripcion()+";"+Integer.toString(t.getDuracionEstimada())+";"+Integer.toString(t.getEmpleadoAsignado().getCuil())+"\n");
		}
		escritor.close();
		} catch (IOException e) {
			System.out.println("Ocurrio un error al intentar escribir las tareas"); 
		}

		
		
	}
	
	private Optional<Empleado> buscarEmpleado(Predicate<Empleado> p){
		return this.empleados.stream().filter(p).findFirst();
	}

	public Double facturar() {
		this.guardarTareasTerminadasCSV();
		return this.empleados.stream()				
				.mapToDouble(e -> e.salario())
				.sum();
	}
}
