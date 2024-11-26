package es.serbatic.modelo.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.serbatic.modelo.VO.DetallePedidoVO;

@Repository
public interface DetalleDAO extends JpaRepository<DetallePedidoVO, Integer>{
	@Query("SELECT d FROM DetallePedidoVO d WHERE d.pedido_id = :pedidoId")
    List<DetallePedidoVO> findByPedidoId(@Param("pedidoId") int pedidoId);
}
