package practica.spring.ejemplo.domain.citaEscolar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaEscolarRepository extends JpaRepository<CitaEscolar, Long> {
}
