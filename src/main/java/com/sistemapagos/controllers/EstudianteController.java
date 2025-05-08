package com.sistemapagos.controllers;

import com.sistemapagos.entities.Estudiante;
import com.sistemapagos.entities.Pago;
import com.sistemapagos.enums.PagoStatus;
import com.sistemapagos.repository.EstudianteRepository;
import com.sistemapagos.repository.PagoRepository;
import com.sistemapagos.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class EstudianteController {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private PagoService pagoService;

    @GetMapping("/estudiantes")
    public List<Estudiante> listarEstudiantes(){
        return estudianteRepository.findAll();
    }

    @GetMapping("/estudiantes/{codigo}")
    public Estudiante listarEstudiantePorCodigo(@PathVariable String codigo){
        return estudianteRepository.findByCodigo(codigo);
    }

    @GetMapping("/estudiantesPorPrograma")
    public List<Estudiante> listarEstudiantePorPrograma(@RequestParam String programaId){
        return estudianteRepository.findByProgramaId(programaId);
    }

    @GetMapping("/pagos")
    public List<Pago> listarPagos(){
        return pagoRepository.findAll();
    }

    @GetMapping("/pagos/{id}")
    public Pago listarPagoPorId(@PathVariable Long id){
        return pagoRepository.findById(id).get();
    }

    @GetMapping("/estudiantes/{codigo}/pagos")
    public List<Pago> listarPagosPorCodigoEstudiante(@PathVariable String codigo){
        return pagoRepository.findByEstudianteCodigo(codigo);
    }

    @GetMapping("/pagosPorStatus")
    public List<Pago> listarPagosPorStatus(@RequestParam PagoStatus status){
        return pagoRepository.findByStatus(status);
    }


}
