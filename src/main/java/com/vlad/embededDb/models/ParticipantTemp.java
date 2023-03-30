package com.vlad.embededDb.models;

import lombok.Data;

@Data
public class ParticipantTemp {
    private int id;
    private String name;
    private String wish;
    private Participant recipient;
}
