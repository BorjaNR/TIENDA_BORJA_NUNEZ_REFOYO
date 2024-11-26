package es.serbatic.modelo.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.serbatic.modelo.VO.ProductoVO;

@Repository
public interface ProductoDAO extends JpaRepository<ProductoVO, Integer>{
	@Query("SELECT p FROM ProductoVO p WHERE p.categoria.id = :idCategoria")
    List<ProductoVO> findByCategoriaId(@Param("idCategoria") Integer idCategoria);
}
