/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.services;









import static com.google.inject.Guice.createInjector;
import com.google.inject.Injector;
import edu.eci.pdsw.samples.dao.DaoConsulta;
import edu.eci.pdsw.samples.dao.DaoEps;
import edu.eci.pdsw.samples.dao.DaoPaciente;
import edu.eci.pdsw.samples.dao.mybatis.MyBatisDAOConsulta;
import edu.eci.pdsw.samples.dao.mybatis.MyBatisDAOEps;
import edu.eci.pdsw.samples.dao.mybatis.MyBatisDAOPaciente;


import org.mybatis.guice.XMLMyBatisModule;
import org.mybatis.guice.datasource.helper.JdbcHelper;
import org.mybatis.guice.*;





import edu.eci.pdsw.samples.services.impl.ServiciosPacientesGuiceMybatis;

/**
 *
 * @author hcadavid
 */
public class ServiciosHistorialPacientesFactory {

    private static  ServiciosHistorialPacientesFactory instance = new ServiciosHistorialPacientesFactory();

    private static Injector injector;

    private static Injector testInjector;

    public ServiciosHistorialPacientesFactory() {

        injector = createInjector(new XMLMyBatisModule() {

            @Override
            protected void initialize() {
                install(JdbcHelper.PostgreSQL);              
                setClassPathResource("mybatis-config.xml");
                bind(ServiciosPacientes.class).to(ServiciosPacientesGuiceMybatis.class);
                bind(DaoEps.class).to(MyBatisDAOEps.class);
                bind(DaoConsulta.class).to(MyBatisDAOConsulta.class);
                bind(DaoPaciente.class).to(MyBatisDAOPaciente.class);
            }

        }
        );

        testInjector = createInjector(new XMLMyBatisModule() {

            @Override
            protected void initialize() {
                install(JdbcHelper.PostgreSQL);
                setClassPathResource("mybatis-config-h2.xml");
                bind(ServiciosPacientes.class).to(ServiciosPacientesGuiceMybatis.class);
                bind(DaoEps.class).to(MyBatisDAOEps.class);
                bind(DaoConsulta.class).to(MyBatisDAOConsulta.class);
                bind(DaoPaciente.class).to(MyBatisDAOPaciente.class);
            }

        }
        );

    }

   
    public ServiciosPacientes getServiciosPaciente() {
        return injector.getInstance(ServiciosPacientes.class);
    }

    public ServiciosPacientes getServiciosPacienteTesting() {
        return testInjector.getInstance(ServiciosPacientes.class);
    }

    public static ServiciosHistorialPacientesFactory getInstance() {
        return instance;
    }

}
