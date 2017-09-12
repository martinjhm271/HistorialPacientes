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
    private EpsMapper epsMapper;
    
    @Override
    public Eps cargar(int idEps) throws PersistenceException {
        return epsMapper.obtenerEps(idEps);
    }

    @Override
    public List<Eps> cargarTodos() throws PersistenceException {
        return epsMapper.obtenerEpss();
    }

    @Override
    public void guardar(Eps eps) throws PersistenceException {
        epsMapper.insertarEps(eps.getNombre(),eps.getNit());
    }

    @Override
    public void actualizar(int idEps,String nombre,String nit) throws PersistenceException {
        epsMapper.actualizarEps(idEps,nombre, nit);
    }

    @Override
    public Eps cargar2(String nombre) throws PersistenceException {
        return epsMapper.obtenerEps2(nombre);
    }

        
    

  

   
    
}
