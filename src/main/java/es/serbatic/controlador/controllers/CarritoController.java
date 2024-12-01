package es.serbatic.controlador.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.serbatic.controlador.services.CarritoService;
import es.serbatic.controlador.services.ProductoService;
import es.serbatic.controlador.services.UsuarioService;
import es.serbatic.modelo.VO.CarritoVO;
import jakarta.servlet.http.HttpSession;

@Controller
public class CarritoController {
	
	@Autowired
	private CarritoService cs;
	@Autowired
	private UsuarioService us;
	@Autowired
	private ProductoService ps;

	@GetMapping("/verCarrito")
	public String verCarrito(Model model, HttpSession sesion) {
		List<CarritoVO> listado = new ArrayList<CarritoVO>();
		Integer id = (Integer) sesion.getAttribute("idUsuario");
		
		if (id != null) {	
			int idUsuario = id;
			listado = cs.getListado(idUsuario);
			model.addAttribute("carrito", listado);
			sesion.setAttribute("carrito", listado);
			model.addAttribute("total", cs.totalPrecioCarritos(listado));
			sesion.setAttribute("totalPrecioCarrito", cs.totalPrecioCarritos(listado));
			if(sesion.getAttribute("totalCarritos") != null) {
				int totalCarrito = cs.totalNumeroDeCarritos(id);
				sesion.setAttribute("totalCarritos", totalCarrito);
			}
		}
		
		return "carrito";
	}
	
	@PostMapping("/añadirCarrito")
	public String añadirCarrito(@ModelAttribute CarritoVO c, @RequestParam("cantidad") String cantidad, HttpSession sesion, Model model) {
		int cantidadUsuario = Integer.parseInt(cantidad);
		String pagina = "redirect:/verCarrito";
		Integer id = (Integer) sesion.getAttribute("idUsuario");



		if (id != null) {
			if(ps.comprobarStock(c.getProducto_id(), cantidadUsuario)) {
				c.setCantidad(cantidadUsuario);
			    c.setUsuario_id(id);
				if(cs.existeCarrito(c)) {
					cs.actualizarCantidadCarrito(c);
				}else {
				    cs.createCarrito(c);
				}
			}else {
				model.addAttribute("errorStock", "Error en stock");
				pagina = "forward:/verProducto?id=" + c.getProducto_id();
			}

		} else {
		    pagina = "redirect:/verLogin";
		}

		return pagina;
	}
	
	@PostMapping("/borrarCarrito")
	public String borrarCarrito(@RequestParam String id) {
		int idCarrito = Integer.parseInt(id);
		
		cs.borrarCarrito(idCarrito);
		
		return "redirect:/verCarrito";
	}
}
