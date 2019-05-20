package infrastructure;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import domain.Locuinta;
import domain.Request;
import infrastructure.connection.ConnectionF;
import infrastructure.connection.HibernateCon;
import infrastructure.persistence.RequestDaoInterface;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static infrastructure.LocuintaDao.LOGGER;

public class RequestDao implements RequestDaoInterface<Request, String> {

    HibernateCon connection = new HibernateCon();


    public List<Request> getAllRequests(){
        Connection dbconn = (Connection) ConnectionF.getConnection();
        String sql = "SELECT `request`.`idRequest`,`request`.`idDocument`,`request`.`idUser`,`request`.`status` FROM `primarie`.`request`;";
        List<Request> reqs = new ArrayList<Request>();
        PreparedStatement findSt = null;
        ResultSet rs = null;
        try {
            findSt = (PreparedStatement) dbconn.prepareStatement(sql);
            rs = findSt.executeQuery(sql);
            while(rs.next())
            {
                Request req = new Request(rs.getInt("idRequest"),rs.getInt("idUser"), rs.getInt("idDocument"),rs.getString("status"));
                reqs.add(req);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return (reqs);
    }

    public int updateRequest(int idReq,String status){
        Connection dbconn = (Connection) ConnectionF.getConnection();
        PreparedStatement updateReq = null;
        String sql = "UPDATE `primarie`.`request` SET `status` = ? WHERE idRequest = ? ;";
        int upId = -1;
        try {
            updateReq = (PreparedStatement) dbconn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            updateReq.setString(1, status);
            updateReq.setInt(2, idReq);
            updateReq.executeUpdate();
            ResultSet rs = updateReq.getGeneratedKeys();

            if(rs.next()) {
                upId= rs.getInt(1);
            }

        }catch(SQLException e) {
            LOGGER.log(Level.WARNING, "RequestDAO: update", e.getMessage());
        } finally { ConnectionF.close(updateReq);
            ConnectionF.close(dbconn);
        }
        return upId;

    }

    public int addRequest(Request request) {
        Connection dbcon = (Connection) ConnectionF.getConnection();
        PreparedStatement insertRequest = null;
        String sql = "INSERT INTO `primarie`.`request`(`idDocument`,`idUser`,`status`)VALUES(?,?,?);";
        int insertId =  -1;
        try {

            insertRequest = (PreparedStatement) dbcon.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            insertRequest.setInt(1, request.getIdDocument());
            insertRequest.setInt(2, request.getIdUser());
            insertRequest.setString(3, request.getStatus());

            insertRequest.executeUpdate();

            ResultSet rs = insertRequest.getGeneratedKeys();
            if(rs.next()) {
                insertId= rs.getInt(1);
            }
        }catch(SQLException e) {
            LOGGER.log(Level.WARNING, "RequestDAO: insert", e.getMessage());
        } finally { ConnectionF.close(insertRequest);
            ConnectionF.close(dbcon);
        }
        return insertId;
    }

    public void persist(Request entity) {
        connection.getCurrentSession().save(entity);
    }

    public void update(Request entity) {
        connection.getCurrentSession().update(entity);
    }

    public Request findById(String id) {
        Request req = (Request) connection.getCurrentSession().get(Request.class, Integer.parseInt(id));
        return req;
    }

    public void delete(Request entity) {
        connection.getCurrentSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    public List<Request> findAll() {
        List<Request> reqs = (List<Request>) connection.getCurrentSession().createQuery("from Request").list();
        return reqs;
    }

    public void deleteAll() {
        List<Request> entityList = findAll();
        for (Request entity : entityList) {
            delete(entity);
        }
    }

}
