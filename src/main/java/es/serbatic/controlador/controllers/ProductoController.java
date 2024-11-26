package es.serbatic.controlador.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.serbatic.controlador.services.CarritoService;
import es.serbatic.controlador.services.CategoriaService;
import es.serbatic.controlador.services.ProductoService;
import es.serbatic.modelo.VO.CategoriaVO;
import es.serbatic.modelo.VO.ProductoVO;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProductoController {
	
	@Autowired
	private ProductoService ps;
	@Autowired
	private CategoriaService cs;
	@Autowired
	private CarritoService cas;
	
	@GetMapping("")
	public String verCatalogo(Model model, HttpSession sesion){
		List<ProductoVO> listado = new ArrayList<ProductoVO>();
		List<ProductoVO> listadoProductos = new ArrayList<ProductoVO>();
		List<CategoriaVO> listadoCategorias = new ArrayList<CategoriaVO>();
		int totalCarrito = 0;
		sesion.setAttribute("totalCarritos", "0");
		
		listado = ps.getListado("");		
		listadoCategorias = cs.getListado();

		for (ProductoVO producto : listado) {
			if(producto.getBaja() == 0) {
				producto.setPrecio(producto.getPrecio() + (producto.getPrecio() * producto.getImpuesto()));
				listadoProductos.add(producto);
			}
		}
		
		model.addAttribute("catalogo", listadoProductos);
		model.addAttribute("categorias", listadoCategorias);
		
		if(sesion.getAttribute("idUsuario")!=null) {
			Integer id = (Integer) sesion.getAttribute("idUsuario");
			totalCarrito = cas.totalNumeroDeCarritos(id);
			sesion.setAttribute("totalCarritos", totalCarrito);
		}

		
//		if(("carrito") == null) {
//			request.getSession().setAttribute("carrito", new HashMap<Integer,LineaCarrito>());
//		}
		
		return "index";
	}
	
	@PostMapping("/verCatalogoCategorias")
	public String verCatalogoPorCategoria(@RequestParam("categoria") String categoria, Model model, HttpSession sesion){
		List<ProductoVO> listadoProducto = new ArrayList<ProductoVO>();
		List<CategoriaVO> listadoCategorias = new ArrayList<CategoriaVO>();
		int totalCarrito = 0;
		sesion.setAttribute("totalCarritos", "0");
		
		listadoProducto = ps.getListado(categoria);		
		listadoCategorias = cs.getListado();
		
		for (ProductoVO producto : listadoProducto) {
			producto.setPrecio(producto.getPrecio() + (producto.getPrecio() * producto.getImpuesto()));
		}
		
		model.addAttribute("catalogo", listadoProducto);
		model.addAttribute("categorias", listadoCategorias);
		
		if(sesion.getAttribute("idUsuario")!=null) {
			Integer id = (Integer) sesion.getAttribute("idUsuario");
			totalCarrito = cas.totalNumeroDeCarritos(id);
			sesion.setAttribute("totalCarritos", totalCarrito);
		}
		
		return "index";
	}
	
	@PostMapping("/verProducto")
	public String verProducto(@RequestParam("id") String id, Model model){
		ProductoVO p = new ProductoVO();
		int idProducto = Integer.parseInt(id);
		
		p = ps.obtenerProducto(idProducto);
		p.setPrecio(p.getPrecio() + (p.getPrecio() * p.getImpuesto()));
		model.addAttribute("producto", p);
		
		return "producto";
	}
}
