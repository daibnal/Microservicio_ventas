package duoc.dairys.ventas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import duoc.dairys.ventas.DTO.VentaDTO;
import duoc.dairys.ventas.model.Venta;
import duoc.dairys.ventas.service.VentaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VentaController.class)
@ActiveProfiles("test")
public class VentaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VentaService ventaService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void registrarVenta_Exito() throws Exception {
        VentaDTO dtoEntrada = new VentaDTO();
        dtoEntrada.setIdPedido(100L);

        Venta ventaRetorno = new Venta(1L, 100L, LocalDateTime.now(), "PAGADA");

        Mockito.when(ventaService.registrarVenta(any(VentaDTO.class))).thenReturn(ventaRetorno);

        mockMvc.perform(post("/api/ventas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoEntrada)))
                .andExpect(status().isCreated()) // 201
                .andExpect(jsonPath("$.mensaje", is("Venta registrada")))
                .andExpect(jsonPath("$.data.idVenta", is(1)))
                .andExpect(jsonPath("$.data.estado", is("PAGADA")));
    }

    @Test
    void listarVentas_Exito() throws Exception {
        Venta v1 = new Venta(1L, 100L, LocalDateTime.now(), "PAGADA");
        Venta v2 = new Venta(2L, 101L, LocalDateTime.now(), "ANULADA");

        List<Venta> lista = Arrays.asList(v1, v2);

        Mockito.when(ventaService.listarVentas()).thenReturn(lista);

        mockMvc.perform(get("/api/ventas"))
                .andExpect(status().isOk()) // 200
                .andExpect(jsonPath("$.mensaje", is("Ventas obtenidas")))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].idVenta", is(1)));
    }

    @Test
    void obtenerVenta_Exito() throws Exception {
        Venta v = new Venta(10L, 200L, LocalDateTime.now(), "PAGADA");

        Mockito.when(ventaService.obtenerVenta(10L)).thenReturn(v);

        mockMvc.perform(get("/api/ventas/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje", is("Venta encontrada")))
                .andExpect(jsonPath("$.data.idVenta", is(10)))
                .andExpect(jsonPath("$.data.idPedido", is(200)));
    }

    @Test
    void anularVenta_Exito() throws Exception {
        Venta vActualizada = new Venta(5L, 150L, LocalDateTime.now(), "ANULADA");

        Mockito.when(ventaService.anularVenta(5L)).thenReturn(vActualizada);

        mockMvc.perform(put("/api/ventas/5/anular"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje", is("Venta anulada correctamente")))
                .andExpect(jsonPath("$.data.estado", is("ANULADA")));
    }
}
