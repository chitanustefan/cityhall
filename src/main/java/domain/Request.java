package domain;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "request")
public class Request implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRequest;

    @Column(nullable = false)
    private int idUser;

    @Column(nullable = false)
    private int idDocument;

    @Column(nullable = false)
    private String status;

    public Request(){}

    public Request(int idUser, int idDocument, String status) {
        this.idUser = idUser;
        this.idDocument = idDocument;
        this.status = status;
    }

    public Request(int idRequest, int idUser, int idDocument, String status) {
        this.idRequest = idRequest;
        this.idUser = idUser;
        this.idDocument = idDocument;
        this.status = status;
    }


    public int getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(int idDocument) {
        this.idDocument = idDocument;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Request{" +
                "idRequest=" + idRequest +
                ", idUser=" + idUser +
                ", idDocument=" + idDocument +
                ", status='" + status + '\'' +
                '}';
    }
}
