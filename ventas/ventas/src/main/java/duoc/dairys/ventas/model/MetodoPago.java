package duoc.dairys.ventas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "metodos_pago")
public class MetodoPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMetodo;

    @Column(nullable = false, name = "tipo_metodo")
    @NotBlank(message = "El tipo de pago es obligatorio")
    private String tipoMetodo;

    @Column(nullable = false, name = "activo_metodo")
    private Boolean activo;
}
