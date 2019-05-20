package infrastructure;

import java.util.List;
import domain.User;
import infrastructure.UserDao;
import infrastructure.connection.HibernateCon;

import javax.transaction.Transactional;


@Transactional
public class UserService {

    private static UserDao userDao;


    public UserService() {
        userDao = new UserDao();
    }

    public void persist(User entity) {
        userDao.connection.openCurrentSessionwithTransaction();
        userDao.persist(entity);
        userDao.connection.closeCurrentSessionwithTransaction();
    }

    public void update(User entity) {
        userDao.connection.openCurrentSessionwithTransaction();
        userDao.update(entity);
        userDao.connection.closeCurrentSessionwithTransaction();
    }

    public User findById(String id) {
        userDao.connection.openCurrentSession();
        User user = userDao.findById(id);
        userDao.connection.closeCurrentSession();
        return user;
    }

    public User login(String email) {
        List<User> list = findAll();
        User u1 = new User();
        boolean notfound=true;
        for (User u : list){
            if (u.getEmail().equals(email)){
                u1 = u;
                notfound = false;
            }
        }
        if (!notfound)
            System.out.println("Logare cu succes");
        else System.out.println("Email invalid");
        return u1;
    }

    public void delete(String id) {
        userDao.connection.openCurrentSessionwithTransaction();
        User user = userDao.findById(id);
        userDao.delete(user);
        userDao.connection.closeCurrentSessionwithTransaction();
    }

    public List<User> findAll() {
        userDao.connection.openCurrentSession();
        List<User> users = userDao.findAll();
        userDao.connection.closeCurrentSession();
        return users;
    }

    public void deleteAll() {
        userDao.connection.openCurrentSessionwithTransaction();
        userDao.deleteAll();
        userDao.connection.closeCurrentSessionwithTransaction();
    }

    public UserDao userDao() {
        return userDao;
    }
}
