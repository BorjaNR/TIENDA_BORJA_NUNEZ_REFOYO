package es.serbatic.controlador.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import es.serbatic.controlador.services.DireccionService;
import es.serbatic.modelo.VO.DireccionVO;
import jakarta.servlet.http.HttpSession;

@Controller
public class DireccionController {

	@Autowired
	DireccionService ds;
	
	@GetMapping("/verNuevaDireccion")
	public String verNuevaDireccion() {
		return "nuevaDireccion";
	}
	
	@PostMapping("/verNuevaDireccion/anadir")
	public String anadirDireccion(@ModelAttribute DireccionVO d, HttpSession sesion, Model model) {
		String pagina = "redirect:/verNuevaDireccion";
		Integer id = (Integer) sesion.getAttribute("idUsuario");
		
		if(ds.validDireccion(d)) {
			d.setUsuario_id(id);
			ds.crearDireccion(d);
			pagina = "redirect:/verMiCuenta";
		}else {
			model.addAttribute("error", "Calle incorrecta");
		}
		
		return pagina;
	}
}
