/*
 * Copyright (C) 2015 hcadavid
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
package edu.eci.pdsw.samples.services;


import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import java.util.Date;

import java.util.List;

/**
 *
 * @author hcadavid
 */
public interface ServiciosPacientes {
    
    
   

    /**
     * Consultar un paciente dado su identificador.
     * @param idPaciente identificador del paciente
     * @param tipoid tipo de identificación del paciente
     * @return el paciente con el identificador dado
     * @throws ExcepcionServiciosPacientes  si el paciente no existe
     */
    public abstract Paciente consultarPaciente(int idPaciente,String tipoid) throws ExcepcionServiciosPacientes;
    
    
    
    /**
     * Consultar todos los pacientes .
     * @return el paciente con el identificador dado
     * @throws ExcepcionServiciosPacientes error en la persistencia al momento de consultar los pacientes
     */
    public abstract List<Paciente> consultarPacientes() throws ExcepcionServiciosPacientes;
    
    /**
     * Registra un nuevo PASIENTE en el sistema
     * @param paciente El nuevo paciente
     * @throws ExcepcionServiciosPacientes error en la persistencia al momento de registrar un nuevo paciente
     */
    public abstract void registrarNuevoPaciente(Paciente paciente) throws ExcepcionServiciosPacientes;
    
    /**
     * Agrega una consulta a un paciente ya registrado
     * @param idPaciente el identificador del paciente
     * @param tipoid el tipo de identificación
     * @param consulta la consulta a ser agregada
     * @throws ExcepcionServiciosPacientes si se presenta algún error de persistencia o si el paciente no existe.
     */
    public abstract void agregarConsultaPaciente(int idPaciente,String tipoid,Consulta consulta) throws ExcepcionServiciosPacientes;
    
    /**
     * Pagar el saldo de una consulta de un paciente en especifico
     * @param idPaciente id del paciente que solicito la consulta
     * @param tipoid el tipo de identificación
     * @param idConsulta  El id de la consulta a pagar
     * @param pagoabono dinero a pagar o abonar , pagoabono mayor a 0 y menor al costo
     * @throws ExcepcionServiciosPacientes si se presenta algún error de persistencia.
     */
    public abstract void pagarAbonarConsulta(int idPaciente,String tipoid,int idConsulta,long pagoabono) throws ExcepcionServiciosPacientes;
    
    
    
    /**
     * Consultar las consultas de una eps con un rango de fechas
     * @param nameEps El nombre de la Eps en cuestion
     * @param fechaInicio fecha inicial del rango de las consultas a tener encuenta
     * @param fechaFin fecha final del rango de las consultas a tener encuenta
     * @return las consultas asociadas a una Eps en un rango de fechas dado
     * @throws ExcepcionServiciosPacientes si se presenta algún error lógico
     * o de persistencia (por ejemplo, si el paciente ya existe).
     */
    public abstract List<Consulta> obtenerConsultasEpsPorFecha(String nameEps,Date fechaInicio,Date fechaFin) throws ExcepcionServiciosPacientes;
    
    /**
     * Consultar el costo total de una eps con un rango de fechas
     * @param nameEps El nombre de la Eps en cuestion
     * @param fechaInicio fecha inicial del rango de las consultas a tener encuenta
     * @param fechaFin fecha final del rango de las consultas a tener encuenta
     * @return Deuda total de las consultas asociadas a una Eps en un rango de fechas dado
     * @throws ExcepcionServiciosPacientes si se presenta algún error lógico
     * o de persistencia (por ejemplo, si el paciente ya existe).
     */
    public abstract long obtenerCostoEpsPorFecha(String nameEps,Date fechaInicio,Date fechaFin) throws ExcepcionServiciosPacientes;
    
    /**
     * Obtiene todas las consultas asociadas a una EPS dada
     * @param nameEps nombre de la Eps en cuestion
     * @return Lista con todas las consultas asociadas a dicha Eps
     * @throws ExcepcionServiciosPacientes si se presenta algún error lógico
     * o de persistencia (por ejemplo, si el paciente ya existe).
     */
    public abstract List<Consulta> obtenerConsultasEps(String nameEps) throws ExcepcionServiciosPacientes;
    
    
    
    /**
     * Consultar el paciente que realizo cierta consulta
     * @param idConsulta El id de la consulta realizada por un paciente
     * @return Paciente que realizo dicha consulta
     * @throws ExcepcionServiciosPacientes si se presenta algún error lógico
     * o de persistencia (por ejemplo, si el paciente ya existe).
     */
    public abstract Paciente consultarPacienteConConsulta(int idConsulta) throws ExcepcionServiciosPacientes;
    
       /**
     * Consulta todas las consultas del paciente en cuestion
     * @param idPaciente id del paciente a buscar sus consultas
     * @param tipoid tipo de identificacion del paciente
     * @return Lista con los item rentados de un cliente
     * @throws ExcepcionServiciosPacientes si se presenta algún error lógico
     * o de persistencia (por ejemplo, si el paciente ya existe).
     */
    public abstract List<Consulta> consultarConsultaPaciente(int idPaciente,String tipoid) throws ExcepcionServiciosPacientes;
    
    /**
     * Consultar los pacientes que mas deben a una Eps
     * @param nameEps el nombre de la Eps a revisar
     * @param deudaGeneral deuda general a evaluar,pacientes que mas deben a una Eps
     * @return Pacientes que deben la deuda general o mas
     * @throws ExcepcionServiciosPacientes si se presenta algún error lógico
     * o de persistencia (por ejemplo, si el paciente ya existe).
     */
    public abstract List<Paciente> consultarPacienteMoroso(String nameEps,long deudaGeneral) throws ExcepcionServiciosPacientes;
}
