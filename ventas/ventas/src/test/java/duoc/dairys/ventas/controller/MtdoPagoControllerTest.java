package duoc.dairys.ventas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import duoc.dairys.ventas.model.MetodoPago;
import duoc.dairys.ventas.service.MtdoPagoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MtdoPagoController.class)
@ActiveProfiles("test")
public class MtdoPagoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MtdoPagoService metodoPagoServicio;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void obtenerMetodos_Exito() throws Exception {
        MetodoPago m1 = new MetodoPago(1L, "EFECTIVO", true);
        MetodoPago m2 = new MetodoPago(2L, "TRANSFERENCIA", false);

        List<MetodoPago> lista = Arrays.asList(m1, m2);

        Mockito.when(metodoPagoServicio.listarMetodos()).thenReturn(lista);

        mockMvc.perform(get("/metodos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].tipoMetodo", is("EFECTIVO")));
    }

    @Test
    void guardarMetodo_Exito() throws Exception {
        MetodoPago entrada = new MetodoPago(null, "CREDITO", true);
        MetodoPago salida = new MetodoPago(10L, "CREDITO", true);

        Mockito.when(metodoPagoServicio.guardarMetodo(any(MetodoPago.class))).thenReturn(salida);

        mockMvc.perform(post("/metodos/guardar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entrada)))
                .andExpect(status().isCreated()) // 201
                .andExpect(jsonPath("$.idMetodo", is(10)))
                .andExpect(jsonPath("$.tipoMetodo", is("CREDITO")));
    }

    @Test
    void activarMetodo_Exito() throws Exception {
        MetodoPago salida = new MetodoPago(1L, "DEBITO", true);

        Mockito.when(metodoPagoServicio.activarMetodo(1L)).thenReturn(salida);

        // Spring retorna 200 OK por defecto cuando devolvemos un objeto directo
        mockMvc.perform(put("/metodos/activar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activo", is(true)));
    }

    @Test
    void desactivarMetodo_Exito() throws Exception {
        Mockito.when(metodoPagoServicio.desactivarMetodo(1L)).thenReturn(true);

        mockMvc.perform(put("/metodos/desactivar/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Metodo desactivado correctamente"));
    }

    @Test
    void desactivarMetodo_NoEncontrado() throws Exception {
        Mockito.when(metodoPagoServicio.desactivarMetodo(99L)).thenReturn(false);

        mockMvc.perform(put("/metodos/desactivar/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Metodo de pago no encontrado"));
    }
}
