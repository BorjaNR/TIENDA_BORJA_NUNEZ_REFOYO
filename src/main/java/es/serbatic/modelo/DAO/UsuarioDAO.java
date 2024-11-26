package es.serbatic.modelo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.serbatic.modelo.VO.ProductoVO;
import es.serbatic.modelo.VO.UsuarioVO;

@Repository
public interface UsuarioDAO extends JpaRepository<UsuarioVO, Integer>{
	//Devuelve un usuario si tiene ese email y esa clave
	@Query("SELECT u FROM UsuarioVO u WHERE u.email = :email")
    UsuarioVO findByEmailAndClave(@Param("email") String email);
	
	//Devuelve un boolean dependiendo de si existe ya un usuario con ese email
	@Query("SELECT COUNT(u) > 0 FROM UsuarioVO u WHERE u.email = :email")
	boolean existsByEmail(@Param("email") String email);
}
