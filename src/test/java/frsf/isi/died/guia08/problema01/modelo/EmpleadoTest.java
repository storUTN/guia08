package frsf.isi.died.guia08.problema01.modelo;

import static org.junit.Assert.*;

import java.time.LocalDateTime;


import org.junit.Test;

public class EmpleadoTest {
	
	Empleado efectivo;
	Empleado contratado;
	Tarea tarea1;
	Tarea tarea2;
	
	// IMPORTANTE
	// ESTA CLASE ESTA ANOTADA COMO @IGNORE por lo que no ejecutará ningun test
	// hasta que no borre esa anotación.

	@Test
	public void testSalario() {
		tarea1= new Tarea();
		tarea1.setId(1);
		tarea1.setDuracionEstimada(10);
		tarea1.setFacturada(false);
		contratado= new Empleado(1, null,"CONTRATADO", 10);
		efectivo= new Empleado(2, null, "EFECTIVO", 10);
		efectivo.asignarTarea(tarea1);
		efectivo.comenzar(1);
		efectivo.finalizar(1);
		assertTrue(efectivo.salario()==120.0);
		assertTrue(tarea1.getFacturada());
		
	}

	@Test
	public void testCostoTarea() {
		tarea1= new Tarea();
		tarea1.setDuracionEstimada(10);
		contratado= new Empleado(1, null,"CONTRATADO", 10);
		efectivo= new Empleado(2, null, "EFECTIVO", 10);
		tarea1.setFechaInicio(LocalDateTime.of(2020, 5, 12, 12, 50));
		tarea1.setFechaFin(LocalDateTime.of(2020, 5, 13, 12, 50));
		assertTrue(efectivo.costoTarea(tarea1)==120.0);
		assertTrue(contratado.costoTarea(tarea1)==130.0);
		tarea1.setFechaFin(LocalDateTime.of(2020, 5, 20, 12, 50));
		assertTrue(contratado.costoTarea(tarea1)==75.0);
	}

	@Test
	public void testAsignarTarea() {
		contratado= new Empleado(1, null,"CONTRATADO", 10);
		efectivo= new Empleado(2, null, "EFECTIVO", 10);
		tarea1= new Tarea();
		tarea1.setDuracionEstimada(20);
		tarea1.setFacturada(false);
		tarea1.setId(1);
		tarea2= new Tarea();
		tarea2.setDuracionEstimada(10);
		tarea2.setFacturada(false);
		tarea2.setId(2);
		Tarea tarea3=new Tarea();
		Tarea tarea4=new Tarea();
		Tarea tarea5=new Tarea();
		Tarea tarea6=new Tarea();
		Tarea tarea7=new Tarea();
		efectivo.asignarTarea(tarea1);
		assertTrue(efectivo.getTareas().contains(tarea1));
		contratado.asignarTarea(tarea1);
		assertFalse(contratado.getTareas().contains(tarea1));
		efectivo.asignarTarea(tarea2);
		assertFalse(efectivo.getTareas().contains(tarea2));
		contratado.asignarTarea(tarea2);
		contratado.asignarTarea(tarea3);
		contratado.asignarTarea(tarea4);
		contratado.asignarTarea(tarea5);
		contratado.asignarTarea(tarea6);
		contratado.asignarTarea(tarea7);
		assertFalse(contratado.getTareas().contains(tarea7));
	}

	@Test
	public void testComenzarInteger() {
		efectivo= new Empleado(2, null, "EFECTIVO", 10);
		tarea1= new Tarea();
		tarea1.setDuracionEstimada(10);
		tarea1.setFacturada(false);
		tarea1.setId(1);
		efectivo.asignarTarea(tarea1);
		efectivo.comenzar(tarea1.getId());
		assertTrue(tarea1.getFechaInicio()!=null);
	}

	@Test
	public void testFinalizarInteger() {
		efectivo= new Empleado(2, null, "EFECTIVO", 10);
		tarea1= new Tarea();
		tarea1.setDuracionEstimada(10);
		tarea1.setFacturada(false);
		tarea1.setId(1);
		efectivo.asignarTarea(tarea1);
		efectivo.comenzar(tarea1.getId());
		efectivo.finalizar(tarea1.getId());
		assertTrue(tarea1.getFechaFin()!=null);
		
	}

	@Test
	public void testComenzarIntegerString() {
		contratado= new Empleado(1, null,"CONTRATADO", 10);
		efectivo= new Empleado(2, null, "EFECTIVO", 10);
		tarea1= new Tarea();
		tarea1.setDuracionEstimada(10);
		tarea1.setFacturada(false);
		tarea1.setId(1);
		tarea2= new Tarea();
		tarea2.setDuracionEstimada(10);
		tarea2.setFacturada(false);
		tarea2.setId(2);
		efectivo.asignarTarea(tarea1);
		//DD-MM-YYYY HH:mm
		efectivo.comenzar(tarea1.getId(),"12-05-2020 12:15");
		assertTrue(tarea1.getFechaInicio()!=null&&tarea1.getFechaInicio().getHour()==12);
		
		
		
	}

	@Test
	public void testFinalizarIntegerString() {
		contratado= new Empleado(1, null,"CONTRATADO", 10);
		efectivo= new Empleado(2, null, "EFECTIVO", 10);
		tarea1= new Tarea();
		tarea1.setDuracionEstimada(10);
		tarea1.setFacturada(false);
		tarea1.setId(1);
		tarea2= new Tarea();
		tarea2.setDuracionEstimada(10);
		tarea2.setFacturada(false);
		tarea2.setId(2);
		efectivo.asignarTarea(tarea1);
		//DD-MM-YYYY HH:mm
		efectivo.comenzar(tarea1.getId(),"12-05-2020 12:15");
		efectivo.finalizar(tarea1.getId(),"27-05-2020 12:30");
		assertTrue(tarea1.getFechaInicio()!=null&&tarea1.getFechaFin().getMinute()==30);
		
	}

}
