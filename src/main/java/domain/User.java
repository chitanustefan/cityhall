package domain;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;

    @Column(nullable = false)
    private  String nume;

    @Column(nullable = false)
    private String adresa;

    @Column(nullable = false)
    private String telefon;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String parola;

    @Column(nullable = false)
    private String role;

    public User(){

    }
    public User(String nume, String adresa, String telefon, String email, String parola, String role) {
        this.nume = nume;
        this.adresa = adresa;
        this.telefon = telefon;
        this.email = email;
        this.parola = parola;
        this.role = role;
    }

    public User(int idUser,String nume, String adresa, String telefon, String email, String parola, String role) {
        this.nume = nume;
        this.idUser = idUser;
        this.adresa = adresa;
        this.telefon = telefon;
        this.email = email;
        this.parola = parola;
        this.role = role;
    }

    public int getIdUser() {
        return idUser;
    }
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", nume='" + nume + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}