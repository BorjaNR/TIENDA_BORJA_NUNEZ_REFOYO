package es.serbatic.modelo.VO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "direccion")
@ToString(callSuper = true) 
public class DireccionVO {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int usuario_id;
	private String calle;
	private int numero;
	private String piso;
	
	@Override
    public String toString() {
        return calle + ", " + numero + ", " + piso;
    }
}
