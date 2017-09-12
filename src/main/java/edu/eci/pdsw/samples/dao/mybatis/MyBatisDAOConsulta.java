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
    public Consulta cargar(int idConsulta) throws PersistenceException {
        return consultaMapper.obtenerConsulta(idConsulta);
    }

    @Override
    public List<Consulta> cargarTodos() throws PersistenceException {
        return consultaMapper.obtenerConsultas();
    }

    @Override
    public void guardar(Consulta consulta,int idpaciente,String tipoId) throws PersistenceException {
         consultaMapper.insertarConsulta(consulta.getFechayHora(),consulta.getResumen(), consulta.getCosto(),idpaciente,tipoId);
    }

    @Override
    public void actualizar (int idPaciente,int idConsulta,long costo) throws PersistenceException {
        consultaMapper.actualizarConsulta(idPaciente,idConsulta, costo);
    }

    
    @Override
    public List<Consulta> cargarTodos2(int idPaciente) throws PersistenceException {
        return consultaMapper.obtenerConsultas2(idPaciente);
    }

  

    @Override
    public List<Consulta> cargarTodos3(String nombreEps) throws PersistenceException {
        return consultaMapper.obtenerConsultas3(nombreEps);
    }


    @Override
    public List<Consulta> cargarTodos4(String nombreEps, Date inicio, Date fin) throws PersistenceException {
         return consultaMapper.obtenerConsultas4(nombreEps, inicio, fin);
    }

    @Override
    public long cargarDeudaPorFecha(String nombreEps, Date inicio, Date fin) {
        return consultaMapper.obtenerCostoEpsPorFecha(nombreEps, inicio, fin);
    }

  

   
    
}
