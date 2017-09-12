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
    private PacienteMapper pacienteMapper; 
    
    @Override
    public Paciente cargar(int idPaciente,String tipoId) throws PersistenceException {
        return pacienteMapper.obtenerPaciente(idPaciente,tipoId);
    }

    @Override
    public void guardar(Paciente paciente) throws PersistenceException {
        pacienteMapper.insertarPaciente( paciente.getTipoId(),paciente.getNombre(), paciente.getFechaNacimiento(), paciente.getEps().getId());
    }

    @Override
    public void actualizar(int idPaciente,int epsId) throws PersistenceException {
        pacienteMapper.actualizarPaciente(idPaciente,epsId);
    }

    @Override
    public List<Paciente> cargarTodos() throws PersistenceException {
        return pacienteMapper.obtenerPacientes();
    }

    @Override
    public Paciente cargar2(int consultaId) throws PersistenceException {
        return pacienteMapper.obtenerPacienteConConsulta(consultaId);
    }

    @Override
    public long cargarDeudaTotalPaciente(int idPaciente) throws PersistenceException {
        return pacienteMapper.obtenerDeudaPaciente(idPaciente);
    }

    @Override
    public List<Paciente> cargarTodos2(String nameEps, long deudageneral) throws PersistenceException {
        return pacienteMapper.obtenerPacientes2(nameEps, deudageneral);
    }
    
    
    
    
    
}
