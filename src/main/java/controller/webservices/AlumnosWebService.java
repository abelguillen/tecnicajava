/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.webservices;

import dao.DAOAlumno;
import model.Alumno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author a635019
 */
@RestController
public class AlumnosWebService {
    private final JdbcTemplate jdbcTemplate;
    private final DAOAlumno daoAlumno;
	
    @Autowired
    public AlumnosWebService(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
            daoAlumno = new DAOAlumno(jdbcTemplate);
    }
    
    @RequestMapping(value = "/alumnos/buscar/{id}", method = RequestMethod.POST)
    public Alumno getAlumnoID(@PathVariable(value = "id") Integer id) {
        return daoAlumno.getAlumnoID(id);
    }
}