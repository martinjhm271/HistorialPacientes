/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.managebeans;

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Eps;

import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.services.ExcepcionServiciosPacientes;

import edu.eci.pdsw.samples.services.ServiciosHistorialPacientesFactory;
import edu.eci.pdsw.samples.services.ServiciosPacientes;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;

import org.primefaces.context.RequestContext;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author 2106913
 */
@ManagedBean(name = "HistorialPacientes")
@SessionScoped
public class RegistroConsultaBean implements Serializable {

    private final ServiciosPacientes sp = ServiciosHistorialPacientesFactory.getInstance().getServiciosPaciente();

    public Paciente selectPaciente, pacienteResponsable;
    public Consulta selectConsulta, selectConsultaEps;
    private String epsconsultarname = "", error = "", tipo_id = "", nombre = "", email = "", epsname = "", epsnit = "", resumenConsulta = "";
    private Date fechaNacimiento, fecha1, fecha2, fechayHoraConsulta;
    private int epsid = 0, epsconsultarid = 0, idConsulta = 0;
    private List<Consulta> consultasEps = new ArrayList<>();
    private List<Paciente> morososEps = new ArrayList<>();
    private long deudaTotalConsultasEps = 0, deudaGeneralPacienteEps = 0, monto = 0, pagoabono = 0, costoConsulta = 0;

    public RegistroConsultaBean() {
    }

    public void showMessage(String m, String n) {
        FacesMessage message;
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, m, n);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public List<Paciente> getPacientes() {
        List<Paciente> temp = null;
        try {
            temp = sp.consultarPacientes();
        } catch (ExcepcionServiciosPacientes ex) {
            Logger.getLogger(RegistroConsultaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return temp;
    }

    public void setPaciente() {
        if (!nombre.equals("") && fechaNacimiento != null && !tipo_id.equals("") && !epsname.equals("") && !epsnit.equals("")) {
            try {
                Eps e = new Eps(epsname, epsnit);
                Paciente p = new Paciente(tipo_id, nombre, fechaNacimiento, e);
                sp.registrarNuevoPaciente(p);
            } catch (ExcepcionServiciosPacientes ex) {
                Logger.getLogger(RegistroConsultaBean.class.getName()).log(Level.SEVERE, null, ex);
                error = "si";
                showMessage("Incorrecto", "Hubo un eror al momento de agregar el paciente, " + ex.getMessage());
            }
        } else {
            showMessage("Campos incompletos", "Porfavor llene todos los campos.");
            error = "si";
        }
        if (error.equals("no")) {
            showMessage("Correcto", "Se agrego el paciente.");
            epsname = "";
            epsnit = "";

            tipo_id = "";
            nombre = "";
            fechaNacimiento = null;

        }
        error = "no";
    }

    public void pagarAbonarConsulta() {
        if (selectConsulta != null && pagoabono > 0) {
            try {
                sp.pagarAbonarConsulta(selectPaciente.getId(), selectPaciente.getTipo_id(), selectConsulta.getId(), pagoabono);
            } catch (ExcepcionServiciosPacientes ex) {
                Logger.getLogger(RegistroConsultaBean.class.getName()).log(Level.SEVERE, null, ex);
                error = "si";
                showMessage("Incorrecto", "Hubo un eror al momento de pagar/abonar, " + ex.getMessage());
            }
        } else {
            showMessage("Incorrecto", "Seleccione una consulta");
            error = "si";
        }

        if (error.equals("no")) {
            showMessage("Correcto", "Se realizo  el pago/abono.");
        }
        error = "no";
    }

    public void setConsulta() {
        if (fechayHoraConsulta != null && !resumenConsulta.equals("") && costoConsulta > 0) {
            try {
                Consulta c = new Consulta(fechayHoraConsulta, resumenConsulta, costoConsulta);
                sp.agregarConsultaPaciente(selectPaciente.getId(), selectPaciente.getTipo_id(), c);
            } catch (ExcepcionServiciosPacientes ex) {
                Logger.getLogger(RegistroConsultaBean.class.getName()).log(Level.SEVERE, null, ex);
                error = "si";
                showMessage("Incorrecto", "Hubo un eror al momento de registrar la consulta, " + ex.getMessage());
            }
        } else {
            showMessage("Campos incompletos", "Porfavor llene todos los campos.");
            error = "si";
        }
        if (error.equals("no")) {
            showMessage("Correcto", "Se agrego la consulta al paciente.");
            fechayHoraConsulta = null;
            resumenConsulta = "";
            costoConsulta = 0;
        }
        error = "no";
    }

    public void calcularDeuda() {
        if (selectPaciente.getConsultas().size() > 0) {
            long ans = 0;
            for (Consulta c : selectPaciente.getConsultas()) {
                ans += c.getCosto();
            }
            deudaGeneralPacienteEps = ans;
        } else {
            error = "si";
            showMessage("Incorrecto", "Hubo un error no hay consultas.");
        }
        if (error.equals("no")) {
            showMessage("Correcto", "Se calculo la deuda del paciente.");
        }
        error = "no";
    }

    public void consultarConsultasEpsPorFecha() {
        if (fecha1 != null && fecha2 != null && !epsconsultarname.equals("")) {
            try {
                consultasEps = sp.obtenerConsultasEpsPorFecha(epsconsultarname, fecha1, fecha2);
            } catch (ExcepcionServiciosPacientes ex) {
                error = "si";
                Logger.getLogger(RegistroConsultaBean.class.getName()).log(Level.SEVERE, null, ex);
                showMessage("Incorrecto", "Hubo un eror al momento de obtener las consultas , " + ex.getMessage());
            }
        } else {
            showMessage("Campos incompletos", "Porfavor llene todos los campos.");
            error = "si";
        }

        if (error.equals("no")) {
            showMessage("Correcto", "Se cargaron " + consultasEps.size() + " consultas.");

        }
        error = "no";
    }

    public void calcularDeudaConsultasEpsPorFecha() {
        if (consultasEps.size() > 0) {
            if (fecha1 != null && fecha2 != null && !epsconsultarname.equals("")) {
                long ans = 0;
                for (Consulta c : consultasEps) {
                    ans += c.getCosto();
                }
                deudaTotalConsultasEps = ans;
            } else {
                showMessage("Campos incompletos", "Porfavor llene todos los campos.");
                error = "si";
            }

            if (error.equals("no")) {
                showMessage("Correcto", "Se calculo la deuda.");

            }
        } else {
            showMessage("Incorrecto", "No hay consultas.");
        }

        error = "no";
    }

    public void consultarMorososEps() {
        if (monto > 0 && !epsconsultarname.equals("")) {
            try {
                morososEps = sp.consultarPacienteMoroso(epsconsultarname, monto);
            } catch (ExcepcionServiciosPacientes ex) {
                error = "si";
                showMessage("Incorrecto", "Hubo un eror al momento de calcular los morosos " + ex.getMessage());
                Logger.getLogger(RegistroConsultaBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            showMessage("Incorrecto", "Llene los campos del monto y el nombre de la Eps a buscar.");
            error = "si";
        }
        if (error.equals("no")) {
            showMessage("Correcto", "Se cargaron los morosos.");
        }
        error = "no";
    }

    public void calcularDeudaTotalPaciente() {
        if (pacienteResponsable != null && pacienteResponsable.getConsultas().size() > 0) {
            long ans = 0;
            for (Consulta c : selectPaciente.getConsultas()) {
                ans += c.getCosto();
            }
            deudaGeneralPacienteEps = ans;
        }
    }

    public int getEpsid() {
        return epsid;
    }

    public void setEpsid(int epsid) {
        this.epsid = epsid;
    }

    public int getEpsconsultarid() {
        return epsconsultarid;
    }

    public void setEpsconsultarid(int epsconsultar) {
        this.epsconsultarid = epsconsultar;
    }

    public String getEpsconsultarname() {
        return epsconsultarname;
    }

    public void setEpsconsultarname(String epsconsultarname) {
        this.epsconsultarname = epsconsultarname;
    }

    public Paciente getSelectPaciente() {
        return selectPaciente;
    }

    public void setSelectPaciente(Paciente selectPaciente) {
        this.selectPaciente = selectPaciente;
    }

    public Paciente getPacienteResponsable() {
        return pacienteResponsable;
    }

    public void setPacienteResponsable(Paciente pacienteResponsable) {
        this.pacienteResponsable = pacienteResponsable;
    }

    public Consulta getSelectConsulta() {
        return selectConsulta;
    }

    public void setSelectConsulta(Consulta selectConsulta) {
        this.selectConsulta = selectConsulta;
    }

    public Consulta getSelectConsultaEps() {
        return selectConsultaEps;
    }

    public void setSelectConsultaEps(Consulta selectConsultaEps) {
        this.selectConsultaEps = selectConsultaEps;
    }

    public String getTipo_id() {
        return tipo_id;
    }

    public void setTipo_id(String tipo_id) {
        this.tipo_id = tipo_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEpsname() {
        return epsname;
    }

    public void setEpsname(String epsname) {
        this.epsname = epsname;
    }

    public String getEpsnit() {
        return epsnit;
    }

    public void setEpsnit(String epsnit) {
        this.epsnit = epsnit;
    }

    public String getResumenConsulta() {
        return resumenConsulta;
    }

    public void setResumenConsulta(String resumenConsulta) {
        this.resumenConsulta = resumenConsulta;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFecha1() {
        return fecha1;
    }

    public void setFecha1(Date fecha1) {
        this.fecha1 = fecha1;
    }

    public Date getFecha2() {
        return fecha2;
    }

    public void setFecha2(Date fecha2) {
        this.fecha2 = fecha2;
    }

    public Date getFechayHoraConsulta() {
        return fechayHoraConsulta;
    }

    public void setFechayHoraConsulta(Date fechayHoraConsulta) {
        this.fechayHoraConsulta = fechayHoraConsulta;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public List<Consulta> getConsultasEps() {
        return consultasEps;
    }

    public void setConsultasEps(List<Consulta> consultasEps) {
        this.consultasEps = consultasEps;
    }

    public List<Paciente> getMorososEps() {
        return morososEps;
    }

    public void setMorososEps(List<Paciente> morososEps) {
        this.morososEps = morososEps;
    }

    public long getDeudaTotalConsultasEps() {
        return deudaTotalConsultasEps;
    }

    public void setDeudaTotalConsultasEps(long deudaTotalConsultasEps) {
        this.deudaTotalConsultasEps = deudaTotalConsultasEps;
    }

    public long getDeudaGeneralPacienteEps() {
        return deudaGeneralPacienteEps;
    }

    public void setDeudaGeneralPacienteEps(long deudaGeneralPacienteEps) {
        this.deudaGeneralPacienteEps = deudaGeneralPacienteEps;
    }

    public long getMonto() {
        return monto;
    }

    public void setMonto(long monto) {
        this.monto = monto;
    }

    public long getPagoabono() {
        return pagoabono;
    }

    public void setPagoabono(long pagoabono) {
        this.pagoabono = pagoabono;
    }

    public long getCostoConsulta() {
        return costoConsulta;
    }

    public void setCostoConsulta(long costoConsulta) {
        this.costoConsulta = costoConsulta;
    }

}
