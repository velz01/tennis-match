package org.velz.tennismatch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.velz.tennismatch.enums.EPlayer;

@Entity
@Table(name = "matches")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player1")
    private Player player1;
    @ManyToOne
    @JoinColumn(name = "player2")
    private Player player2;
    @ManyToOne
    @JoinColumn(name = "winner")
    private Player winner;

    @Transient
    private MatchScore matchScore;


}
