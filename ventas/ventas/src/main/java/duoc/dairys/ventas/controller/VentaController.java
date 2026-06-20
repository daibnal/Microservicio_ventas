package duoc.dairys.ventas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import duoc.dairys.ventas.DTO.ResponseDTO;
import duoc.dairys.ventas.DTO.VentaDTO;
import duoc.dairys.ventas.service.VentaService;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {
    
    @Autowired
    private VentaService ventaService;

    @PostMapping
    public ResponseEntity<ResponseDTO> registrarVenta(@RequestBody VentaDTO dto){
        return ResponseEntity.ok(new ResponseDTO("Venta registrada",ventaService.registrarVenta(dto)));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> listarVentas(){
        return ResponseEntity.ok( new ResponseDTO("Ventas obtenidas",ventaService.listarVentas()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> obtener(@PathVariable Long id){
        return ResponseEntity.ok(new ResponseDTO("Venta encontrada",ventaService.obtenerVenta(id)));
    }

}
