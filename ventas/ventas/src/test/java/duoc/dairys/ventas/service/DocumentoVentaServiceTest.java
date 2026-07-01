package duoc.dairys.ventas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import duoc.dairys.ventas.DTO.DocumentoVentaDTO;
import duoc.dairys.ventas.model.DocumentoVenta;
import duoc.dairys.ventas.repository.DocumentoVentaRepo;

@ExtendWith(MockitoExtension.class)
public class DocumentoVentaServiceTest {
    @Mock
    private DocumentoVentaRepo documentoRepo;

    @InjectMocks
    private DocumentoVentaService documentoVentaService;

    @Test
    void generarDocumento_Exito() {
        // Preparar
        DocumentoVentaDTO dto = new DocumentoVentaDTO();
        // Asumiendo que el DTO tiene estos setters (ajustar si tienen otro nombre)
        dto.setIdVenta(100L);
        dto.setTipoDocumento("BOLETA");

        DocumentoVenta guardado = new DocumentoVenta();
        guardado.setIdDocumento(1L);
        guardado.setIdVenta(100L);
        guardado.setTipoDocumento("BOLETA");
        guardado.setFechaEmision(LocalDateTime.now());

        when(documentoRepo.save(any(DocumentoVenta.class))).thenReturn(guardado);

        // Ejecutar
        DocumentoVenta resultado = documentoVentaService.generarDocumento(dto);

        // Validar
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdDocumento());
        assertEquals("BOLETA", resultado.getTipoDocumento());
        verify(documentoRepo, times(1)).save(any(DocumentoVenta.class));
    }

    @Test
    void obtenerDocumento_Existe() {
        DocumentoVenta doc = new DocumentoVenta();
        doc.setIdDocumento(5L);

        when(documentoRepo.findById(5L)).thenReturn(Optional.of(doc));

        DocumentoVenta resultado = documentoVentaService.obtenerDocumento(5L);

        assertNotNull(resultado);
        assertEquals(5L, resultado.getIdDocumento());
    }

    @Test
    void obtenerDocumento_NoExiste_LanzaExcepcion() {
        when(documentoRepo.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            documentoVentaService.obtenerDocumento(99L);
        });

        assertEquals("Documento no encontrado", exception.getMessage());
    }

    @Test
    void eliminarDocumento_Exito() {
        DocumentoVenta doc = new DocumentoVenta();
        doc.setIdDocumento(10L);

        // El método eliminarDocumento llama internamente a obtenerDocumento
        when(documentoRepo.findById(10L)).thenReturn(Optional.of(doc));
        doNothing().when(documentoRepo).delete(doc);

        documentoVentaService.eliminarDocumento(10L);

        verify(documentoRepo, times(1)).findById(10L);
        verify(documentoRepo, times(1)).delete(doc);
    }

}
