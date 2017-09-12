

package edu.eci.pdsw.samples.dao;



import edu.eci.pdsw.samples.entities.Consulta;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 2106913
 */
public interface DaoConsulta {
 
    public Consulta cargar(int idConsulta) throws PersistenceException;
    
    public List<Consulta> cargarTodos() throws PersistenceException;
    
    public List<Consulta> cargarTodos2(int idPaciente) throws PersistenceException;
    
    public List<Consulta> cargarTodos3(String nombreEps) throws PersistenceException;
    
    public List<Consulta> cargarTodos4(String nombreEps,Date inicio,Date fin) throws PersistenceException;
    
    public long cargarDeudaPorFecha(String nombreEps,Date inicio,Date fin) throws PersistenceException;
    
    public void guardar(Consulta consulta,int idPaciente,String tipoId) throws PersistenceException;
    
    public void actualizar(int idPaciente,int idConsulta,long costo) throws PersistenceException;
    
  
    
}
