package edu.eci.pdsw.samples.dao;



import edu.eci.pdsw.samples.entities.Eps;
import java.util.List;

/**
 * @author 2106913
 */
public interface DaoEps {
 
    public Eps cargar(int id) throws PersistenceException;
    
    public Eps cargar2(String nombre) throws PersistenceException;
    
    public List<Eps> cargarTodos() throws PersistenceException;
    
    public void guardar(Eps i) throws PersistenceException;
    
    public void actualizar(int id,String nombre,String nit) throws PersistenceException;
    
    
}
