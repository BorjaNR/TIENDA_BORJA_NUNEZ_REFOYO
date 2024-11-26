package es.serbatic.controlador.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.serbatic.controlador.services.CarritoService;
import es.serbatic.controlador.services.DetalleService;
import es.serbatic.controlador.services.DireccionService;
import es.serbatic.controlador.services.PedidoService;
import es.serbatic.controlador.services.ProductoService;
import es.serbatic.controlador.services.UsuarioService;
import es.serbatic.modelo.DAO.DetalleDAO;
import es.serbatic.modelo.VO.CarritoVO;
import es.serbatic.modelo.VO.DetallePedidoVO;
import es.serbatic.modelo.VO.PedidoVO;
import es.serbatic.modelo.VO.UsuarioVO;
import es.serbatic.modelo.hilos.CambioEstadoDetalle;
import jakarta.servlet.http.HttpSession;

@Controller
public class CompraController {
	
	@Autowired
	DireccionService ds;
	@Autowired
	UsuarioService us;
	@Autowired
	PedidoService ps;
	@Autowired
	ProductoService prs;
	@Autowired
	DetalleService des;
	@Autowired
	CarritoService cs;
	
	@GetMapping("/verPago")
    public String pago(HttpSession sesion, Model model) {
		String pagina = "pago";
		UsuarioVO u = (UsuarioVO) sesion.getAttribute("usuario");
		List<CarritoVO> listado = (List<CarritoVO>) sesion.getAttribute("carrito");

		if (u == null) {
			pagina = "redirect:/verLogin";
		}else {
			if(!listado.isEmpty()){
				model.addAttribute("direcciones", ds.getListadoPorUsuarioId(u.getId()));
				model.addAttribute("usuario", u);
			}else {
				pagina = "redirect:/";
			}
		}
        
		return pagina;
	}
	
	@PostMapping("/finalizarCompra")
	public String finalizarCompra(@RequestParam("direccion") String direccion, @RequestParam("metodopago") String metodopago, 
			HttpSession sesion) {
		double total = (Double) sesion.getAttribute("totalPrecioCarrito");
		Integer idUsuario = (Integer) sesion.getAttribute("idUsuario"); 
		int idDireccion = Integer.parseInt(direccion);
		List<CarritoVO> listado = (List<CarritoVO>) sesion.getAttribute("carrito");
		Date fecha = new Date();
		PedidoVO p = new PedidoVO();
		
		p.setUsuario_id(idUsuario);
		p.setDireccion_id(idDireccion);
		p.setFecha(fecha);
		p.setMetodopago(metodopago);
		p.setNumfactura(ps.generarNumeroFactura());
		p.setTotal(total);
		p.setEstado("Pendiente de envio");
		
		DetallePedidoVO d = new DetallePedidoVO();
		
		ps.crearPedido(p);		
		p = ps.obtenerPedido(p.getUsuario_id(), p.getNumfactura());
		prs.restarStock(listado);
		
		des.crearDetalle(listado, p.getId(), total);
		
		List<DetallePedidoVO> listadoDetalles = des.getDetallesPorPedido(p.getId());
		
		ps.ejecutarCambioEstado(p.getId());
		
		for (CarritoVO carrito : listado) {
			cs.borrarCarrito(carrito.getId());
		}
		sesion.setAttribute("carrito", null);
		
		return "compraFinalizada";
	}
}
