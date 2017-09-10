package edu.eci.pdsw.samples.dao.mybatis;


import java.util.List;
import com.google.inject.Inject;
import edu.eci.pdsw.samples.dao.DaoPaciente;
import edu.eci.pdsw.samples.dao.PersistenceException;
import edu.eci.pdsw.samples.dao.mybatis.mappers.PacienteMapper;

import edu.eci.pdsw.samples.entities.Paciente;

/**
 *
 * @author 2106913
 */
public class MyBatisDAOPaciente implements DaoPaciente{

    
    
    @Inject
    private PacienteMapper PacienteMapper; 
    
    @Override
    public Paciente cargar(int id,String tipo_id) throws PersistenceException {
        return PacienteMapper.obtenerPaciente(id,tipo_id);
    }

    @Override
    public void guardar(Paciente p) throws PersistenceException {
        PacienteMapper.insertarPaciente( p.getTipo_id(),p.getNombre(), p.getFechaNacimiento(), p.getEps().getId());
    }

    @Override
    public void actualizar(int id,int eps_id) throws PersistenceException {
        PacienteMapper.actualizarPaciente(id,eps_id);
    }

    @Override
    public List<Paciente> cargarTodos() throws PersistenceException {
        return PacienteMapper.obtenerPacientes();
    }

    @Override
    public Paciente cargar2(int consulta_id) throws PersistenceException {
        return PacienteMapper.obtenerPacienteConConsulta(consulta_id);
    }

    @Override
    public long cargarDeudaTotalPaciente(int idPaciente) throws PersistenceException {
        return PacienteMapper.obtenerDeudaPaciente(idPaciente);
    }

    @Override
    public List<Paciente> cargarTodos2(String nameEps, long deudageneral) throws PersistenceException {
        return PacienteMapper.obtenerPacientes2(nameEps, deudageneral);
    }
    
    
    
    
    
}
