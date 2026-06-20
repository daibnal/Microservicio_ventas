package duoc.dairys.ventas.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duoc.dairys.ventas.DTO.DocumentoVentaDTO;
import duoc.dairys.ventas.model.DocumentoVenta;
import duoc.dairys.ventas.repository.DocumentoVentaRepo;

@Service
public class DocumentoVentaService {
    
    @Autowired
    private DocumentoVentaRepo documentoRepo;

    //generar documento de venta
    public DocumentoVenta generarDocumento(DocumentoVentaDTO dto) {
        DocumentoVenta documento = new DocumentoVenta();

        documento.setIdVenta(dto.getIdVenta());
        documento.setTipoDocumento(dto.getTipoDocumento());
        documento.setFechaEmision(LocalDateTime.now());

        return documentoRepo.save(documento);
    }

    //obtener documento de venta
    public DocumentoVenta obtenerDocumento(Long id) {
        return documentoRepo.findById(id).orElseThrow(() -> new RuntimeException("Documento no encontrado"));
    }

    //eliminar documento de venta
    public void eliminarDocumento(Long id) {
        DocumentoVenta documento = obtenerDocumento(id);
        documentoRepo.delete(documento);
    }
}
