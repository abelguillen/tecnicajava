/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ch.qos.logback.core.CoreConstants;
import dao.DAOAlumno;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import model.Alumno;
import model.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author a635019
 */
@Controller
@RequestMapping("/alumnos")
public class AbmAlumnosController {
    private final JdbcTemplate jdbcTemplate;
    private final DAOAlumno daoAlumno;

    @Autowired
    public AbmAlumnosController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        daoAlumno = new DAOAlumno(jdbcTemplate);
    }
    
    @RequestMapping(value = "")
    public String alumnos(Model template) {
        return "alumnos";
    }
    
    @RequestMapping(value = "/nuevo")
    public String nuevoAlumno(Model template) {
        return "nuevoAlumno";
    }
    
    @RequestMapping(value = "/nuevo", method = RequestMethod.POST)
    public String nuevoAlumnoPost(Model template, 
            @RequestParam(value = "nombre") String nombre,
            @RequestParam(value = "apellido") String apellido,
            @RequestParam(value = "tipoDocumento") String tipoDocumento,
            @RequestParam(value = "nroDocumento") Integer nroDocumento,
            @RequestParam(value = "fechaNacimiento") String fechaNacimiento,
            @RequestParam(value = "legajo") Integer legajo) {
            
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Persona p = new Persona();
        p.setNombre(nombre);
        p.setApellido(apellido);
        p.setTipoDocumento(tipoDocumento);
        p.setNroDocumento(nroDocumento);
        Date fechaNac = null;
        try {
            fechaNac = sdf.parse(fechaNacimiento);
        } catch (ParseException ex) {
            System.out.println(ex);
        }
        p.setFechaNacimiento(fechaNac);
        
        Alumno a = new Alumno();
        a.setPersona(p);
        a.setLegajo(legajo);
        
        daoAlumno.insertAlumno(a);
        
        return "redirect:" + "/alumnos/lista";
    }
    
    @RequestMapping(value = "/lista")
    public String listaAlumnos(Model template) {
        ArrayList<Alumno> alumnos = daoAlumno.getAllAlumnos();
        template.addAttribute("alumnos", alumnos);
        return "listaAlumnos";
    }
    
    @RequestMapping(value = "/modificar/{idPersona}")
    public String modificarAlumnos(Model template,
            @PathVariable(value = "idPersona") Integer idPersona,
            @RequestParam(value = "nombre") String nombre,
            @RequestParam(value = "apellido") String apellido,
            @RequestParam(value = "tipoDocumento") String tipoDocumento,
            @RequestParam(value = "nroDocumento") Integer nroDocumento,
            @RequestParam(value = "fechaNacimiento") String fechaNacimiento,
            @RequestParam(value = "legajo") Integer legajo) {
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Persona p = new Persona();
        p.setIdentificador(idPersona);
        p.setNombre(nombre);
        p.setApellido(apellido);
        p.setTipoDocumento(tipoDocumento);
        p.setNroDocumento(nroDocumento);
        Date fechaNac = null;
        try {
            fechaNac = sdf.parse(fechaNacimiento);
        } catch (ParseException ex) {
            System.out.println(ex);
        }
        p.setFechaNacimiento(fechaNac);
        
        Alumno a = new Alumno();
        a.setPersona(p);
        a.setLegajo(legajo);
        
        daoAlumno.modificarAlumno(a);
        
        return "redirect:" + "/alumnos/lista";
    }
    
    @RequestMapping(value = "/eliminar/{idPersona}")
    public String eliminarAlumno(Model template,
            @PathVariable(value = "idPersona") Integer idPersona) {
        daoAlumno.eliminarAlumno(idPersona);
        return "redirect:" + "/alumnos/lista";
    }
}