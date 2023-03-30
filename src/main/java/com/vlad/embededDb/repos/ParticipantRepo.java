package com.vlad.embededDb.repos;

import com.vlad.embededDb.models.Group;
import com.vlad.embededDb.models.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepo extends JpaRepository<Participant,Integer> {

}
