package mx.casino.chingon.controladores;

import mx.casino.chingon.dto.RecargarDTO;
import mx.casino.chingon.entidades.Mesa;
import mx.casino.chingon.entidades.Usuario;
import mx.casino.chingon.servicios.GestionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gestion")
public class GestionControlador {

    @Autowired
    private GestionServicio gestionServicio;
    @PutMapping("/mesa/{mesaId}/cerrar")
    public ResponseEntity<Mesa> cerrarMesa(@PathVariable Long mesaId) {
        Mesa mesa = gestionServicio.cerrarMesa(mesaId);
        return ResponseEntity.ok(mesa);
    }

    @PutMapping("/usuario/fichas")
    public ResponseEntity<Usuario> recargarFichas(@RequestBody RecargarDTO dto) {
        Usuario usuario = gestionServicio.añadirFichas(dto.getUsuarioId(), dto.getMonto());
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/usuario/{usuarioId}/mesas")
    public ResponseEntity<List<Mesa>> historial(@PathVariable Long usuarioId) {
        List<Mesa> mesas = gestionServicio.consultarMesas(usuarioId);
        return ResponseEntity.ok(mesas);
    }
}
