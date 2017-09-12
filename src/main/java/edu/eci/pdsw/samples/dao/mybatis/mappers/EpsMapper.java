package edu.eci.pdsw.samples.dao.mybatis.mappers;




import edu.eci.pdsw.samples.entities.Eps;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author 2106913
 */
public interface EpsMapper {
    
    
    public List<Eps> obtenerEpss();
   
    public Eps obtenerEps(@Param("id")int idEps);
    
    public Eps obtenerEps2(@Param("nombre")String nombre);
    
    public void insertarEps(@Param("nombre")String nombre,@Param("nit")String nit);
    
    public void actualizarEps(@Param("id")int idEps,@Param("nombre")String nombre,@Param("nit")String nit);

    
    
}
