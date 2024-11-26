package es.serbatic.controlador.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.serbatic.modelo.DAO.DetalleDAO;
import es.serbatic.modelo.DAO.PedidoDAO;
import es.serbatic.modelo.DAO.ProductoDAO;
import es.serbatic.modelo.VO.CarritoVO;
import es.serbatic.modelo.VO.DetallePedidoVO;
import es.serbatic.modelo.VO.ProductoVO;
import es.serbatic.modelo.hilos.CambioEstadoDetalle;

@Service
public class DetalleService {

	@Autowired
	DetalleDAO dd;
	@Autowired
	PedidoDAO pd;
	@Autowired
	ProductoDAO prd;
	 
	public void crearDetalle(List<CarritoVO> listadoCarrito, int pedidoId, double total) {
		ProductoVO p = new ProductoVO();

		for (CarritoVO carrito : listadoCarrito) {
			DetallePedidoVO d = new DetallePedidoVO();
			d.setProducto_id(carrito.getProducto_id());
			
			p = prd.findById(d.getProducto_id()).orElse(null);
			
			d.setPedido_id(pedidoId);
			d.setUnidades(carrito.getCantidad());
			d.setPreciounidad(carrito.getPrecio());
			d.setImpuesto(p.getImpuesto());
			d.setTotal(total);
			
			dd.save(d);
			
			
		}
	}
	
	public List<DetallePedidoVO> getDetallesPorPedido(int id){
		List<DetallePedidoVO> lista = dd.findByPedidoId(id);
		
		return lista;
	}
}
