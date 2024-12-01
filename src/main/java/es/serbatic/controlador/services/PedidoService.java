package es.serbatic.controlador.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.serbatic.modelo.DAO.PedidoDAO;
import es.serbatic.modelo.VO.CarritoVO;
import es.serbatic.modelo.VO.DetallePedidoVO;
import es.serbatic.modelo.VO.PedidoVO;
import es.serbatic.modelo.hilos.CambioEstadoDetalle;

@Service
public class PedidoService {

	@Autowired
	PedidoDAO pd;
	
	  public void ejecutarCambioEstado(int id) {
		  CambioEstadoDetalle cambioEstado = new CambioEstadoDetalle(id, pd);

	        // Crear y ejecutar el hilo
	        Thread hilo = new Thread(cambioEstado);
	        hilo.start();
	  }
	
	public String generarNumeroFactura() {
		// Obtén la fecha actual en formato "yyyyMMdd"
	    LocalDate fecha = LocalDate.now();
	    String fechaFormateada = fecha.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	    
	    // Crea un UUID y toma los primeros 9 caracteres en mayúsculas
	    String uuidPart = UUID.randomUUID().toString().substring(0, 9).toUpperCase();
	    
	    // Añade un contador o un número único (por ejemplo, puedes generar un número aleatorio o un contador)
	    int numeroFactura = (int)(Math.random() * 1000); // Puedes usar un contador o algo que garantice unicidad
	    
	    // Formatea el número a 3 dígitos con ceros a la izquierda
	    return fechaFormateada + "-" + String.format("%03d", numeroFactura) + "-" + uuidPart;
    }
	
	public void crearPedido(PedidoVO p) {
		pd.save(p);
	}
	
	public PedidoVO obtenerPedido(int idUsuario, String numFactura) {
		PedidoVO p = new PedidoVO();
		
		p = pd.findByUsuarioIdAndNumFactura(idUsuario, numFactura);
		
		return p;
	}
	
	public List<PedidoVO> obtenerListaPedidos(int id){
		return pd.findByUsuarioId(id);
	}
	
	public boolean cancelarPedido(int id) {
		PedidoVO p = pd.findById(id).orElse(null);
		
		if (!p.getEstado().equals("Enviado") && !p.getEstado().equals("Pendiente de cancelacion") && !p.getEstado().equals("Cancelado")) {
			p.setEstado("Pendiente de cancelacion");
			pd.save(p);
			return true;
		}
		
		return false;
	}
}
