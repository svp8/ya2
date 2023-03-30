package com.vlad.embededDb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Participant {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    private String wish;
    @JsonIgnore
    @OneToOne()
    @JoinColumn(name = "recipient")
    private Participant recipient;
}
