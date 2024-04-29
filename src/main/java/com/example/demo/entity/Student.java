package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_generator")
    @SequenceGenerator(name = "student_generator", sequenceName = "student_id_seq", allocationSize = 1)

    @Column(name = "id")
    private Long id;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "student_surname")
    private String studentSurname;

    @Column(name = "phone")
    private int phone;

    @Column(name = "age")
    private int age;

    @ManyToOne( cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "group_number")
    private Group group;
}
