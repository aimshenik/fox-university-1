package net.imshenik.university.dao;

public class DAOException extends Exception {
    private static final long serialVersionUID = 6703482227622346022L;
    
    public DAOException() {
        super();
    }
    
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public DAOException(String message) {
        super(message);
    }
}
