package frsf.isi.died.guia08.problema01.modelo;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;

public class TareaTest {
Tarea tarea;
Empleado empleado1;
Empleado empleado2;
	@Test
	public void asignarEmpleadoTest() {
		tarea=new Tarea();
		empleado1=new Empleado(0, null, "CONTRATADO", 0);
		empleado2=new Empleado(0, null, "CONTRATADO", 0);
		tarea.asignarEmpleado(empleado1);
		tarea.setFechaFin(LocalDateTime.of(2020, 5, 13, 12, 50));
		tarea.asignarEmpleado(empleado2);
		assertTrue(tarea.getEmpleadoAsignado()==empleado1);
	}

}
