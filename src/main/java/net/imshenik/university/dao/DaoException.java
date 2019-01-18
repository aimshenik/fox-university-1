package net.imshenik.university.dao;

public class DaoException extends RuntimeException {
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
