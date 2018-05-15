package net.imshenik.university.dao;

public class DaoException extends Exception {
    private static final long serialVersionUID = 6703482227622346022L;
    
    public DaoException() {
        super();
    }
    
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public DaoException(String message) {
        super(message);
    }
}
