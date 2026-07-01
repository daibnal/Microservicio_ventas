package duoc.dairys.ventas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import duoc.dairys.ventas.model.MetodoPago;
import duoc.dairys.ventas.repository.MtdoPagoRepo;

@ExtendWith(MockitoExtension.class)
public class MtdoPagoServiceTest {
    @Mock
    private MtdoPagoRepo mtdoPagoRepo;

    @InjectMocks
    private MtdoPagoService mtdoPagoService;

    @Test
    void guardarMetodo_Exito() {
        MetodoPago metodo = new MetodoPago(null, "TARJETA_CREDITO", true);
        MetodoPago guardado = new MetodoPago(1L, "TARJETA_CREDITO", true);

        when(mtdoPagoRepo.save(any(MetodoPago.class))).thenReturn(guardado);

        MetodoPago resultado = mtdoPagoService.guardarMetodo(metodo);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdMetodo());
        verify(mtdoPagoRepo, times(1)).save(metodo);
    }

    @Test
    void listarMetodos_Exito() {
        MetodoPago m1 = new MetodoPago(1L, "EFECTIVO", true);
        MetodoPago m2 = new MetodoPago(2L, "DEBITO", true);

        when(mtdoPagoRepo.findAll()).thenReturn(List.of(m1, m2));

        List<MetodoPago> resultado = mtdoPagoService.listarMetodos();

        assertEquals(2, resultado.size());
        verify(mtdoPagoRepo, times(1)).findAll();
    }

    @Test
    void activarMetodo_Existe() {
        MetodoPago metodo = new MetodoPago(1L, "EFECTIVO", false);

        when(mtdoPagoRepo.findById(1L)).thenReturn(Optional.of(metodo));
        when(mtdoPagoRepo.save(any(MetodoPago.class))).thenReturn(metodo);

        MetodoPago resultado = mtdoPagoService.activarMetodo(1L);

        assertNotNull(resultado);
        assertTrue(resultado.getActivo()); // Verificamos que se cambió a true
        verify(mtdoPagoRepo, times(1)).save(metodo);
    }

    @Test
    void activarMetodo_NoExiste_LanzaExcepcion() {
        when(mtdoPagoRepo.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            mtdoPagoService.activarMetodo(99L);
        });

        assertEquals("No existe el método", exception.getMessage());
        verify(mtdoPagoRepo, times(0)).save(any());
    }

    @Test
    void desactivarMetodo_Existe() {
        MetodoPago metodo = new MetodoPago(1L, "DEBITO", true);

        when(mtdoPagoRepo.findById(1L)).thenReturn(Optional.of(metodo));
        when(mtdoPagoRepo.save(any(MetodoPago.class))).thenReturn(metodo);

        boolean resultado = mtdoPagoService.desactivarMetodo(1L);

        assertTrue(resultado);
        assertFalse(metodo.getActivo()); // Verificamos que se cambió a false
        verify(mtdoPagoRepo, times(1)).save(metodo);
    }

    @Test
    void desactivarMetodo_NoExiste() {
        when(mtdoPagoRepo.findById(99L)).thenReturn(Optional.empty());

        boolean resultado = mtdoPagoService.desactivarMetodo(99L);

        assertFalse(resultado);
        verify(mtdoPagoRepo, times(0)).save(any());
    }
}
