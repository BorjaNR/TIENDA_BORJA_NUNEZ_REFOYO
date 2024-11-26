package es.serbatic.modelo.VO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "carrito")
public class CarritoVO {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int producto_id;
	private int usuario_id;
	private int cantidad;
	private double precio;	
	private String nombre;
	private String ruta;
}
