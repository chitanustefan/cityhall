package infrastructure;

import java.util.List;
import domain.Locuinta;
import infrastructure.connection.HibernateCon;

public class LocuintaService {

    private static LocuintaDao locuintaDao;


    public LocuintaService() {
        locuintaDao = new LocuintaDao();
    }

    public void persist(Locuinta entity) {
        locuintaDao.connection.openCurrentSessionwithTransaction();
        locuintaDao.persist(entity);
        locuintaDao.connection.closeCurrentSessionwithTransaction();
    }

    public void update(Locuinta entity) {
        locuintaDao.connection.openCurrentSessionwithTransaction();
        locuintaDao.update(entity);
        locuintaDao.connection.closeCurrentSessionwithTransaction();
    }

    public Locuinta findById(String id) {
        locuintaDao.connection.openCurrentSession();
        Locuinta locuinta = locuintaDao.findById(id);
        locuintaDao.connection.closeCurrentSession();
        return locuinta;
    }

    public void delete(String id) {
        locuintaDao.connection.openCurrentSessionwithTransaction();
        Locuinta loc = locuintaDao.findById(id);
        locuintaDao.delete(loc);
        locuintaDao.connection.closeCurrentSessionwithTransaction();
    }

    public List<Locuinta> findAll() {
        locuintaDao.connection.openCurrentSession();
        List<Locuinta> locuinte = locuintaDao.findAll();
        locuintaDao.connection.closeCurrentSession();
        return locuinte;
    }

    public void deleteAll() {
        locuintaDao.connection.openCurrentSessionwithTransaction();
        locuintaDao.deleteAll();
        locuintaDao.connection.closeCurrentSessionwithTransaction();
    }

    public LocuintaDao locuintaDao() {
        return locuintaDao;
    }
}
