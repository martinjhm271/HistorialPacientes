/*
 * Copyright (C) 2015 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package  tests;


import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class ConsultasTest {
    
    public ConsultasTest() {
    }
    
    @Before
    public void setUp() {
    }
    @After 
    public void clearDB() throws SQLException, Exception{
        //java.sql.Statement stmt = getConnection().createStatement();
        //stmt.execute("delete from Paciente");
        //stmt.execute("delete from Eps");
        //stmt.execute("delete from Consulta");
        //stmt.executeBatch();
        //stmt.close();
    }
    
    private java.sql.Connection getConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:h2:file:./target/db/testdb;MODE=PostgreSQL", "anonymous", "");        
    }
    
    
    @Test
    public void registroPacienteTest() throws SQLException{
        //java.sql.Statement stmt=getConnection().createStatement();  
        //stmt.execute("INSERT INTO Posgrado(id, nombre,creditos )  VALUES(1, 'Economias',100 );");
        //ServiciosReporte report=ServiciosReporteFactory.getInstance().getServiciosReporteForTesting();
        
    }
    
    
}
