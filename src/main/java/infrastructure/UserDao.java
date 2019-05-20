package infrastructure;

import domain.Locuinta;
import domain.User;

import infrastructure.connection.ConnectionF;
import infrastructure.connection.HibernateCon;
import infrastructure.persistence.UserDaoInterface;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mysql.jdbc.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao implements UserDaoInterface<User, String> {

    protected static final Logger LOGGER = Logger.getLogger(UserDao.class.getName());
    private static final String insert = "INSERT INTO `primarie`.`user`(`adresa`,`email`,`nume`,`parola`,`telefon`,`role`)VALUES(?,?,?,?,?,?);";
    HibernateCon connection = new HibernateCon();


    public List<User> getAllUsers(){
        Connection dbconn = (Connection) ConnectionF.getConnection();
        String sql = "SELECT `user`.`idUser`,`user`.`adresa`,`user`.`email`,`user`.`nume`,`user`.`parola`,`user`.`telefon`,`user`.`role`FROM `primarie`.`user`;";
        List<User> users = new ArrayList<User>();
        PreparedStatement findSt = null;
        ResultSet rs = null;
        try {
            findSt = (PreparedStatement) dbconn.prepareStatement(sql);
            rs = findSt.executeQuery(sql);
            while(rs.next())
            {
                User user = new User(rs.getInt("idUser"),rs.getString("nume"), rs.getString("adresa"),rs.getString("telefon")
                        ,rs.getString("email"),rs.getString("parola"),rs.getString("role"));
                users.add(user);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return (users);
    }

    public static User findUser(String email, String parola) {
        String find = "SELECT * FROM `primarie`.`user` WHERE email = '"+email+"' and parola ='"+parola+"' ;";
        Connection dbconn = (Connection) ConnectionF.getConnection();
        PreparedStatement findSt = null;
        ResultSet rs = null;
        User user = new User();
        //String permis=" ";
        try {
            findSt = (PreparedStatement) dbconn.prepareStatement(find);
            rs = findSt.executeQuery(find);
            if(rs.next())
            {
                if(email.equals(rs.getString("email")) && parola.equals(rs.getString("parola")))
                {
                    System.out.println(parola);
                    user.setEmail(rs.getString("email"));
                    user.setIdUser(rs.getInt("idUser"));
                    user.setAdresa(rs.getString("adresa"));
                    user.setTelefon(rs.getString("telefon"));
                    user.setNume(rs.getString("nume"));
                    user.setRole(rs.getString("role"));
                    user.setParola(rs.getString("parola"));
                }
            }else System.out.println("Email sau parola invalide");
        }catch(SQLException e) {
            LOGGER.log(Level.WARNING,"UserDAO: find " + e.getMessage());
        } finally {
            ConnectionF.close(rs);
            ConnectionF.close(findSt);
            ConnectionF.close(dbconn);
        }
        return user;
    }

    public int insertUser(User user) {
        Connection dbcon = (Connection) ConnectionF.getConnection();
        PreparedStatement insertUser = null;
        int insertId =  -1;
        try {

            insertUser = (PreparedStatement) dbcon.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            insertUser.setString(1, user.getAdresa());
            insertUser.setString(2, user.getEmail());
            insertUser.setString(3, user.getNume());
            insertUser.setString(4, user.getParola());
            insertUser.setString(5, user.getTelefon());
            insertUser.setString(6, user.getRole());
            insertUser.executeUpdate();

            ResultSet rs = insertUser.getGeneratedKeys();
        if(rs.next()) {
            insertId= rs.getInt(1);
        }
    }catch(SQLException e) {
        LOGGER.log(Level.WARNING, "UserDAO: insert", e.getMessage());
    } finally { ConnectionF.close(insertUser);
        ConnectionF.close(dbcon);
    }
        return insertId;
    }

    public void persist(User entity) {
        connection.getCurrentSession().save(entity);
    }

    public void update(User entity) {
        connection.getCurrentSession().update(entity);
    }

    public User findById(String id) {
        User user = (User) connection.getCurrentSession().get(User.class, id);
        return user;
    }

    public void delete(User entity) {
        connection.getCurrentSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        List<User> users = (List<User>) connection.getCurrentSession().createQuery("from User").list();
        return users;
    }

    public void deleteAll() {
        List<User> entityList = findAll();
        for (User entity : entityList) {
            delete(entity);
        }
    }

}
