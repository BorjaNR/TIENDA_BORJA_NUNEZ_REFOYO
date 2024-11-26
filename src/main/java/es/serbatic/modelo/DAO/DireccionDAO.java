package es.serbatic.modelo.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.serbatic.modelo.VO.DireccionVO;

@Repository
public interface DireccionDAO extends JpaRepository<DireccionVO, Integer>{
    @Query("SELECT d FROM DireccionVO d WHERE d.usuario_id = :idUsuario")
    List<DireccionVO> findByUsuarioId(@Param("idUsuario") Integer idUsuario);
}
