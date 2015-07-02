package utiles;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.ejb.EJB;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import datas.DataEleccion;
import negocio.interfaces.IEleccionHandler;

@WebListener
@TransactionManagement(TransactionManagementType.CONTAINER)
public class Test extends TimerTask implements ServletContextListener {
	
	private Timer timer;
	@EJB
	private IEleccionHandler eleccionHdlr;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		timer.cancel();

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		timer = new Timer();
		timer.schedule(this, 0, 2 * 60 * 1000);

	}

	@Override
	public void run() {
		
//		System.out.println("EJECUTANDO TEST");
//		List<DataEleccion> elecciones = eleccionHdlr.getEleccionesActuales();
//		for (DataEleccion dataEleccion : elecciones) {
//			System.out.println("Eleccion = "+dataEleccion.getNombre());			
//		}

	}

}
