/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.Date;
import model.Alumno;
import model.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author a635019
 */
public class DAOAlumno {
    private final JdbcTemplate jdbcTemplate;
	
    @Autowired
    public DAOAlumno(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
    }
    
    public void insertAlumno(Alumno a) {
        //Inserto Persona
        this.jdbcTemplate.update(
                "INSERT INTO persona (tipodoc, documento, nombre, apellido, fechanac) " +
                "VALUES (?,?,?,?,?);", a.getPersona().getTipoDocumento(), 
                a.getPersona().getNroDocumento(), a.getPersona().getNombre(), 
                a.getPersona().getApellido(), a.getPersona().getFechaNacimiento());
        
        //Obtengo el id de Persona
        SqlRowSet getIdPersona;
        getIdPersona = jdbcTemplate.queryForRowSet(
                "select max(identificador) identificador from persona;");
        getIdPersona.next();
        int idPersona = getIdPersona.getInt("identificador");
        
        //Inserto Alumno
        this.jdbcTemplate.update(
                "INSERT INTO alumno (idpersona, legajo) " +
                "VALUES (?,?);", idPersona, a.getLegajo());
    }
    
    public ArrayList<Alumno> getAllAlumnos() {
        SqlRowSet alumnos;
        alumnos = jdbcTemplate.queryForRowSet(
                "SELECT p.identificador idPersona, p.tipodoc, p.documento, " +
                "p.nombre, p.apellido, p.fechanac, a.identificador idAlumno, " +
                "a.legajo " +
                "FROM persona p, alumno a " +
                "WHERE p.identificador = a.idpersona;");

        ArrayList<Alumno> listaAlumnos = new ArrayList<Alumno>();
        while (alumnos.next()) {
            Integer identificadorP = alumnos.getInt("idPersona");
            String tipoDocumento = alumnos.getString("tipodoc");
            Integer nroDocumento = alumnos.getInt("documento");
            String nombre = alumnos.getString("nombre");
            String apellido = alumnos.getString("apellido");
            Date fechaNacimiento = alumnos.getDate("fechanac");
            Integer identificadorA = alumnos.getInt("idAlumno");
            Integer legajo = alumnos.getInt("legajo");
            
            Persona p = new Persona();
            p.setIdentificador(identificadorP);
            p.setTipoDocumento(tipoDocumento);
            p.setNroDocumento(nroDocumento);
            p.setNombre(nombre);
            p.setApellido(apellido);
            p.setFechaNacimiento(fechaNacimiento);
            
            Alumno a = new Alumno();
            a.setLegajo(legajo);
            a.setIdentificador(identificadorA);
            a.setPersona(p);
            
            listaAlumnos.add(a);
        }
        return listaAlumnos;
    }
    
    public Alumno getAlumnoID(Integer id) {
        SqlRowSet alumnos;
        alumnos = jdbcTemplate.queryForRowSet(
                "SELECT p.identificador idPersona, p.tipodoc, p.documento, " +
                "p.nombre, p.apellido, p.fechanac, a.identificador idAlumno, " +
                "a.legajo " +
                "FROM persona p, alumno a " +
                "WHERE p.identificador = a.idpersona " +
                "AND p.identificador = ?;", id);

        alumnos.next();
        Integer identificadorP = alumnos.getInt("idPersona");
        String tipoDocumento = alumnos.getString("tipodoc");
        Integer nroDocumento = alumnos.getInt("documento");
        String nombre = alumnos.getString("nombre");
        String apellido = alumnos.getString("apellido");
        Date fechaNacimiento = alumnos.getDate("fechanac");
        Integer identificadorA = alumnos.getInt("idAlumno");
        Integer legajo = alumnos.getInt("legajo");

        Persona p = new Persona();
        p.setIdentificador(identificadorP);
        p.setTipoDocumento(tipoDocumento);
        p.setNroDocumento(nroDocumento);
        p.setNombre(nombre);
        p.setApellido(apellido);
        p.setFechaNacimiento(fechaNacimiento);

        Alumno a = new Alumno();
        a.setLegajo(legajo);
        a.setIdentificador(identificadorA);
        a.setPersona(p);
        
        return a;
    }
    
    public void modificarAlumno(Alumno a) {
        this.jdbcTemplate.update(
                "UPDATE persona SET tipodoc = ?, documento = ?, nombre = ?, " +
                "apellido = ?, fechanac = ? where identificador = ? ;", 
                a.getPersona().getTipoDocumento(), a.getPersona().getNroDocumento(),
                a.getPersona().getNombre(), a.getPersona().getApellido(),
                a.getPersona().getFechaNacimiento(), a.getPersona().getIdentificador());
        
        this.jdbcTemplate.update(
                "UPDATE alumno SET legajo = ? where idpersona = ? ;", 
                a.getLegajo(), a.getPersona().getIdentificador());
    }
    
    public void eliminarAlumno(Integer idPersona) {
        jdbcTemplate.update(
                "DELETE FROM persona WHERE identificador = ?;", idPersona);
        
        jdbcTemplate.update(
                "DELETE FROM alumno WHERE idpersona = ?;", idPersona);
    }
}