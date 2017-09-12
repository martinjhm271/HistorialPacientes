package edu.eci.pdsw.samples.dao;



import edu.eci.pdsw.samples.entities.Paciente;
import java.util.List;



/**
 * @author 2106913
 */
public interface DaoPaciente {

    
    public Paciente cargar(int idPaciente,String tipoId) throws PersistenceException;
    
    public Paciente cargar2(int idConsulta) throws PersistenceException;
    
    public long cargarDeudaTotalPaciente(int idPaciente) throws PersistenceException;
    
    public List<Paciente> cargarTodos() throws PersistenceException;
    
    public List<Paciente> cargarTodos2(String nameEps,long deudageneral) throws PersistenceException;
    
    public void guardar(Paciente paciente) throws PersistenceException;
    
    public void actualizar(int idPaciente,int epsId) throws PersistenceException;
    
    
}
