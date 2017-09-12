package edu.eci.pdsw.samples.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.eci.pdsw.samples.dao.DaoConsulta;
import edu.eci.pdsw.samples.dao.DaoEps;
import edu.eci.pdsw.samples.dao.DaoPaciente;
import edu.eci.pdsw.samples.dao.PersistenceException;




import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;


import edu.eci.pdsw.samples.services.ExcepcionServiciosPacientes;

import edu.eci.pdsw.samples.services.ServiciosPacientes;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mybatis.guice.transactional.Transactional;

/**
 * 
 * @author 2106913
 */
@Singleton
public class ServiciosPacientesGuiceMybatis implements ServiciosPacientes {
    
    @Inject
    DaoEps daoEps;
    
    @Inject 
    DaoPaciente daoPaciente;
    
    @Inject 
    DaoConsulta daoConsulta;
    

    public ServiciosPacientesGuiceMybatis() {  
    }
    
  

    @Override
    public Paciente consultarPaciente(int idPaciente, String tipoid) throws ExcepcionServiciosPacientes {
        Paciente  paciente =null;
        try {
            paciente=daoPaciente.cargar(idPaciente,tipoid);
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosPacientesGuiceMybatis.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paciente;
    }

    @Override
    public List<Paciente> consultarPacientes() throws ExcepcionServiciosPacientes {
        List<Paciente>  temp =new ArrayList<>();
        try {
            temp=daoPaciente.cargarTodos();
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosPacientesGuiceMybatis.class.getName()).log(Level.SEVERE, null, ex);
        }
        return temp;
    }
    
    @Transactional
    @Override
    public void registrarNuevoPaciente(Paciente paciente) throws ExcepcionServiciosPacientes {
        try {
            daoEps.guardar(paciente.getEps());
            int ideps=daoEps.cargar2(paciente.getEps().getNombre()).getId();
            paciente.getEps().setId(ideps);
            daoPaciente.guardar(paciente);
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosPacientesGuiceMybatis.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
    
    @Transactional
    @Override
    public void agregarConsultaPaciente(int idPaciente, String tipoid, Consulta consulta) throws ExcepcionServiciosPacientes {
         try {
            daoConsulta.guardar(consulta,idPaciente,tipoid);
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosPacientesGuiceMybatis.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    @Transactional
    @Override
    public void pagarAbonarConsulta(int idPaciente, String tipoid, int idConsulta, long pagoabono) throws ExcepcionServiciosPacientes {
        try {
            daoConsulta.actualizar(idPaciente, idConsulta,  pagoabono);
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosPacientesGuiceMybatis.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }

   

    

    @Override
    public List<Consulta> obtenerConsultasEps(String nameEps) throws ExcepcionServiciosPacientes {
        List<Consulta>  temp =new ArrayList<>();
        try {
            temp=daoConsulta.cargarTodos3(nameEps);
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosPacientesGuiceMybatis.class.getName()).log(Level.SEVERE, null, ex);
        }
        return temp;
    }

   

    @Override
    public Paciente consultarPacienteConConsulta(int idConsulta) throws ExcepcionServiciosPacientes {
        Paciente  paciente =null;
        try {
            paciente=daoPaciente.cargar2(idConsulta);
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosPacientesGuiceMybatis.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paciente;
    }

    @Override
    public List<Consulta> consultarConsultaPaciente(int idPaciente, String tipoid) throws ExcepcionServiciosPacientes {
        List<Consulta>  temp =new ArrayList<>();
        try {
            temp=daoConsulta.cargarTodos2(idPaciente);
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosPacientesGuiceMybatis.class.getName()).log(Level.SEVERE, null, ex);
        }
        return temp;
    }

    @Override
    public List<Paciente> consultarPacienteMoroso(String nameEps, long deudaGeneral) throws ExcepcionServiciosPacientes {
        List<Paciente>  temp =new ArrayList<>();
        try {
            temp=daoPaciente.cargarTodos2(nameEps, deudaGeneral);
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosPacientesGuiceMybatis.class.getName()).log(Level.SEVERE, null, ex);
        }
        return temp;
    }


    @Override
    public List<Consulta> obtenerConsultasEpsPorFecha(String nameEps, java.util.Date fechaInicio, java.util.Date fechaFin) throws ExcepcionServiciosPacientes {
         List<Consulta>  temp =new ArrayList<>();
        try {
            temp=daoConsulta.cargarTodos4(nameEps, fechaInicio, fechaFin);
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosPacientesGuiceMybatis.class.getName()).log(Level.SEVERE, null, ex);
        }
        return temp;
        
    }

    @Override
    public long obtenerCostoEpsPorFecha(String nameEps, java.util.Date fechaInicio, java.util.Date fechaFin) throws ExcepcionServiciosPacientes {
         long deuda =0;
        try {
            deuda=daoConsulta.cargarDeudaPorFecha(nameEps, fechaInicio, fechaFin);
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosPacientesGuiceMybatis.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deuda;
    }
   
    
}
