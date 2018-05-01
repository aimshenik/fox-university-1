package net.imshenik.university;

import org.junit.Test;
import net.imshenik.university.dao.postgres.*;

public class TestDBConnection {
    DBConnection connection = new DBConnection();
    
    @Test
    public void connect() {
        connection.connect();
    }
    
}
