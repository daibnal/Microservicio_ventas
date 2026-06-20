package duoc.dairys.ventas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import duoc.dairys.ventas.DTO.DocumentoVentaDTO;
import duoc.dairys.ventas.DTO.ResponseDTO;
import duoc.dairys.ventas.service.DocumentoVentaService;

@RestController
@RequestMapping("/api/documentos")
public class DocumentoVentaController {

    @Autowired
    private DocumentoVentaService documentoService;

    //generar un documento de venta
    @PostMapping
    public ResponseEntity<ResponseDTO> generarDocumento( @RequestBody DocumentoVentaDTO dto) {
        return ResponseEntity.ok(new ResponseDTO("Documento generado correctamente", documentoService.generarDocumento(dto)));
    }

    //obtener un documento de venta
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> obtenerDocumento( @PathVariable Long id) {
        return ResponseEntity.ok(new ResponseDTO("Documento encontrado", documentoService.obtenerDocumento(id)) );
    }

    //eliminar un documento de venta
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> eliminarDocumento(@PathVariable Long id) {
        documentoService.eliminarDocumento(id);
        return ResponseEntity.ok(new ResponseDTO("Documento eliminado correctamente", null));
    }
}

