package domain;

import org.w3c.dom.css.DocumentCSS;

import java.io.Serializable;
import javax.persistence.*;
import javax.print.Doc;

@Entity
@Table(name = "document")
public class Document implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDocument;

    @Column(nullable = false)
    private String tip;

    public Document(){}

    public Document(String tip) {
        this.tip = tip;
    }

    public Document(int idDocument, String tip){this.idDocument = idDocument; this.tip = tip;}

    public int getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(int idDocument) {
        this.idDocument = idDocument;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }


}
