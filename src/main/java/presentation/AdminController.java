package presentation;

import domain.Document;
import domain.Locuinta;
import domain.Request;
import domain.User;
import infrastructure.*;

import java.util.List;
import java.util.Scanner;

public class AdminController {
    private User user;
    private String type;
    private LocuintaService lc = new LocuintaService();
    private RequestService rs = new RequestService();
    private UserService us = new UserService();
    private DocumentService ds = new DocumentService();
    private LocuintaDao locuintaDao = new LocuintaDao();
    private RequestDao requestDao = new RequestDao();
    private UserDao userDao = new UserDao();
    private DocumentDao documentDao = new DocumentDao();


    public AdminController(User user,String type) {
        this.user = user;
        this.type = type;
    }

    public void menu() {
        System.out.println("Bun venit " + user.getNume());
        boolean stop = false;
        while (stop != true) {
            System.out.println("1 - Vizualizare utilizatori");
            System.out.println("2 - Adaugare document");
            System.out.println("3 - Stergere document");
            System.out.println("4 - Vizualizare requests");
            System.out.println("5 - Accept/Decline request");
            System.out.println("0 - Log out");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            switch (choice) {
                case 0:
                    System.out.println("Logged out with success");
                    stop = true;
                    break;
                case 1:
                    System.out.println("Utilizatori:");
                    List<User> userList;
                    if(type.equals("Hibernate")){
                       userList = us.findAll();
                    }else userList = userDao.getAllUsers();

                    for (User u : userList)
                        if (u.getRole().equals("user"))
                            System.out.println("idUser=" + u.getIdUser() +
                                    ", nume='" + u.getNume() + '\'' +
                                    ", email='" + u.getEmail() + "'");
                    break;
                case 2:
                    int succes = 0;
                    System.out.println("Introduceti tipul documentului: ");
                    String tipDoc = scanner.next();
                    Document doc = new Document(tipDoc);
                    if(type.equals("Hibernate"))
                        ds.persist(doc);
                    else succes = documentDao.insertDocument(doc.getTip());
                    System.out.println("Document adaugat cu succes");
                    break;
                case 3:
                    int succes1 = 0;
                    List<Document> documentList;
                    if(type.equals("Hibernate"))
                         documentList = ds.findAll();
                    else documentList = documentDao.getAllDocuments();
                    for (Document d: documentList)
                        System.out.println(d.getIdDocument() + ". "+ d.getTip());
                    System.out.println("Introduceti id-ul documentului");
                    String idDoc = scanner.next();
                    if (type.equals("Hibernate")){
                        System.out.println(type);
                        ds.delete(idDoc);
                    }
                    else documentDao.deleteDocument(Integer.parseInt(idDoc));
                    System.out.println("Document sters cu succes");
                    break;
                case 4:
                    List<Request> reqList;
                    if(type.equals("Hibernate"))
                        reqList = rs.findAll();
                    else reqList = requestDao.getAllRequests();

                    for(Request r: reqList)
                        System.out.println("idRequest=" + r.getIdRequest() +
                                ", idUser=" + r.getIdUser() +
                                ", idDocument=" + r.getIdDocument() +
                                ", status='" + r.getStatus()+"'"
                        );
                    break;
                case 5:
                    System.out.println("Introduceti id-ul requestului:");
                    String idReq = scanner.next();
                    System.out.println("Aprobat/Refuzat: ");
                    String stats = scanner.next();
                    if(type.equals("Hibernate")){
                        Request r1 = rs.findById(idReq);
                        r1.setStatus(stats);
                        rs.update(r1);
                    }else{
                        requestDao.updateRequest(Integer.parseInt(idReq),stats);
                    }

                    break;
                default:
            }
        }
    }
}
