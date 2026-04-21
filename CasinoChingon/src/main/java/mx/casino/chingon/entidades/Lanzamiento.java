package mx.casino.chingon.entidades;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lanzamientos")
@Data
@NoArgsConstructor
public class Lanzamiento {

    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mesa_id", nullable = false)
    private Mesa mesa;

    @Column(nullable = false)
    private int dado1;

    @Column(nullable = false)
    private int dado2;

    @Column(nullable = false)
    private int total;

    @Column(nullable = false)
    private boolean victoria;
}
