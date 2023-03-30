package com.vlad.embededDb.repos;

import com.vlad.embededDb.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepo extends JpaRepository<Group,Integer> {
    public Group findByName(String name);
}
