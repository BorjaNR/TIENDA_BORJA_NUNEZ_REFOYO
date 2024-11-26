package es.serbatic.modelo.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.serbatic.modelo.VO.PedidoVO;

@Repository
public interface PedidoDAO extends JpaRepository<PedidoVO, Integer>{
	@Query("SELECT p FROM PedidoVO p WHERE p.usuario_id = :usuarioId")
	List<PedidoVO> findByUsuarioId(@Param("usuarioId") int usuarioId);
	@Query("SELECT p FROM PedidoVO p WHERE p.usuario_id = :usuarioId AND p.numfactura = :numFactura")
    PedidoVO findByUsuarioIdAndNumFactura(@Param("usuarioId") int usuarioId, @Param("numFactura") String numFactura);
}
