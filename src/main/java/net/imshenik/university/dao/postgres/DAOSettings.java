package net.imshenik.university.dao.postgres;

class DAOSettings {
    private static final String DRIVER   = "org.postgresql.Driver";
    private static final String URL      = "jdbc:postgresql://localhost:5432/university";
    private static final String LOGIN    = "andrey";
    private static final String PASSWORD = "1234321";
    
    static final String getDriver() {
        return DRIVER;
    }
    
    static final String getUrl() {
        return URL;
    }
    
    static final String getLogin() {
        return LOGIN;
    }
    
    static final String getPassword() {
        return PASSWORD;
    }
}
