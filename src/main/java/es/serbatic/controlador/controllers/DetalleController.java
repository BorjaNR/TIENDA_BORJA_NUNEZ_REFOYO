package es.serbatic.controlador.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.serbatic.controlador.services.DetalleService;
import es.serbatic.controlador.services.PedidoService;
import es.serbatic.controlador.services.ProductoService;
import es.serbatic.modelo.VO.DetallePedidoVO;
import es.serbatic.modelo.VO.ProductoVO;

@Controller
public class DetalleController {

	@Autowired
	private ProductoService ps;
	@Autowired 
	private DetalleService ds;
	@Autowired
	private PedidoService pes;
	
	@PostMapping("/verDetalle")
	public String verDetalle(@RequestParam("pedidoId") String id, Model model) {
		int idPedido = Integer.parseInt(id);
		double total = 0;
		List<DetallePedidoVO> listado = ds.getDetallesPorPedido(idPedido);
		List<String> listadoProductos = new ArrayList<String>();
		
		for (DetallePedidoVO detalle : listado) {
			ProductoVO p = new ProductoVO();
			
			total = detalle.getTotal();
			p = ps.obtenerProducto(detalle.getProducto_id());
			listadoProductos.add(p.getNombre());
		}
		
		model.addAttribute("detalles", listado);
		model.addAttribute("productos", listadoProductos);
		model.addAttribute("idPedido", idPedido);
		model.addAttribute("total", total);
		
		return "detalle";
	}
}
