package com.sistemapagos.services;

import com.sistemapagos.entities.Estudiante;
import com.sistemapagos.entities.Pago;
import com.sistemapagos.enums.PagoStatus;
import com.sistemapagos.enums.TypePago;
import com.sistemapagos.repository.EstudianteRepository;
import com.sistemapagos.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class PagoService {
    @Autowired
    private PagoRepository pagoRepository;
    @Autowired
    private EstudianteRepository estudianteRepository;

    public Pago savePago(MultipartFile file, double cantdad, TypePago type, LocalDate date, String codigoEstudiante) throws IOException {
        /*
        - Creamos una ruta donde se guardara el archivo
        - System.getProperty(user,home): Obtiene la ruta del directorio personal del ususario del S.O.
        - Paths.get(...): Crea un objeto path  apuntando a una carpeta llamada enset/pagos dentro del directorio
         */
        Path folderPath = Paths.get(System.getProperty("user.home"),"enset-data","pagos");

        if(!Files.exists(folderPath)){
            Files.createDirectories(folderPath);
        }

        String fileName = UUID.randomUUID().toString();

        //Creamos un Path para el archivo PDF que se guardara en enset/data
        Path filePath = Paths.get(System.getProperty("user.home"),"enset-data","pagos",fileName,".pdf");

        //file.getInputStream(): Obtiene el flujo de datos del archivo recibido desde la solicitud HTTP
        //Files.copy(...): Copia los datos del archivo al destino filePath
        Files.copy(file.getInputStream(),filePath);

        Estudiante estudiante = estudianteRepository.findByCodigo(codigoEstudiante);

        Pago pago = Pago.builder()
                .type(type)
                .status(PagoStatus.CREADO)
                .fecha(date)
                .estudiante(estudiante)
                .cantidad(cantdad)
                .file(filePath.toUri().toString())
                .build();

        return pagoRepository.save(pago);
    }
}
