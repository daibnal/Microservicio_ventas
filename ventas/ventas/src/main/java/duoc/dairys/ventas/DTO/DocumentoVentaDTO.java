package duoc.dairys.ventas.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoVentaDTO {

    @NotNull(message = "El id de la venta es obligatorio")
    private Long idVenta;

    @NotBlank(message = "El tipo de documento es obligatorio")
    private String tipoDocumento;
}
