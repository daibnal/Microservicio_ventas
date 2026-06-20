package duoc.dairys.ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import duoc.dairys.ventas.model.MetodoPago;

public interface MtdoPagoRepo extends JpaRepository<MetodoPago, Long> {
    
}
