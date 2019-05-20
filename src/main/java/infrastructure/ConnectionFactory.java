package infrastructure;

import infrastructure.connection.Conn;
import infrastructure.connection.ConnectionF;
import infrastructure.connection.HibernateCon;

public class ConnectionFactory extends AbstractFactory {

    @Override
    public Conn getConn(String connType){
        if(connType.equalsIgnoreCase("Hibernate"))
            return new HibernateCon();
        else return new ConnectionF();
    }
}
