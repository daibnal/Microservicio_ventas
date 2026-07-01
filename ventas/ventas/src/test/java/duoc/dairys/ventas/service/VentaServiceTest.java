package duoc.dairys.ventas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import duoc.dairys.ventas.DTO.VentaDTO;
import duoc.dairys.ventas.model.Venta;
import duoc.dairys.ventas.repository.VentaRepo;

@ExtendWith(MockitoExtension.class)
public class VentaServiceTest {
    @Mock
    private VentaRepo ventaRepo;

    @InjectMocks
    private VentaService ventaService;

    @Test
    void registrarVenta_Exito() {
        VentaDTO dto = new VentaDTO();
        dto.setIdPedido(100L);

        Venta guardada = new Venta();
        guardada.setIdVenta(1L);
        guardada.setIdPedido(100L);
        guardada.setEstado("PAGADA");
        guardada.setFechaVenta(LocalDateTime.now());

        when(ventaRepo.save(any(Venta.class))).thenReturn(guardada);

        Venta resultado = ventaService.registrarVenta(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdVenta());
        assertEquals("PAGADA", resultado.getEstado());
        assertEquals(100L, resultado.getIdPedido());
        verify(ventaRepo, times(1)).save(any(Venta.class));
    }

    @Test
    void listarVentas_Exito() {
        Venta v1 = new Venta(1L, 100L, LocalDateTime.now(), "PAGADA");
        Venta v2 = new Venta(2L, 101L, LocalDateTime.now(), "ANULADA");

        when(ventaRepo.findAll()).thenReturn(List.of(v1, v2));

        List<Venta> resultado = ventaService.listarVentas();

        assertEquals(2, resultado.size());
        verify(ventaRepo, times(1)).findAll();
    }

    @Test
    void obtenerVenta_Existe() {
        Venta v = new Venta(5L, 200L, LocalDateTime.now(), "PAGADA");

        when(ventaRepo.findById(5L)).thenReturn(Optional.of(v));

        Venta resultado = ventaService.obtenerVenta(5L);

        assertNotNull(resultado);
        assertEquals(5L, resultado.getIdVenta());
    }

    @Test
    void obtenerVenta_NoExiste_LanzaExcepcion() {
        when(ventaRepo.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ventaService.obtenerVenta(99L);
        });

        assertEquals("Venta no encontrada", exception.getMessage());
    }

    @Test
    void cambiarEstado_Exito() {
        Venta v = new Venta(10L, 300L, LocalDateTime.now(), "PAGADA");

        when(ventaRepo.findById(10L)).thenReturn(Optional.of(v));
        when(ventaRepo.save(any(Venta.class))).thenReturn(v);

        Venta resultado = ventaService.cambiarEstado(10L, "PROCESANDO");

        assertNotNull(resultado);
        assertEquals("PROCESANDO", resultado.getEstado()); // El estado debe haberse actualizado
        verify(ventaRepo, times(1)).save(v);
    }

    @Test
    void anularVenta_Exito() {
        Venta v = new Venta(15L, 400L, LocalDateTime.now(), "PAGADA");

        when(ventaRepo.findById(15L)).thenReturn(Optional.of(v));
        when(ventaRepo.save(any(Venta.class))).thenReturn(v);

        Venta resultado = ventaService.anularVenta(15L);

        assertNotNull(resultado);
        assertEquals("ANULADA", resultado.getEstado()); // El estado debe ser ANULADA
        verify(ventaRepo, times(1)).save(v);
    }
}
