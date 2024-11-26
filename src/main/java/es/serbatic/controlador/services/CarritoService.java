package es.serbatic.controlador.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.serbatic.modelo.DAO.CarritoDAO;
import es.serbatic.modelo.DAO.ProductoDAO;
import es.serbatic.modelo.VO.CarritoVO;
import es.serbatic.modelo.VO.ProductoVO;

@Service
public class CarritoService {

	@Autowired
	CarritoDAO cd;
	@Autowired
	ProductoDAO pd;
	
	public List<CarritoVO> getListado(int id){
		List<CarritoVO> listado = new ArrayList<CarritoVO>();
		
		listado = cd.findByUsuarioId(id);
		
		return listado;
	}
	
	public void createCarrito(CarritoVO c) {
		cd.save(c);
	}
	
	public boolean existeCarrito(CarritoVO c) {
		return cd.existsByUsuarioIdAndProductoId(c.getUsuario_id(), c.getProducto_id());
	}
	
	public void actualizarCantidadCarrito(CarritoVO c) {
		int cantidad = c.getCantidad();
		
		c = cd.findByUsuarioIdAndProductoId(c.getUsuario_id(), c.getProducto_id());
		c.setCantidad(c.getCantidad() + cantidad);
		cd.save(c);
	}
	
	public double totalPrecioCarritos(List<CarritoVO> listado) {
		double totalPorCantidad = 0;
		double total = 0;

		if(listado != null) {
			for (CarritoVO carrito : listado) {
				totalPorCantidad = carrito.getPrecio() * carrito.getCantidad();
				total += totalPorCantidad;
			}
		}
		
		return total;
	}
	
	public void borrarCarrito(int id) {
		cd.deleteById(id);
	}
	
	public boolean comprobarStrock(int productoId, int cantidad) {
		boolean esValido = false;
		ProductoVO p = new ProductoVO();
		p = pd.findById(productoId).orElse(null);
		
		if(p.getStock()>= cantidad) {
			esValido = true;
		}
		
		return esValido;
	}

	public int totalNumeroDeCarritos(int id) {
		int contadorCarrito = 0;
		
		List<CarritoVO> listado = cd.findByUsuarioId(id);
		
		for (CarritoVO carrito : listado) {
			contadorCarrito += carrito.getCantidad();
		}
		
		return contadorCarrito;
	}
}
