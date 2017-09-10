package edu.eci.pdsw.samples.dao.mybatis;







import java.util.Date;
import java.util.List;
import com.google.inject.Inject;
import edu.eci.pdsw.samples.dao.DaoConsulta;
import edu.eci.pdsw.samples.dao.PersistenceException;
import edu.eci.pdsw.samples.dao.mybatis.mappers.ConsultaMapper;

import edu.eci.pdsw.samples.entities.Consulta;

/**
 *
 * @author 2106913
 */
public class MyBatisDAOConsulta implements DaoConsulta{

    
    
    @Inject
    private ConsultaMapper consultaMapper;
    
    @Override
    public Consulta cargar(int id) throws PersistenceException {
        return consultaMapper.obtenerConsulta(id);
    }

    @Override
    public List<Consulta> cargarTodos() throws PersistenceException {
        return consultaMapper.obtenerConsultas();
    }

    @Override
    public void guardar(Consulta c,int idpaciente,String tipo_id) throws PersistenceException {
         consultaMapper.insertarConsulta(c.getFechayHora(),c.getResumen(), c.getCosto(),idpaciente,tipo_id);
    }

    @Override
    public void actualizar (int idPaciente,int id,long costo) throws PersistenceException {
        consultaMapper.actualizarConsulta(idPaciente,id, costo);
    }

    
    @Override
    public List<Consulta> cargarTodos2(int id_paciente) throws PersistenceException {
        return consultaMapper.obtenerConsultas2(id_paciente);
    }

  

    @Override
    public List<Consulta> cargarTodos3(String nombreEps) throws PersistenceException {
        return consultaMapper.obtenerConsultas3(nombreEps);
    }


    @Override
    public List<Consulta> cargarTodos4(String nombreEps, Date inicio, Date fin) throws PersistenceException {
         return consultaMapper.obtenerConsultas4(nombreEps, inicio, fin);
    }

  

   
    
}
