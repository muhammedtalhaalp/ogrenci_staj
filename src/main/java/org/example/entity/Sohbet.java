package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sohbet")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sohbet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ogrenci_id", referencedColumnName = "id", nullable = false)
    private Ogrenci ogrenci;

    @ManyToOne
    @JoinColumn(name = "isveren_id", referencedColumnName = "id", nullable = false)
    private Isveren isveren;

    @ManyToOne
    @JoinColumn(name = "staj_ilani_id", referencedColumnName = "id")
    private StajIlani stajIlani;
}
