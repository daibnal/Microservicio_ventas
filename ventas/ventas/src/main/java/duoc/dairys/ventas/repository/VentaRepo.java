package duoc.dairys.ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import duoc.dairys.ventas.model.Venta;

@Repository
public interface VentaRepo extends JpaRepository<Venta, Long> {

    
}
