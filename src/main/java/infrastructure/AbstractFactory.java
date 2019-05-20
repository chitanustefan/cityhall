package infrastructure;

import infrastructure.connection.Conn;

public abstract class AbstractFactory {
    abstract Conn getConn(String connType);
}
