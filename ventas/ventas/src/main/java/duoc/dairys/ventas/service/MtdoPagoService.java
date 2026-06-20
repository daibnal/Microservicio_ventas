package duoc.dairys.ventas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import duoc.dairys.ventas.model.MetodoPago;
import duoc.dairys.ventas.repository.MtdoPagoRepo;

public class MtdoPagoService {

    @Autowired
    private MtdoPagoRepo mtdoPagoRepo;


    //guardar metodo de pago
    public MetodoPago guardarMetodo(MetodoPago metodoPago){
        return mtdoPagoRepo.save(metodoPago);
    }

    //obtener todos los metodos de pago
    public List<MetodoPago> listarMetodos(){
        return mtdoPagoRepo.findAll();
    }

    //activar metodo
    public MetodoPago activarMetodo(Long idMetodo){
        MetodoPago metodo = mtdoPagoRepo.findById(idMetodo).orElseThrow(() -> new RuntimeException("No existe el método"));
        metodo.setActivo(true);

        return mtdoPagoRepo.save(metodo);
    }

    //desactivar metodo
    public boolean desactivarMetodo(Long idMetodo){
    return mtdoPagoRepo.findById(idMetodo).map(metodo -> {metodo.setActivo(false);
            mtdoPagoRepo.save(metodo);
            return true;
        })
        .orElse(false);
    }
}
