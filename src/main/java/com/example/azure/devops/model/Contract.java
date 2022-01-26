package com.example.azure.devops.model;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name="contract")
@NoArgsConstructor
@AllArgsConstructor
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    String name;
}
