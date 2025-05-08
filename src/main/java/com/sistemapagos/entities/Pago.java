package com.sistemapagos.entities;

import com.sistemapagos.enums.PagoStatus;
import com.sistemapagos.enums.TypePago;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private LocalDate fecha;

    private double cantidad;

    private TypePago type;

    private PagoStatus status;

    private String file;

    @ManyToOne
    private Estudiante estudiante;
}
