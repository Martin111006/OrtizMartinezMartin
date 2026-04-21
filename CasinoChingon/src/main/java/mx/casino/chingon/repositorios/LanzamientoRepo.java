package mx.casino.chingon.repositorios;

import mx.casino.chingon.entidades.Lanzamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanzamientoRepo extends JpaRepository<Lanzamiento, Long> {
}
