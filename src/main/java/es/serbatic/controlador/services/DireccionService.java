package es.serbatic.controlador.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.serbatic.modelo.DAO.DireccionDAO;
import es.serbatic.modelo.VO.DireccionVO;

@Service
public class DireccionService {
	
	@Autowired
	DireccionDAO dd;
	
	public void crearDireccion(DireccionVO d) {
		dd.save(d);
	}
	
	public boolean validDireccion(DireccionVO d) { 
	    if (d == null) {
	        return false;
	    }

	    // Validamos que los campos no estén vacíos o inválidos
	    boolean esValido = d.getCalle() != null && !d.getCalle().isBlank()
	                    && d.getPiso() != null && !d.getPiso().isBlank()
	                    && d.getNumero() > 0;

	    return esValido;
	}
	
	public List<DireccionVO> getListadoPorUsuarioId(int id){
		return dd.findByUsuarioId(id);
	}
	
	public DireccionVO getDirecion(int id) {
		DireccionVO d = dd.getById(id);
		
		return d;
	}
}
