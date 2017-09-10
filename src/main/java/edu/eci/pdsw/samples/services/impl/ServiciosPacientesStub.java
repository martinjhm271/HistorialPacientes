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
        Paciente p = pacientes.get(new Tupla<>(idPaciente, tipoid));
        if (p == null) {
            throw new ExcepcionServiciosPacientes("Paciente " + idPaciente + " no esta registrado");
        } else {
            return p;
        }

    }

    @Override
    public void registrarNuevoPaciente(Paciente p) throws ExcepcionServiciosPacientes {
        p.getEps().setId(ideps);
        ideps++;
        p.setId(idpaciente);
        idpaciente++;
        pacientes.put(new Tupla<>(p.getId(), p.getTipo_id()), p);
    }

    @Override
    public void agregarConsultaPaciente(int idPaciente, String tipoid, Consulta c) throws ExcepcionServiciosPacientes {
        Paciente p = pacientes.get(new Tupla<>(idPaciente, tipoid));
        if (p != null) {
            c.setId(idconsulta);
            idconsulta++;
            p.getConsultas().add(c);
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
        Paciente p = consultarPaciente(idPaciente, tipoid);
        if (p != null) {
            for (Consulta c : p.getConsultas()) {
                if (c.getId() == idConsulta) {
                    if (c.getCosto() < pagoabono) {
                        throw new ExcepcionServiciosPacientes("esta pagando/abonando mas de la cuenta,porfavor pague solo lo necesario.");
                    } else {
                        c.setCosto(c.getCosto() - pagoabono);
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
        for (Paciente p : pacientes.values()) {
            if (p.getEps().getNombre().equals(nameEps)) {
                for (Consulta c : p.getConsultas()) {
                    if (c.getFechayHora().before(fechaFin) && c.getFechayHora().after(fechaInicio)) {
                        temp.add(c);
                    }
                }
            }
        }
        return temp;
    }

    @Override
    public List<Consulta> obtenerConsultasEps(String nameEps) throws ExcepcionServiciosPacientes {
        List<Consulta> temp = new ArrayList<>();
        for (Paciente p : pacientes.values()) {
            if (p.getEps().getNombre().equals(nameEps)) {
                temp.addAll(p.getConsultas());
            }
        }
        if (temp.isEmpty()) {
            throw new ExcepcionServiciosPacientes("La EPS " + nameEps + " no se encuentra asociada a ningun paciente o no existe ");
        }
        return temp;
    }

    

    @Override
    public Paciente consultarPacienteConConsulta(int idConsulta) throws ExcepcionServiciosPacientes {
        Paciente p = null;
        for (Paciente p1 : pacientes.values()) {
            for (Consulta c : p1.getConsultas()) {
                if (c.getId() == idConsulta) {
                    return p1;
                }
            }
        }
        return p;
    }

    @Override
    public List<Consulta> consultarConsultaPaciente(int idPaciente, String tipoid) throws ExcepcionServiciosPacientes {
        Paciente p = pacientes.get(new Tupla<>(idPaciente, tipoid));
        List<Consulta> temp = new ArrayList<>();
        if (p == null) {
            throw new ExcepcionServiciosPacientes("Paciente " + idPaciente + " no esta registrado");
        } else {
            temp.addAll(p.getConsultas());
            return temp;
        }
    }

    @Override
    public List<Paciente> consultarPacienteMoroso(String nameEps, long deudaGeneral) throws ExcepcionServiciosPacientes {
        List<Paciente> temp = new ArrayList<>();
        long deuda = 0;
        for (Paciente p1 : pacientes.values()) {
            if (p1.getEps().getNombre().equals(nameEps)) {
                for (Consulta c : p1.getConsultas()) {
                    deuda += c.getCosto();
                }
                if (deuda >= deudaGeneral) {
                    temp.add(p1);
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

            Paciente p1 = new Paciente("CC", "Juan Perez", java.sql.Date.valueOf("2000-01-01"), eps1);
            Paciente p2 = new Paciente("CC", "Maria Rodriguez", java.sql.Date.valueOf("2000-01-01"), eps2);
            Paciente p3 = new Paciente("CC", "Pedro Martinez", java.sql.Date.valueOf("1956-05-01"), eps3);
            Paciente p4 = new Paciente("CC", "Martin Hernandez", java.sql.Date.valueOf("2000-01-01"), eps4);
            Paciente p5 = new Paciente("CC", "Cristian Pinzon", java.sql.Date.valueOf("2000-01-01"), eps4);
            Paciente p6 = new Paciente("CC", "Daniel Beltran", java.sql.Date.valueOf("1956-05-01"), eps5);
            Paciente p7 = new Paciente("CC", "Ricardo Pinto", java.sql.Date.valueOf("1956-05-01"), eps6);

            registrarNuevoPaciente(p1);
            registrarNuevoPaciente(p2);
            registrarNuevoPaciente(p3);
            registrarNuevoPaciente(p4);
            registrarNuevoPaciente(p5);
            registrarNuevoPaciente(p6);
            registrarNuevoPaciente(p7);

            Consulta c1 = new Consulta(java.sql.Date.valueOf("2000-01-01"), "Dolor de cabeza", 454);
            Consulta c2 = new Consulta(java.sql.Date.valueOf("2000-01-02"), "Dolor de estomago", 271);
            Consulta c3 = new Consulta(java.sql.Date.valueOf("2000-04-01"), "Dolor de garganta", 222);
            Consulta c4 = new Consulta(java.sql.Date.valueOf("2000-01-01"), "Gripa", 54);
            Consulta c5 = new Consulta(java.sql.Date.valueOf("2000-03-11"), "Fiebre", 71);
            Consulta c6 = new Consulta(java.sql.Date.valueOf("2000-02-07"), "Malestar y mareo", 322);
            Consulta c7 = new Consulta(java.sql.Date.valueOf("2000-02-09"), "Vomito", 322);
            Consulta c8 = new Consulta(java.sql.Date.valueOf("2000-02-09"), "Alergia", 322);
            Consulta c9 = new Consulta(java.sql.Date.valueOf("2000-02-09"), "Resfriado", 322);

            agregarConsultaPaciente(1, "CC", c1);
            agregarConsultaPaciente(1, "CC", c2);
            agregarConsultaPaciente(1, "CC", c3);
            agregarConsultaPaciente(2, "CC", c4);
            agregarConsultaPaciente(3, "CC", c5);
            agregarConsultaPaciente(4, "CC", c6);
            agregarConsultaPaciente(5, "CC", c7);
            agregarConsultaPaciente(6, "CC", c8);
            agregarConsultaPaciente(7, "CC", c5);
            agregarConsultaPaciente(7, "CC", c9);

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
