package es.serbatic.controlador.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.serbatic.modelo.DAO.ProductoDAO;
import es.serbatic.modelo.VO.CarritoVO;
import es.serbatic.modelo.VO.ProductoVO;

@Service
public class ProductoService {
	@Autowired
	private ProductoDAO dao;
	
	public List<ProductoVO> getListado(String filtro){
		List<ProductoVO> listado = new ArrayList<ProductoVO>();
		
		if(filtro.equals("") || filtro.equals("0")) {
			listado = dao.findAll();
		}else {
			listado = dao.findByCategoriaId(Integer.parseInt(filtro));
		}

		return listado;
	}
	
	public ProductoVO obtenerProducto(int id) {
		ProductoVO p = new ProductoVO();
		
		p = dao.findById(id).orElse(null);
		
		return p;
	}
	
	public void restarStock(List<CarritoVO> listadoCarrito) {
		for (CarritoVO carrito : listadoCarrito) {
			ProductoVO p = dao.findById(carrito.getProducto_id()).orElse(null);
			p.setStock(p.getStock() - carrito.getCantidad());
			dao.save(p);
		}
	}
}
