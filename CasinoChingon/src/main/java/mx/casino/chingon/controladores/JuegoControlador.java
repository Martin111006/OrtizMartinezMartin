package mx.casino.chingon.controladores;

import mx.casino.chingon.dto.AbrirMesaDTO;
import mx.casino.chingon.entidades.Lanzamiento;
import mx.casino.chingon.entidades.Mesa;
import mx.casino.chingon.servicios.JuegoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/juego")
public class JuegoControlador {

    @Autowired
    private JuegoServicio juegoServicio;
    @PostMapping("/mesa")
    public ResponseEntity<Mesa> abrirMesa(@RequestBody AbrirMesaDTO dto) {
        Mesa mesa = juegoServicio.abrirMesa(dto);
        return ResponseEntity.ok(mesa);
    }
    @PostMapping("/mesa/{mesaId}/tirar")
    public ResponseEntity<Lanzamiento> tirar(@PathVariable Long mesaId) {
        Lanzamiento resultado = juegoServicio.lanzarDados(mesaId);
        return ResponseEntity.ok(resultado);
    }
}
