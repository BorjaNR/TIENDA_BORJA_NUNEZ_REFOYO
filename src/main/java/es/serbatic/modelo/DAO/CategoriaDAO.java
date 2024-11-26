package es.serbatic.modelo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.serbatic.modelo.VO.CategoriaVO;

@Repository
public interface CategoriaDAO extends JpaRepository<CategoriaVO, Integer>{

}
