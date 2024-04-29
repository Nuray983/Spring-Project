package com.example.demo.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "grouppa")
public class Group implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_generator")
    @SequenceGenerator(name = "group_generator", sequenceName = "grouppa_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "quantity")
    private int quantity;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Student> studentsList = new ArrayList<>();
}
