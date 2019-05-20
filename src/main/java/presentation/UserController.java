package presentation;


import domain.Locuinta;
import domain.Request;
import domain.User;
import infrastructure.LocuintaDao;
import infrastructure.LocuintaService;
import infrastructure.RequestDao;
import infrastructure.RequestService;

import java.util.List;
import java.util.Scanner;

public class UserController {

    private User user;
    private String type;
    private LocuintaService lc = new LocuintaService();
    private RequestService rs = new RequestService();
    private LocuintaDao locuintaDao = new LocuintaDao();
    private RequestDao requestDao = new RequestDao();

    public UserController(User user, String type){
        this.user = user;
        this.type = type;
    }

    public void menu(){
        System.out.println("Bun venit " + user.getNume());
        boolean stop = false;
        while(stop != true)
        {
            System.out.println("1 - Add locuinta");
            System.out.println("2 - Remove locuinta");
            System.out.println("3 - Vizualizare locuinte");
            System.out.println("4 - Vizualizare request-uri");
            System.out.println("5 - Add request");
            System.out.println("0 - Log out");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            switch (choice) {
                case 0:
                    System.out.println("Logged out with success");
                    stop = true;
                    break;
                case 1:
                    System.out.println("Adresa locuinta:");
                    //Scanner scanner1 = new Scanner(System.in);
                    String adresa = scanner.next();
                    if (this.type.equals("Hibernate")){
                        Locuinta l1 = new Locuinta(this.user.getIdUser(),adresa);
                        lc.persist(l1);
                    }else {
                        int succes = locuintaDao.insertLocuinta(this.user.getIdUser(),adresa);
                    }

                    System.out.println("Locuinta adaugata cu succes");
                    break;
                case 2:
                    System.out.println("ID Locuinta: ");
                    String idLoc = scanner.next();
                    if(type.equals("Hibernate")){
                        lc.delete(idLoc);
                    }else{
                        locuintaDao.deleteLocuinta(Integer.parseInt(idLoc));
                    }
                    System.out.println("Locuinta stearsa cu succes");
                    break;
                case 3:
                    List<Locuinta> listLc = null;
                    if(type.equals("Hibernate")){
                        listLc = lc.findAll();
                    }
                    else{
                        listLc = locuintaDao.getAllLocuinte();
                    }
                    System.out.println("Locuintele tale: ");
                    for(Locuinta l: listLc)
                        if(l.getIdUser() == this.user.getIdUser())
                            System.out.println("Adresa: " + l.getAdresa()+ "\n");
                     break;
                case 4:
                    List<Request> requestList;
                    if (type.equals("Hibernate"))
                        requestList = rs.findAll();
                    else requestList = requestDao.getAllRequests();

                    System.out.println("Requesturile tale: ");
                    for(Request r: requestList)
                        if(r.getIdUser() == this.user.getIdUser())
                            System.out.println("idRequest=" + r.getIdRequest() +
                                    ", idUser=" + r.getIdUser() +
                                    ", idDocument=" + r.getIdDocument() +
                                    ", status='" + r.getStatus()+"'"
                                    );
                    break;
                case 5:
                    System.out.println("Adauga un nou request");
                    System.out.println("1. Impozit");
                    System.out.println("2. Titlu proprietate");
                    System.out.println("3. Chitanta");
                    System.out.println("Introduceti id-ul tipul dorit:");
                    String tipDoc = scanner.next();
                    Request req = new Request(this.user.getIdUser(), Integer.parseInt(tipDoc), "In Asteptare" );
                    int nr = 0;
                    List<Request> requestList1 = rs.findAll();
                    for (Request r : requestList1)
                        if (this.user.getIdUser() == r.getIdUser() && r.getIdDocument() == Integer.parseInt(tipDoc))
                                nr++;
                    if(nr<=3){
                        if(type.equals("Hibernate")){
                            rs.persist(req);
                        }else
                        {
                            requestDao.addRequest(req);
                        }
                        System.out.println("Request adaugata cu succes");
                    }else{
                        System.out.println("Puteti face maxim 3 requesturi");
                    }


                    break;
                default:
            }
        }
    }
}
