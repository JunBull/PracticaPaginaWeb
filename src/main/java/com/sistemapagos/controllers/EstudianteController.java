package com.sistemapagos.controllers;

import com.sistemapagos.entities.Estudiante;
import com.sistemapagos.entities.Pago;
import com.sistemapagos.enums.PagoStatus;
import com.sistemapagos.enums.TypePago;
import com.sistemapagos.repository.EstudianteRepository;
import com.sistemapagos.repository.PagoRepository;
import com.sistemapagos.services.PagoService;
import org.hibernate.dialect.PgJdbcHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class EstudianteController {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private PagoService pagoService;

    @GetMapping("/estudiantes")
    public String listarEstudiantes(Model model){
        List<Estudiante> estudiantes = estudianteRepository.findAll();
        model.addAttribute("estudiantes", estudiantes);
        return "estudiantes";  // Esta es la vista Thymeleaf (estudiantes.html)
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
    public String listarPagos(Model model){
        List<Pago> pagos = pagoRepository.findAll();
        model.addAttribute("pagos", pagos);
        return "pagos";  // Esta es la vista Thymeleaf (pagos.html)
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

    @GetMapping("/pagos/porTipo")
    public List<Pago> listarPagoPorType(@RequestParam TypePago type){
        return pagoRepository.findByType(type);
    }

    @PutMapping("/pagos/{pagoId}/actualizarStatusDePago")
    public Pago actualizarStatusDePago(@RequestParam PagoStatus status,@PathVariable Long pagoId){
        return pagoService.actualizarPagoPorStatus(status,pagoId);
    }

    @PostMapping(path = "/pagos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Pago guardarPago(@RequestParam("file") MultipartFile file, double cantidad, TypePago type, LocalDate date, String codigoEstudiante) throws IOException {
        return pagoService.savePago(file, cantidad, type, date, codigoEstudiante);
    }

    @GetMapping(value = "/pagoFile/{pagoId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] listarArchivoPorId(@PathVariable Long pagoId) throws IOException {
        return pagoService.getArchivoPorId(pagoId);
    }

}
