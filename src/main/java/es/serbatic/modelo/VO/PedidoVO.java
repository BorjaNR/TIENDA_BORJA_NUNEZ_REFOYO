package es.serbatic.modelo.VO;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "pedido")
public class PedidoVO {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int usuario_id;
	private int direccion_id;
	private Date fecha;
	private String metodopago;
	private String numfactura;
	private String estado;
	private double total;
}
