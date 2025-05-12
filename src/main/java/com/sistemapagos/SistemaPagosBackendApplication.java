package com.sistemapagos;

import com.sistemapagos.entities.Estudiante;
import com.sistemapagos.entities.Pago;
import com.sistemapagos.enums.PagoStatus;
import com.sistemapagos.enums.TypePago;
import com.sistemapagos.repository.EstudianteRepository;
import com.sistemapagos.repository.PagoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class SistemaPagosBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaPagosBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(EstudianteRepository estudianteRepository, PagoRepository pagoRepository){
		return args -> {
			estudianteRepository.save(Estudiante.builder()
					.id(UUID.randomUUID().toString())
					.nombre("Jorge")
					.apellido("Bullon")
					.codigo("134")
					.programaId("LTA1")
					.build());

			estudianteRepository.save(Estudiante.builder()
					.id(UUID.randomUUID().toString())
					.nombre("Jhin")
					.apellido("Rojas")
					.codigo("1345")
					.programaId("LTA1")
					.build());

			estudianteRepository.save(Estudiante.builder()
					.id(UUID.randomUUID().toString())
					.nombre("Diego")
					.apellido("Vasquez")
					.codigo("12")
					.programaId("LTA2")
					.build());

			estudianteRepository.save(Estudiante.builder()
					.id(UUID.randomUUID().toString())
					.nombre("Aldo")
					.apellido("Alegria")
					.codigo("124475")
					.programaId("LTA3")
					.build());

			TypePago tiposPago[] = TypePago.values();
			Random random = new Random();

			estudianteRepository.findAll().forEach(estudiante -> {
				for(int i = 0; i < 10; i++){
					int index = random.nextInt(tiposPago.length);
					Pago pago = Pago.builder()
							.cantidad(1000+(int)(Math.random()*20000))
							.type(tiposPago[index])
							.status(PagoStatus.CREADO)
							.fecha(LocalDate.now())
							.estudiante(estudiante)
							.build();
					pagoRepository.save(pago);
				}
			});
		};
	}
}
