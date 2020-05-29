package frsf.isi.died.guia08.problema01;

import static org.junit.Assert.*;

import org.junit.Test;

public class AppRRHHTest {

	@Test
	public void test() {
		AppRRHH app=new AppRRHH();
		app.agregarEmpleadoContratado(1, "Jose", 10.0);
		app.agregarEmpleadoEfectivo(2, "Luis", 10.0);
		app.asignarTarea(1, 1, null, 10);
		app.empezarTarea(1, 1);
		app.terminarTarea(1, 1);
		assertTrue(app.facturar()==130.0);
	}
}
