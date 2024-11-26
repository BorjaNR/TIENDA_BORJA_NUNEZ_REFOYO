package es.serbatic.controlador.controllers;

import java.util.ArrayList;
import java.util.List;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.serbatic.controlador.services.DetalleService;
import es.serbatic.controlador.services.DireccionService;
import es.serbatic.controlador.services.PedidoService;
import es.serbatic.controlador.services.ProductoService;
import es.serbatic.controlador.services.UsuarioService;
import es.serbatic.modelo.VO.DetallePedidoVO;
import es.serbatic.modelo.VO.DireccionVO;
import es.serbatic.modelo.VO.PedidoVO;
import es.serbatic.modelo.VO.ProductoVO;
import es.serbatic.modelo.VO.UsuarioVO;
import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioService us;
	
	@Autowired
	private DireccionService ds;
	
	@GetMapping("/verLogin")
	public String verLogin() {
		return "Login";
	}
	
	@PostMapping("/verLogin/logearse")
	public String login(@RequestParam("email") String email, @RequestParam("password") String pass, HttpSession sesion, Model model) {
		String pagina = "Login";
		UsuarioVO u = null;
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		u = us.getUsuarioByEmail(email);
		
		if(us.validUsuarioLogin(u) && u.getRol_id()==0) {
			if(passwordEncryptor.checkPassword(pass, u.getClave())) {
				sesion.setAttribute("usuario", u);
				sesion.setAttribute("idUsuario", u.getId());
				pagina = "redirect:/";
			}
		}else {
			model.addAttribute("error", "login incorrecto");
		}
		
		return pagina;
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession sesion) {
		sesion.setAttribute("usuario", null);
		sesion.setAttribute("idUsuario", null);
		
		return "redirect:/";
	}
	
	@GetMapping("/verRegistro")
	public String verRegistro() {
		return "Registro";
	}
	
	@PostMapping("/verRegistro/registrarse")
	public String registro(@ModelAttribute UsuarioVO u, @RequestParam("calle") String calle, 
		@RequestParam("numero_portal") String numero, @RequestParam("piso") String piso, HttpSession sesion, Model model) {
		DireccionVO d = new DireccionVO();
		d.setCalle(calle);
		d.setNumero(Integer.parseInt(numero));
		d.setPiso(piso);
		
		String pagina = "Registro";
		
		if(us.validUsuarioRegistro(u) && ds.validDireccion(d)) {
			u.setClave(us.encriptarPass(u.getClave()));
			us.crearUsuario(u);
			UsuarioVO uCreado = us.getUsuarioByEmail(u.getEmail());
			d.setUsuario_id(uCreado.getId());
			ds.crearDireccion(d);
			sesion.setAttribute("usuario", u);
			sesion.setAttribute("idUsuario", u.getId());
			pagina = "redirect:/";
		}else {
			model.addAttribute("error", "login incorrecto");
		}
		
		return pagina;
	}
	
	@GetMapping("/verMiCuenta")
	public String verMiCuenta(HttpSession sesion, Model model) {
		UsuarioVO u = (UsuarioVO) sesion.getAttribute("usuario");
		
		model.addAttribute("usuario", u);
		model.addAttribute("direcciones", ds.getListadoPorUsuarioId(u.getId()));
		
		return "MiCuenta";
	}
	
	@GetMapping("/verCambiarPass")
	public String verCambiarPass() {
		return "cambiarPass";
	}
	
	@PostMapping("/verCambiarPass/cambiar")
	public String cambiarPass(@RequestParam("passActual") String passActual, @RequestParam("passNueva") String passNueva,
			@RequestParam("passNuevaRepe") String passNueva2, HttpSession sesion, Model model) {
		
		String pagina = "cambiarPass";
		UsuarioVO u = (UsuarioVO) sesion.getAttribute("usuario");
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		
			if(passwordEncryptor.checkPassword(passActual, u.getClave()) && passNueva.equals(passNueva2)) {
				u.setClave(us.encriptarPass(passNueva));
				us.crearUsuario(u);				
				sesion.setAttribute("usuario", u);

				pagina = "redirect:/";
			}else {
				model.addAttribute("error", "cambio de contraseña incorrecto");
			}
		
		return pagina;
		
	}
}
