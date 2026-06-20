package duoc.dairys.ventas.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaDTO {

    @NotNull(message = "El id del pedido es obligato")
    private Long idPedido;

}
