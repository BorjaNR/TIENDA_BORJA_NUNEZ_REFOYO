package es.serbatic.controlador.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.serbatic.modelo.DAO.CategoriaDAO;
import es.serbatic.modelo.VO.CategoriaVO;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaDAO dao;
	
	public List<CategoriaVO> getListado(){
		List<CategoriaVO> listado = new ArrayList<CategoriaVO>();
		listado = dao.findAll();
		
		return listado;
	}
}
