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
@Entity @Table(name = "detalle")
public class DetallePedidoVO {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int pedido_id;
	private int producto_id;
	private int unidades;
	private double preciounidad;
	private double impuesto;
	private double total;
}
