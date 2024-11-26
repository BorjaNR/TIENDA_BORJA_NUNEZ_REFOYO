package es.serbatic.controlador.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.serbatic.controlador.services.DireccionService;
import es.serbatic.controlador.services.PedidoService;
import es.serbatic.modelo.VO.DireccionVO;
import es.serbatic.modelo.VO.PedidoVO;
import jakarta.servlet.http.HttpSession;

@Controller
public class PedidoController {

	@Autowired
	private DireccionService ds;
	
	@Autowired
	private PedidoService ps;
	
	@GetMapping("/verMisPedidos")
	public String verMisPedidos(Model model, HttpSession sesion) {
		 Integer id = (Integer) sesion.getAttribute("idUsuario");
		 List<PedidoVO> lista = ps.obtenerListaPedidos(id);
		 List<DireccionVO> listaDirecciones = new ArrayList<DireccionVO>();

		 // Asociamos las direcciones a los pedidos, usando el id de la dirección
		 for (PedidoVO pedido : lista) {
		     DireccionVO d = ds.getDirecion(pedido.getDireccion_id());
		     listaDirecciones.add(d);
		 }

		 if (!lista.isEmpty()) {
		     model.addAttribute("pedidos", lista);       // Lista de pedidos
		     model.addAttribute("direcciones", listaDirecciones);  // Lista de direcciones
		 }

		 return "MisPedidos";  // Nombre de la vista
	}
	
	@PostMapping("/cancelarPedido")
	public String cancelarDetalle(@RequestParam("pedidoId") String idPedido, Model model){
		String pagina = "redirect:/verMisPedidos";
		int id = Integer.parseInt(idPedido);
		
		if(!ps.cancelarDetalle(id)) {
			model.addAttribute("error", "No se puede cancelar");
			pagina = "forward:/verDetalle";
		}

		return pagina;
	}
}
