package mx.casino.chingon.repositorios;

import mx.casino.chingon.entidades.Mesa;
import mx.casino.chingon.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MesaRepo extends JpaRepository<Mesa, Long> {
    List<Mesa> findByUsuario(Usuario usuario);
}
