/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  a635019
 * Created: 28/10/2018
 */
$(document).ready(function() {
    $("tbody tr").mouseover(function() {
        $(this).css("cursor", "pointer");
        if ($(this).hasClass("visto")) {
                $(this).removeClass("visto");
        } else {
                $(this).addClass("visto");
        }
    });
    $("tbody tr").mouseout(function() {
        $(this).css("cursor", "pointer");
        if ($(this).hasClass("visto")) {
                $(this).removeClass("visto");
        }
    });
    
    $('button#modificarAlumno').click(function() {
        var idPer = $('tr.visto p#idPer').text();
        console.log(idPer);
        
        $.ajax({
            url: '/alumnos/buscar/' + idPer,
            method: "POST",
            success: function(respuesta) {
                
                var tipoDoc = respuesta.persona.tipoDocumento;
                var nroDoc = respuesta.persona.nroDocumento;
                var nombre = respuesta.persona.nombre;
                var apellido = respuesta.persona.apellido;
                var fecNac = respuesta.persona.fechaNacimiento;
                var legajo = respuesta.legajo;
                var idPersona = respuesta.persona.identificador;
                var idAlumno = respuesta.identificador;

                $("#tipoDoc option[value="+tipoDoc+"]").attr("selected",true);
                $("#nroDoc").val(nroDoc);
                $("#nombre").val(nombre);
                $("#apellido").val(apellido);
                $("#fecNac").val(fecNac);
                $("#legajo").val(legajo);
                
                $('#formAlumno').attr('action', '/alumnos/modificar/' + idPersona);
            }
        });
        
    });
});