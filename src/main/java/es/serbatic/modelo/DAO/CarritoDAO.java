package es.serbatic.modelo.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.serbatic.modelo.VO.CarritoVO;

@Repository
public interface CarritoDAO extends JpaRepository<CarritoVO, Integer>{
	@Query("SELECT c FROM CarritoVO c WHERE c.usuario_id = :usuarioId")
    List<CarritoVO> findByUsuarioId(@Param("usuarioId") int usuarioId);
	@Query("SELECT c FROM CarritoVO c WHERE c.usuario_id = :usuarioId AND c.producto_id = :productoId")
	CarritoVO findByUsuarioIdAndProductoId(@Param("usuarioId") int usuarioId, @Param("productoId") int productoId);
	@Query("SELECT COUNT(c) > 0 FROM CarritoVO c WHERE c.usuario_id = :usuarioId AND c.producto_id = :productoId")
	boolean existsByUsuarioIdAndProductoId(@Param("usuarioId") int usuarioId, @Param("productoId") int productoId);
}
