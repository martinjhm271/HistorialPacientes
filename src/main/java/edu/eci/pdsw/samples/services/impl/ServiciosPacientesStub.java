/*
 * Copyright (C) 2016 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.pdsw.samples.services.impl;

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Eps;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.services.ExcepcionServiciosPacientes;
import edu.eci.pdsw.samples.services.ServiciosPacientes;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class ServiciosPacientesStub implements ServiciosPacientes {

    private final Map<Tupla<Integer, String>, Paciente> pacientes;
    private int idpaciente = 1, ideps = 1, idconsulta = 1;

    public ServiciosPacientesStub() {
        this.pacientes = new LinkedHashMap<>();
        cargarDatosEstaticos(pacientes);
    }

    @Override
    public Paciente consultarPaciente(int idPaciente, String tipoid) throws ExcepcionServiciosPacientes {
        Paciente paciente = pacientes.get(new Tupla<>(idPaciente, tipoid));
        if (paciente == null) {
            throw new ExcepcionServiciosPacientes("Paciente " + idPaciente + " no esta registrado");
        } else {
            return paciente;
        }

    }

    @Override
    public void registrarNuevoPaciente(Paciente paciente) throws ExcepcionServiciosPacientes {
        paciente.getEps().setId(ideps);
        ideps++;
        paciente.setId(idpaciente);
        idpaciente++;
        pacientes.put(new Tupla<>(paciente.getId(), paciente.getTipoId()), paciente);
    }

    @Override
    public void agregarConsultaPaciente(int idPaciente, String tipoid, Consulta consulta) throws ExcepcionServiciosPacientes {
        Paciente paciente = pacientes.get(new Tupla<>(idPaciente, tipoid));
        if (paciente != null) {
            consulta.setId(idconsulta);
            idconsulta++;
            paciente.getConsultas().add(consulta);
        } else {
            throw new ExcepcionServiciosPacientes("Paciente " + idPaciente + " no esta registrado");
        }
    }

    @Override
    public List<Paciente> consultarPacientes() throws ExcepcionServiciosPacientes {
        List<Paciente> temp = new ArrayList<>();
        temp.addAll(pacientes.values());
        return temp;
    }

    @Override
    public void pagarAbonarConsulta(int idPaciente, String tipoid, int idConsulta, long pagoabono) throws ExcepcionServiciosPacientes {
        boolean ban = false;
        Paciente paciente = consultarPaciente(idPaciente, tipoid);
        if (paciente != null) {
            for (Consulta consulta : paciente.getConsultas()) {
                if (consulta.getId() == idConsulta) {
                    if (consulta.getCosto() < pagoabono) {
                        throw new ExcepcionServiciosPacientes("esta pagando/abonando mas de la cuenta,porfavor pague solo lo necesario.");
                    } else {
                        consulta.setCosto(consulta.getCosto() - pagoabono);
                        ban = true;
                        break;
                    }

                }
            }
        } else {
            throw new ExcepcionServiciosPacientes("Paciente " + idPaciente + " no esta registrado");
        }
        if (!ban) {
            throw new ExcepcionServiciosPacientes("El Paciente " + idPaciente + " no tiene asociada dicha consulta " + idConsulta);
        }
    }


    @Override
    public List<Consulta> obtenerConsultasEpsPorFecha(String nameEps, Date fechaInicio, Date fechaFin) throws ExcepcionServiciosPacientes {
        List<Consulta> temp = new ArrayList<>();
        for (Paciente paciente : pacientes.values()) {
            if (paciente.getEps().getNombre().equals(nameEps)) {
                for (Consulta consulta : paciente.getConsultas()) {
                    if (consulta.getFechayHora().before(fechaFin) && consulta.getFechayHora().after(fechaInicio)) {
                        temp.add(consulta);
                    }
                }
            }
        }
        return temp;
    }

    @Override
    public List<Consulta> obtenerConsultasEps(String nameEps) throws ExcepcionServiciosPacientes {
        List<Consulta> temp = new ArrayList<>();
        for (Paciente paciente : pacientes.values()) {
            if (paciente.getEps().getNombre().equals(nameEps)) {
                temp.addAll(paciente.getConsultas());
            }
        }
        if (temp.isEmpty()) {
            throw new ExcepcionServiciosPacientes("La EPS " + nameEps + " no se encuentra asociada a ningun paciente o no existe ");
        }
        return temp;
    }
    
    @Override
    public long obtenerCostoEpsPorFecha(String nameEps, Date fechaInicio, Date fechaFin) throws ExcepcionServiciosPacientes {
        long deuda = 0;
        for (Paciente paciente : pacientes.values()) {
            if (paciente.getEps().getNombre().equals(nameEps)) {
                for (Consulta consulta : paciente.getConsultas()) {
                    if(consulta.getFechayHora().after(fechaInicio) && consulta.getFechayHora().before(fechaFin)){deuda += consulta.getCosto();}
                }
            }
        }
        return deuda;
    }


    

    @Override
    public Paciente consultarPacienteConConsulta(int idConsulta) throws ExcepcionServiciosPacientes {
        Paciente paciente = null;
        for (Paciente paciente2 : pacientes.values()) {
            for (Consulta consulta : paciente2.getConsultas()) {
                if (consulta.getId() == idConsulta) {
                    return paciente2;
                }
            }
        }
        return paciente;
    }

    @Override
    public List<Consulta> consultarConsultaPaciente(int idPaciente, String tipoid) throws ExcepcionServiciosPacientes {
        Paciente paciente = pacientes.get(new Tupla<>(idPaciente, tipoid));
        List<Consulta> temp = new ArrayList<>();
        if (paciente == null) {
            throw new ExcepcionServiciosPacientes("Paciente " + idPaciente + " no esta registrado");
        } else {
            temp.addAll(paciente.getConsultas());
            return temp;
        }
    }

    @Override
    public List<Paciente> consultarPacienteMoroso(String nameEps, long deudaGeneral) throws ExcepcionServiciosPacientes {
        List<Paciente> temp = new ArrayList<>();
        long deuda = 0;
        for (Paciente paciente1 : pacientes.values()) {
            if (paciente1.getEps().getNombre().equals(nameEps)) {
                for (Consulta consulta : paciente1.getConsultas()) {
                    deuda += consulta.getCosto();
                }
                if (deuda >= deudaGeneral) {
                    temp.add(paciente1);
                }
            }

            deuda = 0;
        }
        return temp;
    }

    private void cargarDatosEstaticos(Map<Tupla<Integer, String>, Paciente> pacientes) {
        try {
            Eps eps1 = new Eps("Compensar", "com");
            Eps eps2 = new Eps("Sanitas", "sani");
            Eps eps3 = new Eps("Sura", "sur");
            Eps eps4 = new Eps("Coomeva", "coo");
            Eps eps5 = new Eps("Medimas", "med");
            Eps eps6 = new Eps("SaludTotal", "slt");

            Paciente paciente1 = new Paciente("CC", "Juan Perez", java.sql.Date.valueOf("2000-01-01"), eps1);
            Paciente paciente2 = new Paciente("CC", "Maria Rodriguez", java.sql.Date.valueOf("2000-01-01"), eps2);
            Paciente paciente3 = new Paciente("CC", "Pedro Martinez", java.sql.Date.valueOf("1956-05-01"), eps3);
            Paciente paciente4 = new Paciente("CC", "Martin Hernandez", java.sql.Date.valueOf("2000-01-01"), eps4);
            Paciente paciente5 = new Paciente("CC", "Cristian Pinzon", java.sql.Date.valueOf("2000-01-01"), eps4);
            Paciente paciente6 = new Paciente("CC", "Daniel Beltran", java.sql.Date.valueOf("1956-05-01"), eps5);
            Paciente paciente7 = new Paciente("CC", "Ricardo Pinto", java.sql.Date.valueOf("1956-05-01"), eps6);

            registrarNuevoPaciente(paciente1);
            registrarNuevoPaciente(paciente2);
            registrarNuevoPaciente(paciente3);
            registrarNuevoPaciente(paciente4);
            registrarNuevoPaciente(paciente5);
            registrarNuevoPaciente(paciente6);
            registrarNuevoPaciente(paciente7);

            Consulta consulta1 = new Consulta(java.sql.Date.valueOf("2000-01-01"), "Dolor de cabeza", 454);
            Consulta consulta2 = new Consulta(java.sql.Date.valueOf("2000-01-02"), "Dolor de estomago", 271);
            Consulta consulta3 = new Consulta(java.sql.Date.valueOf("2000-04-01"), "Dolor de garganta", 222);
            Consulta consulta4 = new Consulta(java.sql.Date.valueOf("2000-01-01"), "Gripa", 54);
            Consulta consulta5 = new Consulta(java.sql.Date.valueOf("2000-03-11"), "Fiebre", 71);
            Consulta consulta6 = new Consulta(java.sql.Date.valueOf("2000-02-07"), "Malestar y mareo", 322);
            Consulta consulta7 = new Consulta(java.sql.Date.valueOf("2000-02-09"), "Vomito", 322);
            Consulta consulta8 = new Consulta(java.sql.Date.valueOf("2000-02-09"), "Alergia", 322);
            Consulta consulta9 = new Consulta(java.sql.Date.valueOf("2000-02-09"), "Resfriado", 322);

            agregarConsultaPaciente(1, "CC", consulta1);
            agregarConsultaPaciente(1, "CC", consulta2);
            agregarConsultaPaciente(1, "CC", consulta3);
            agregarConsultaPaciente(2, "CC", consulta4);
            agregarConsultaPaciente(3, "CC", consulta5);
            agregarConsultaPaciente(4, "CC", consulta6);
            agregarConsultaPaciente(5, "CC", consulta7);
            agregarConsultaPaciente(6, "CC", consulta8);
            agregarConsultaPaciente(7, "CC", consulta5);
            agregarConsultaPaciente(7, "CC", consulta9);

        } catch (ExcepcionServiciosPacientes ex) {
            Logger.getLogger(ServiciosPacientesStub.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
}

class Tupla<A, B> {

    A a;
    B b;

    public Tupla(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.a);
        hash = 71 * hash + Objects.hashCode(this.b);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tupla<?, ?> other = (Tupla<?, ?>) obj;
        if (!Objects.equals(this.a, other.a)) {
            return false;
        }
        if (!Objects.equals(this.b, other.b)) {
            return false;
        }
        return true;
    }

}
