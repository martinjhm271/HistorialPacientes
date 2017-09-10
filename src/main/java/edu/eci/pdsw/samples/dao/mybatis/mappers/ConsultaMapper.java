package edu.eci.pdsw.samples.dao.mybatis.mappers;




import edu.eci.pdsw.samples.entities.Consulta;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author 2106913
 */
public interface ConsultaMapper {
    
    
    public List<Consulta> obtenerConsultas();
    
    public List<Consulta> obtenerConsultas2(@Param("idPaciente")int id);
    
    public List<Consulta> obtenerConsultas3(@Param("nameEps")String nameEps);
    
    
    public List<Consulta> obtenerConsultas4(@Param("nameEps")String nameEps,@Param("f1")Date fechainicio,@Param("f2")Date fechafin);
    
    public Consulta obtenerConsulta(@Param("id")int id);
    
    public void insertarConsulta(@Param("fechayHora")Date fechayHora,@Param("resumen")String resumen,@Param("costo")long costo,@Param("idPaciente")int idPaciente,@Param("tipo_id")String tipo_id);
    
    public void actualizarConsulta(@Param("idPaciente")int idPaciente,@Param("id")int id,@Param("costo")long costo);
    
 
    
}
