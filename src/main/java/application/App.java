package application;

import domain.User;
import infrastructure.ConnectionFactory;
import infrastructure.UserDao;
import presentation.AdminController;
import presentation.UserController;
import infrastructure.UserService;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Hibernate");
        System.out.println("2. JDBC");
        String type = scanner.next();
        connectionFactory.getConn(type);
        boolean stop = false;
        while (stop != true) {
            System.out.println("1 - Inregistrare");
            System.out.println("2 - Login");

            UserService userService = new UserService();
            UserDao userDao = new UserDao();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Nume: ");
                    Scanner scanner1 = new Scanner(System.in);
                    String name = scanner1.next();
                    System.out.println("\n Adresa: ");
                    String adresa = scanner1.next();
                    System.out.println("\n Telefon: ");
                    String telefon = scanner1.next();
                    System.out.println("\n Email: ");
                    String email = scanner1.next();
                    System.out.println("\n Parola: ");
                    String parola = scanner1.next();
                    User u1 = new User(name, adresa, telefon, email, parola, "user");
                    if (type.equals("Hibernate"))
                        userService.persist(u1);
                    else userDao.insertUser(u1);
                    break;

                case 2:
                    Scanner scanner2 = new Scanner(System.in);
                    System.out.println("\n Email: ");
                    String email1 = scanner2.next();
                    System.out.println("\n Parola: ");
                    String parola1 = scanner2.next();
                    if (type.equals("Hibernate")) {
                        User user = userService.login(email1);
                        if (user.getRole().equals("user")) {
                            UserController uc = new UserController(user, "Hibernate");
                            uc.menu();
                        } else {
                            AdminController ac = new AdminController(user, "Hibernate");
                            ac.menu();
                        }
                    } else {
                        User user = userDao.findUser(email1, parola1);
                        if (user.getRole().equals("user")) {
                            UserController uc = new UserController(user, "JDBC");
                            uc.menu();
                        } else {
                            AdminController ac = new AdminController(user, "JDBC");
                            ac.menu();
                        }
                    }

                    break;
                default:
            }
        }
    }
}