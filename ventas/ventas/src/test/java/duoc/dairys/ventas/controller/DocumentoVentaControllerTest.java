package duoc.dairys.ventas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import duoc.dairys.ventas.DTO.DocumentoVentaDTO;
import duoc.dairys.ventas.model.DocumentoVenta;
import duoc.dairys.ventas.service.DocumentoVentaService;

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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DocumentoVentaController.class)
@ActiveProfiles("test")
public class DocumentoVentaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DocumentoVentaService documentoService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void generarDocumento_Exito() throws Exception {
        DocumentoVentaDTO dto = new DocumentoVentaDTO();
        dto.setIdVenta(50L);
        dto.setTipoDocumento("FACTURA");

        DocumentoVenta docRetorno = new DocumentoVenta(1L, 50L, LocalDateTime.now(), "FACTURA");

        Mockito.when(documentoService.generarDocumento(any(DocumentoVentaDTO.class))).thenReturn(docRetorno);

        mockMvc.perform(post("/api/documentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje", is("Documento generado correctamente")))
                .andExpect(jsonPath("$.data.idDocumento", is(1)))
                .andExpect(jsonPath("$.data.tipoDocumento", is("FACTURA")));
    }

    @Test
    void obtenerDocumento_Exito() throws Exception {
        DocumentoVenta doc = new DocumentoVenta(10L, 20L, LocalDateTime.now(), "BOLETA");

        Mockito.when(documentoService.obtenerDocumento(10L)).thenReturn(doc);

        mockMvc.perform(get("/api/documentos/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje", is("Documento encontrado")))
                .andExpect(jsonPath("$.data.idDocumento", is(10)))
                .andExpect(jsonPath("$.data.idVenta", is(20)));
    }

    @Test
    void eliminarDocumento_Exito() throws Exception {
        Mockito.doNothing().when(documentoService).eliminarDocumento(1L);

        mockMvc.perform(delete("/api/documentos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje", is("Documento eliminado correctamente")))
                .andExpect(jsonPath("$.data", is(nullValue())));
    }
}
