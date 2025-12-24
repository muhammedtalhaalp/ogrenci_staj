package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "isveren")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Isveren {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "kullanici_id", referencedColumnName = "id", nullable = false, unique = true)
    private Kullanici kullanici;

    @ManyToOne
    @JoinColumn(name = "firma_id", referencedColumnName = "id", nullable = false)
    private Firma firma;

    private String ad;

    private String soyad;
}
