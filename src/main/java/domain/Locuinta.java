package domain;

import javax.persistence.*;
import java.io.Serializable;



@Entity
@Table(name = "locuinta")
public class Locuinta implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLocuinta;

    @Column(nullable = false)
    private int idUser;

    @Column(nullable = false)
    private String adresa;

    public Locuinta(){}

    public Locuinta(int idUser, String adresa) {
        this.idUser = idUser;
        this.adresa = adresa;
    }

    public int getIdLocuinta() {
        return idLocuinta;
    }

    public void setIdLocuinta(int idLocuinta) {
        this.idLocuinta = idLocuinta;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    @Override
    public String toString() {
        return "Locuinta{" +
                "idLocuinta=" + idLocuinta +
                ", adresa='" + adresa + '\'' +
                '}';
    }
}
