package infrastructure;

import domain.Locuinta;

import domain.User;
import infrastructure.connection.ConnectionF;
import infrastructure.connection.HibernateCon;
import infrastructure.persistence.LocuintaDaoInterface;
import infrastructure.persistence.UserDaoInterface;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mysql.jdbc.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LocuintaDao implements LocuintaDaoInterface<Locuinta, String> {


    protected static final Logger LOGGER = Logger.getLogger(UserDao.class.getName());
    private static final String insert = "INSERT INTO `primarie`.`locuinta`(`adresa`,`idUser`)VALUES(?,?);";
    HibernateCon connection = new HibernateCon();


    public List<Locuinta> getAllLocuinte(){
        Connection dbconn = (Connection) ConnectionF.getConnection();
        String sql = "SELECT `locuinta`.`idLocuinta`,`locuinta`.`adresa`,`locuinta`.`idUser`FROM `primarie`.`locuinta`;";
        List<Locuinta> locs = new ArrayList<Locuinta>();
        PreparedStatement findSt = null;
        ResultSet rs = null;
        try {
            findSt = (PreparedStatement) dbconn.prepareStatement(sql);
            rs = findSt.executeQuery(sql);
            while(rs.next())
            {
                    Locuinta loc = new Locuinta(rs.getInt("idUser"), rs.getString("adresa"));
                    locs.add(loc);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return (locs);
    }

    public int deleteLocuinta(int idLoc){
        String sql = "DELETE FROM `primarie`.`locuinta` WHERE `locuinta`.`idLocuinta` = ?";
        Connection dbcon = (Connection) ConnectionF.getConnection();
        PreparedStatement insertUser = null;
        int insertId =  -1;
        try {

            insertUser = (PreparedStatement) dbcon.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            insertUser.setInt(1, idLoc);
            insertUser.executeUpdate();

            ResultSet rs = insertUser.getGeneratedKeys();
            if(rs.next()) {
                insertId= rs.getInt(1);
            }
        }catch(SQLException e) {
            LOGGER.log(Level.WARNING, "LocuintaDAO: delete", e.getMessage());
        } finally { ConnectionF.close(insertUser);
            ConnectionF.close(dbcon);
        }
        return insertId;
    }

    public int insertLocuinta(int idUser, String adresa) {
        Connection dbcon = (Connection) ConnectionF.getConnection();
        PreparedStatement insertUser = null;
        int insertId =  -1;
        try {

            insertUser = (PreparedStatement) dbcon.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            insertUser.setString(1, adresa);
            insertUser.setInt(2, idUser);

            insertUser.executeUpdate();

            ResultSet rs = insertUser.getGeneratedKeys();
            if(rs.next()) {
                insertId= rs.getInt(1);
            }
        }catch(SQLException e) {
            LOGGER.log(Level.WARNING, "LocuintaDAO: insert", e.getMessage());
        } finally { ConnectionF.close(insertUser);
            ConnectionF.close(dbcon);
        }
        return insertId;
    }

    public void persist(Locuinta entity) {
        connection.getCurrentSession().save(entity);
    }

    public void update(Locuinta entity) {
        connection.getCurrentSession().update(entity);
    }

    public Locuinta findById(String id) {
        Locuinta locuinta = (Locuinta) connection.getCurrentSession().get(Locuinta.class, Integer.parseInt(id));
        return locuinta;
    }

    public void delete(Locuinta entity) {
        connection.getCurrentSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    public List<Locuinta> findAll() {
        List<Locuinta> locuinte = (List<Locuinta>) connection.getCurrentSession().createQuery("from Locuinta").list();
        return locuinte;
    }

    public void deleteAll() {
        List<Locuinta> entityList = findAll();
        for (Locuinta entity : entityList) {
            delete(entity);
        }
    }

}
