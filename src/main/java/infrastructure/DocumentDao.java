package infrastructure;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import domain.Document;
import domain.Document;
import domain.Request;
import infrastructure.connection.ConnectionF;
import infrastructure.connection.HibernateCon;
import infrastructure.persistence.DocumentDaoInterface;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.print.Doc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static infrastructure.LocuintaDao.LOGGER;

public class DocumentDao implements DocumentDaoInterface<Document, String> {

    HibernateCon connection = new HibernateCon();

    public List<Document> getAllDocuments(){
        Connection dbconn = (Connection) ConnectionF.getConnection();
        String sql = "SELECT `document`.`idDocument`,`document`.`tip` FROM `primarie`.`document`;";
        List<Document> docs = new ArrayList<Document>();
        PreparedStatement findSt = null;
        ResultSet rs = null;
        try {
            findSt = (PreparedStatement) dbconn.prepareStatement(sql);
            rs = findSt.executeQuery(sql);
            while(rs.next())
            {
                Document doc = new Document(rs.getInt("idDocument"),rs.getString("tip"));
                docs.add(doc);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return (docs);
    }

    public int deleteDocument(int idDoc){
        String sql = "DELETE FROM `primarie`.`document` WHERE `document`.`idDocument` = ?";
        Connection dbcon = (Connection) ConnectionF.getConnection();
        PreparedStatement deleteDoc = null;
        int deleteId =  -1;
        try {

            deleteDoc = (PreparedStatement) dbcon.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            deleteDoc.setInt(1, idDoc);
            deleteDoc.executeUpdate();

            ResultSet rs = deleteDoc.getGeneratedKeys();
            if(rs.next()) {
                deleteId= rs.getInt(1);
            }
        }catch(SQLException e) {
            LOGGER.log(Level.WARNING, "DocumentDAO: delete", e.getMessage());
        } finally { ConnectionF.close(deleteDoc);
            ConnectionF.close(dbcon);
        }
        return deleteId;
    }

    public int insertDocument(String tipDoc) {
        Connection dbcon = (Connection) ConnectionF.getConnection();
        String sql ="INSERT INTO `primarie`.`document`(`tip`)VALUES(?);";
        PreparedStatement insertDoc = null;
        int insertId =  -1;
        try {

            insertDoc = (PreparedStatement) dbcon.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            insertDoc.setString(1, tipDoc);

            insertDoc.executeUpdate();

            ResultSet rs = insertDoc.getGeneratedKeys();
            if(rs.next()) {
                insertId= rs.getInt(1);
            }
        }catch(SQLException e) {
            LOGGER.log(Level.WARNING, "DocumentDAO: insert", e.getMessage());
        } finally { ConnectionF.close(insertDoc);
            ConnectionF.close(dbcon);
        }
        return insertId;
    }

    public void persist(Document entity) {
        connection.getCurrentSession().save(entity);
    }

    public void update(Document entity) {
        connection.getCurrentSession().update(entity);
    }

    public Document findById(String id) {
        Document doc = (Document) connection.getCurrentSession().get(Document.class, Integer.parseInt(id));
        return doc;
    }

    public void delete(Document entity) {
        connection.getCurrentSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    public List<Document> findAll() {
        List<Document> docs = (List<Document>) connection.getCurrentSession().createQuery("from Document").list();
        return docs;
    }

    public void deleteAll() {
        List<Document> entityList = findAll();
        for (Document entity : entityList) {
            delete(entity);
        }
    }
}
