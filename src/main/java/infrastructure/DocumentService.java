package infrastructure;

import domain.Document;
import infrastructure.connection.HibernateCon;

import java.util.List;

public class DocumentService {

    private static DocumentDao documentDao;

    public DocumentService() {
        documentDao = new DocumentDao();
    }

    public void persist(Document entity) {
        documentDao.connection.openCurrentSessionwithTransaction();
        documentDao.persist(entity);
        documentDao.connection.closeCurrentSessionwithTransaction();
    }

    public void update(Document entity) {
        documentDao.connection.openCurrentSessionwithTransaction();
        documentDao.update(entity);
        documentDao.connection.closeCurrentSessionwithTransaction();

    }

    public Document findById(String id) {
        documentDao.connection.openCurrentSession();
        Document doc = documentDao.findById(id);
        documentDao.connection.closeCurrentSession();
        return doc;
    }

    public void delete(String id) {
        documentDao.connection.openCurrentSessionwithTransaction();
        Document doc = documentDao.findById(id);
        documentDao.delete(doc);
        documentDao.connection.closeCurrentSessionwithTransaction();
    }

    public List<Document> findAll() {
        documentDao.connection.openCurrentSession();
        List<Document> docs = documentDao.findAll();
        documentDao.connection.closeCurrentSession();
        return docs;
    }

    public void deleteAll() {
        documentDao.connection.openCurrentSessionwithTransaction();
        documentDao.deleteAll();
        documentDao.connection.closeCurrentSessionwithTransaction();
    }

    public DocumentDao DocumentDao() {
        return documentDao;
    }
}
