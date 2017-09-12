package edu.eci.pdsw.samples.dao.mybatis.mappers;



import edu.eci.pdsw.samples.entities.Paciente;
import java.util.Date;

import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author 2106913
 */
public interface PacienteMapper {
    
    
    public Paciente obtenerPacienteConConsulta(@Param("id")int idConsulta); 
    
    public Paciente obtenerPaciente(@Param("id")int idPaciente,@Param("tipo_id")String tipoId); 
    
    public long obtenerDeudaPaciente(@Param("id")int idPaciente);
    
    public void insertarPaciente(@Param("tipo_id")String tipoId,@Param("nombre")String nombre,@Param("fechaNacimiento")Date fechaNacimiento,@Param("eps")int epsId);
    
    public List<Paciente> obtenerPacientes();
    
    public List<Paciente> obtenerPacientes2(@Param("nameEps")String nameEps,@Param("deuda")long deuda);
    
    public void actualizarPaciente(@Param("id")int idPaciente,@Param("eps")int epsId);

}
