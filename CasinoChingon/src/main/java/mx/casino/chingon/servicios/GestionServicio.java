package mx.casino.chingon.servicios;

import mx.casino.chingon.entidades.Mesa;
import mx.casino.chingon.entidades.Mesa.EstadoMesa;
import mx.casino.chingon.entidades.Usuario;
import mx.casino.chingon.repositorios.MesaRepo;
import mx.casino.chingon.repositorios.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestionServicio {

    @Autowired
    private MesaRepo mesaRepo;

    @Autowired
    private UsuarioRepo usuarioRepo;
    public Mesa cerrarMesa(Long mesaId) {
        Mesa mesa = mesaRepo.findById(mesaId)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada con id: " + mesaId));

        if (mesa.getEstado() == EstadoMesa.FINALIZADA) {
            throw new RuntimeException("La mesa ya está cerrada.");
        }

        mesa.setEstado(EstadoMesa.FINALIZADA);
        return mesaRepo.save(mesa);
    }

    public Usuario añadirFichas(Long usuarioId, double monto) {
        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + usuarioId));

        if (monto <= 0) {
            throw new RuntimeException("El monto a recargar debe ser mayor a cero.");
        }

        usuario.setFichas(usuario.getFichas() + monto);
        return usuarioRepo.save(usuario);
    }

    public List<Mesa> consultarMesas(Long usuarioId) {
        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + usuarioId));

        return mesaRepo.findByUsuario(usuario);
    }
}
