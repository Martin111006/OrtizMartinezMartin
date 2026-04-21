package mx.casino.chingon.servicios;

import mx.casino.chingon.dto.AbrirMesaDTO;
import mx.casino.chingon.entidades.Lanzamiento;
import mx.casino.chingon.entidades.Mesa;
import mx.casino.chingon.entidades.Mesa.EstadoMesa;
import mx.casino.chingon.entidades.Usuario;
import mx.casino.chingon.repositorios.LanzamientoRepo;
import mx.casino.chingon.repositorios.MesaRepo;
import mx.casino.chingon.repositorios.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class JuegoServicio {

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private MesaRepo mesaRepo;

    @Autowired
    private LanzamientoRepo lanzamientoRepo;

    private final Random rng = new Random();

    public Mesa abrirMesa(AbrirMesaDTO dto) {
        Usuario usuario = usuarioRepo.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + dto.getUsuarioId()));

        if (!usuario.isHabilitado()) {
            throw new RuntimeException("El usuario no está habilitado para jugar.");
        }

        if (usuario.getFichas() < dto.getApuesta()) {
            throw new RuntimeException("Fichas insuficientes. Saldo actual: " + usuario.getFichas());
        }

        usuario.setFichas(usuario.getFichas() - dto.getApuesta());
        usuarioRepo.save(usuario);

        Mesa mesa = new Mesa();
        mesa.setFechaApertura(LocalDateTime.now());
        mesa.setEstado(EstadoMesa.EN_JUEGO);
        mesa.setUsuario(usuario);
        mesa.setApuestaInicial(dto.getApuesta());

        return mesaRepo.save(mesa);
    }

    public Lanzamiento lanzarDados(Long mesaId) {
        Mesa mesa = mesaRepo.findById(mesaId)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada con id: " + mesaId));

        if (mesa.getEstado() == EstadoMesa.FINALIZADA) {
            throw new RuntimeException("Esta mesa ya está finalizada.");
        }

        int dado1 = rng.nextInt(6) + 1;
        int dado2 = rng.nextInt(6) + 1;
        int total = dado1 + dado2;

        boolean victoria = false;

        if (total == 7 || total == 11) {
            victoria = true;
            mesa.setEstado(EstadoMesa.FINALIZADA);
            Usuario usuario = mesa.getUsuario();
            usuario.setFichas(usuario.getFichas() + (mesa.getApuestaInicial() * 2));
            usuarioRepo.save(usuario);
            mesaRepo.save(mesa);

        } else if (total == 2 || total == 3 || total == 12) {
            victoria = false;
            mesa.setEstado(EstadoMesa.FINALIZADA);
            mesaRepo.save(mesa);

        }

        Lanzamiento lanzamiento = new Lanzamiento();
        lanzamiento.setMesa(mesa);
        lanzamiento.setDado1(dado1);
        lanzamiento.setDado2(dado2);
        lanzamiento.setTotal(total);
        lanzamiento.setVictoria(victoria);

        return lanzamientoRepo.save(lanzamiento);
    }
}
