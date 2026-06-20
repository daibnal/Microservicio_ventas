package duoc.dairys.ventas.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duoc.dairys.ventas.DTO.VentaDTO;
import duoc.dairys.ventas.model.Venta;
import duoc.dairys.ventas.repository.VentaRepo;

@Service
public class VentaService {
    
    @Autowired
    private VentaRepo ventaRepo;


    //Registrar venta
    public Venta registrarVenta(VentaDTO dto){
        Venta venta = new Venta();

        venta.setIdPedido(dto.getIdPedido());
        venta.setFechaVenta(LocalDateTime.now());
        venta.setEstado("PAGADA");

        return ventaRepo.save(venta);
    }

    //Listar ventas
    public List<Venta> listarVentas(){
        return ventaRepo.findAll();
    }

    //obtener venta
    public Venta obtenerVenta(Long id){
        return ventaRepo.findById(id).orElseThrow(() -> new RuntimeException("Venta no encontrada"));
    }

    //cambiar estado de la venta
    public Venta cambiarEstado(Long id, String estado){
        Venta venta = obtenerVenta(id);
        venta.setEstado(estado);

        return ventaRepo.save(venta);
    }

    //anular venta
    public Venta anularVenta(Long id) {
        Venta venta = obtenerVenta(id);

        venta.setEstado("ANULADA");
        return ventaRepo.save(venta);
    }
}
