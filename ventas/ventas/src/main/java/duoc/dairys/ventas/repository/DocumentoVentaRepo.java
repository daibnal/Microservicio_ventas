package duoc.dairys.ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import duoc.dairys.ventas.model.DocumentoVenta;

@Repository
public interface DocumentoVentaRepo extends JpaRepository<DocumentoVenta, Long> {
    
}
