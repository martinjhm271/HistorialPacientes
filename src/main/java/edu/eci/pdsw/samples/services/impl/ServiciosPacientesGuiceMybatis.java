package edu.eci.pdsw.samples.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.eci.pdsw.samples.dao.DaoConsulta;
import edu.eci.pdsw.samples.dao.DaoEps;
import edu.eci.pdsw.samples.dao.DaoPaciente;
import edu.eci.pdsw.samples.dao.PersistenceException;




import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Eps;

import edu.eci.pdsw.samples.entities.Paciente;


import edu.eci.pdsw.samples.services.ExcepcionServiciosPacientes;

import edu.eci.pdsw.samples.services.ServiciosPacientes;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    

    public ServiciosPacientesGuiceMybatis() {  
    }
    
    @Inject
    DaoEps daoEps;
    
    @Inject 
    DaoPaciente daoPaciente;
    
    @Inject 
    DaoConsulta daoConsulta;


    @Override
    public Paciente consultarPaciente(int idPaciente, String tipoid) throws ExcepcionServiciosPacientes {
        Paciente  p =null;
        try {
            p=daoPaciente.cargar(idPaciente,tipoid);
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosPacientesGuiceMybatis.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    @Override
    public List<Paciente> consultarPacientes() throws ExcepcionServiciosPacientes {
        List<Paciente>  p =new ArrayList<>();
        try {
            p=daoPaciente.cargarTodos();
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosPacientesGuiceMybatis.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    
    @Transactional
    @Override
    public void registrarNuevoPaciente(Paciente p) throws ExcepcionServiciosPacientes {
        try {
            daoEps.guardar(p.getEps());
            int id_eps=daoEps.cargar2(p.getEps().getNombre()).getId();
            p.getEps().setId(id_eps);
            daoPaciente.guardar(p);
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosPacientesGuiceMybatis.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
    
    @Transactional
    @Override
    public void agregarConsultaPaciente(int idPaciente, String tipoid, Consulta c) throws ExcepcionServiciosPacientes {
         try {
            daoConsulta.guardar(c,idPaciente,tipoid);
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
        List<Consulta>  c =new ArrayList<>();
        try {
            c=daoConsulta.cargarTodos3(nameEps);
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosPacientesGuiceMybatis.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

   

    @Override
    public Paciente consultarPacienteConConsulta(int idConsulta) throws ExcepcionServiciosPacientes {
        Paciente  p =null;
        try {
            p=daoPaciente.cargar2(idConsulta);
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosPacientesGuiceMybatis.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    @Override
    public List<Consulta> consultarConsultaPaciente(int idPaciente, String tipoid) throws ExcepcionServiciosPacientes {
        List<Consulta>  c =new ArrayList<>();
        try {
            c=daoConsulta.cargarTodos2(idPaciente);
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosPacientesGuiceMybatis.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

    @Override
    public List<Paciente> consultarPacienteMoroso(String nameEps, long deudaGeneral) throws ExcepcionServiciosPacientes {
        List<Paciente>  p =new ArrayList<>();
        try {
            p=daoPaciente.cargarTodos2(nameEps, deudaGeneral);
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosPacientesGuiceMybatis.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }


    @Override
    public List<Consulta> obtenerConsultasEpsPorFecha(String nameEps, java.util.Date fechaInicio, java.util.Date fechaFin) throws ExcepcionServiciosPacientes {
        
        
         List<Consulta>  c =new ArrayList<>();
        try {
            c=daoConsulta.cargarTodos4(nameEps, fechaInicio, fechaFin);
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiciosPacientesGuiceMybatis.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
        
    }
   
    
}
