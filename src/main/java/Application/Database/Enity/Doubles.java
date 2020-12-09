package Application.Database.Enity;

import Application.Database.DTO.DoublesCreateDTO;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Doubles extends BaseEntity {

    @NotNull
    private String score;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "winner1_id")
    private Player winner1;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "winner2_id")
    private Player winner2;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "loser1_id")
    private Player loser1;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "loser2_id")
    private Player loser2;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    public Doubles(@NotNull String score, @NotNull Player winner1, @NotNull Player winner2, @NotNull Player loser1,
                   @NotNull Player loser2, @NotNull Tournament tournament) {
        this.score = score;
        this.winner1 = winner1;
        this.winner2 = winner2;
        this.loser1 = loser1;
        this.loser2 = loser2;
        this.tournament = tournament;
    }

    public Doubles() {

    }

    public Player getWinner1() {
        return winner1;
    }

    public void setWinner1(Player winner1) {
        this.winner1 = winner1;
    }

    public Player getWinner2() {
        return winner2;
    }

    public void setWinner2(Player winner2) {
        this.winner2 = winner2;
    }

    public Player getLoser1() {
        return loser1;
    }

    public void setLoser1(Player loser1) {
        this.loser1 = loser1;
    }

    public Player getLoser2() {
        return loser2;
    }

    public void setLoser2(Player loser2) {
        this.loser2 = loser2;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
