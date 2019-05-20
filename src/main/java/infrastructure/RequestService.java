package infrastructure;

import domain.Request;
import infrastructure.connection.HibernateCon;

import java.util.List;

public class RequestService {

    private static RequestDao requestDao;


    public RequestService() {
        requestDao = new RequestDao();
    }

    public void persist(Request entity) {
        requestDao.connection.openCurrentSessionwithTransaction();
        requestDao.persist(entity);
        requestDao.connection.closeCurrentSessionwithTransaction();
    }

    public void update(Request entity) {
        requestDao.connection.openCurrentSessionwithTransaction();
        requestDao.update(entity);
        requestDao.connection.closeCurrentSessionwithTransaction();
    }

    public Request findById(String id) {
        requestDao.connection.openCurrentSession();
        Request req = requestDao.findById(id);
        requestDao.connection.closeCurrentSession();
        return req;
    }

    public void delete(String id) {
        requestDao.connection.openCurrentSessionwithTransaction();
        Request req = requestDao.findById(id);
        requestDao.delete(req);
        requestDao.connection.closeCurrentSessionwithTransaction();
    }

    public List<Request> findAll() {
        requestDao.connection.openCurrentSession();
        List<Request> reqs = requestDao.findAll();
        requestDao.connection.closeCurrentSession();
        return reqs;
    }

    public void deleteAll() {
        requestDao.connection.openCurrentSessionwithTransaction();
        requestDao.deleteAll();
        requestDao.connection.closeCurrentSessionwithTransaction();
    }

    public RequestDao RequestDao() {
        return requestDao;
    }
}
