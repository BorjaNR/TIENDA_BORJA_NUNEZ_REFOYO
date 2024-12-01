package es.serbatic.controlador.services;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.serbatic.modelo.DAO.UsuarioDAO;
import es.serbatic.modelo.VO.UsuarioVO;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioDAO dao;
	
	public boolean validUsuarioLogin(UsuarioVO u) {
		boolean esValido = false;
		
		if(u != null) {
			if(!u.getEmail().isBlank() || !u.getEmail().isEmpty()) {
				esValido = true;
			}else {
				esValido= false;
			}
			if(!u.getClave().isBlank() || !u.getClave().isEmpty()) {
				esValido = true;
			}else {
				esValido= false;
			}
		}	
		
		return esValido;
	}
	
	public boolean validUsuarioRegistro(UsuarioVO u) {
		if (u == null) {
	        return false;
	    }

	    // Validamos si todos los campos no están vacíos
	    boolean esValido = !u.getNombre().isBlank() && !u.getApellidos().isBlank() 
	                        && !u.getEmail().isBlank() && !u.getClave().isBlank();

	    // Verificamos si el email ya existe
	    if (esValido && !dao.existsByEmail(u.getEmail())) {
	        return true;
	    }

	    return false;
	}
	
	public UsuarioVO getUsuarioByEmail(String email) {
		UsuarioVO u = null;

		u = dao.findByEmail(email);
		
		return u;
	}
	
	public void crearUsuario(UsuarioVO u) {
		dao.save(u);
	}
	
	public boolean existeUsuarioId(int id) {
		return dao.existsById(id);
	}
	
	public String encriptarPass(String pass) {
		StrongPasswordEncryptor pe = new StrongPasswordEncryptor();
		String passEncriptada = pe.encryptPassword(pass);
		
		return passEncriptada;
	}
}
