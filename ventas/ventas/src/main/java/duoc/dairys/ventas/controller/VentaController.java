package duoc.dairys.ventas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import duoc.dairys.ventas.DTO.ResponseDTO;
import duoc.dairys.ventas.DTO.VentaDTO;
import duoc.dairys.ventas.service.VentaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {
    
    @Autowired
    private VentaService ventaService;

    //registrar venta
    @PostMapping
    public ResponseEntity<ResponseDTO> registrarVenta(@Valid @RequestBody VentaDTO dto){
        return ResponseEntity.status(201).body(new ResponseDTO("Venta registrada",ventaService.registrarVenta(dto)));
    }

    //listar ventas
    @GetMapping
    public ResponseEntity<ResponseDTO> listarVentas(){
        return ResponseEntity.ok( new ResponseDTO("Ventas obtenidas",ventaService.listarVentas()));
    }

    //obtener venta
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> obtenerVenta(@PathVariable Long id){
        return ResponseEntity.ok(new ResponseDTO("Venta encontrada",ventaService.obtenerVenta(id)));
    }

    //anular venta
    @PutMapping("/{id}/anular")
    public ResponseEntity<ResponseDTO> anularVenta(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseDTO("Venta anulada correctamente", ventaService.anularVenta(id)));
    }


}
