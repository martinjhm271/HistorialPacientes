package edu.eci.pdsw.samples.dao.mybatis;









import edu.eci.pdsw.samples.entities.Eps;
import java.util.List;
import com.google.inject.Inject;
import edu.eci.pdsw.samples.dao.DaoEps;
import edu.eci.pdsw.samples.dao.PersistenceException;
import edu.eci.pdsw.samples.dao.mybatis.mappers.EpsMapper;


/**
 *
 * @author 2106913
 */
public class MyBatisDAOEps implements DaoEps{

    
     
    @Inject
    private EpsMapper EpsMapper;
    
    @Override
    public Eps cargar(int id) throws PersistenceException {
        return EpsMapper.obtenerEps(id);
    }

    @Override
    public List<Eps> cargarTodos() throws PersistenceException {
        return EpsMapper.obtenerEpss();
    }

    @Override
    public void guardar(Eps eps) throws PersistenceException {
        EpsMapper.insertarEps(eps.getNombre(),eps.getNit());
    }

    @Override
    public void actualizar(int id,String nombre,String nit) throws PersistenceException {
        EpsMapper.actualizarEps(id,nombre, nit);
    }

    @Override
    public Eps cargar2(String nombre) throws PersistenceException {
        return EpsMapper.obtenerEps2(nombre);
    }

        
    

  

   
    
}
