package com.vlad.embededDb.controllers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vlad.embededDb.models.Group;
import com.vlad.embededDb.models.Participant;
import com.vlad.embededDb.models.ParticipantTemp;
import com.vlad.embededDb.services.GroupService;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class GroupController {
    @Autowired
    GroupService groupService;

    @GetMapping("/group/{id}")
    public ResponseEntity getById(@PathVariable int id) {
        Group group = groupService.getGroup(id);

        if (group != null) {
            GroupTemp gr = new GroupTemp();
            gr.setId(group.getId());
            gr.setName(group.getName());
            gr.setDescription(group.getDescription());
            List<ParticipantTemp> participantTempList = new ArrayList<>();
            for (Participant p : group.getParticipants()) {
                ParticipantTemp temp = new ParticipantTemp();
                temp.setId(p.getId());
                temp.setName(p.getName());
                temp.setRecipient(p.getRecipient());
                temp.setWish(p.getWish());
                participantTempList.add(temp);
            }
            gr.setParticipants(participantTempList);
            return ResponseEntity.ok(gr);
        }
        return ResponseEntity.badRequest().body("");
    }

    @GetMapping("/groups")
    public ResponseEntity<?> getAll() {
        List<Group> groups = groupService.getGroups();
        if (groups != null) {
//            List<GroupTemp> groupTemps=new ArrayList<>();
//            for(int i=0;i<groups.size();i++){
//                Group group=groups.get(0);
//                GroupTemp gr=new GroupTemp();
//                gr.setId(group.getId());
//                gr.setName(group.getName());
//                gr.setDescription(group.getDescription());
//                List<ParticipantTemp> participantTempList=new ArrayList<>();
//                for (Participant p:group.getParticipants()) {
//                    ParticipantTemp temp=new ParticipantTemp();
//                    temp.setId(p.getId());
//                    temp.setName(p.getName());
//                    temp.setRecipient(p.getRecipient());
//                    temp.setWish(p.getWish());
//                    participantTempList.add(temp);
//                }
//                gr.setParticipants(participantTempList);
//                groupTemps.add(gr);
//            }
            return ResponseEntity.ok(groups);
        }
        return ResponseEntity.badRequest().body("");
    }

    @PostMapping("/group")
    public ResponseEntity<?> postGroup(@RequestBody Group group) {
        Group group1 = groupService.addGroup(group);
        if (group1 != null) {
            return ResponseEntity.ok(group1.getId());
        }
        return ResponseEntity.badRequest().body("");
    }

    @PutMapping("/group/{id}")
    public ResponseEntity<?> putGroup(@PathVariable int id, @RequestBody Group group) {
        Group group1 = groupService.updateGroup(id, group);
        if (group1 != null) {

            return ResponseEntity.ok(group1);
        }
        return ResponseEntity.badRequest().body("");
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable int id) {
        groupService.deleteGroup(id);
        return ResponseEntity.ok("");
    }

    @PostMapping("/group/{id}/participant")
    public ResponseEntity<?> postParticipant(@PathVariable int id, @RequestBody Participant participant) {
        int i = groupService.addParticipant(id, participant);
        if (i != -1) {
            return ResponseEntity.ok(i);
        }
        return ResponseEntity.badRequest().body("");
    }

    @DeleteMapping("/group/{id}/participant/{participantId}")
    public ResponseEntity<?> deleteParticipant(@PathVariable int id, @PathVariable int participantId) {
        groupService.deleteParticipant(id, participantId);
        return ResponseEntity.ok("");
    }

    @PostMapping("/group/{id}/toss")
    public ResponseEntity<?> toss(@PathVariable int id) {
        List<Participant> participants = groupService.toss(id);
        List<ParticipantTemp> participantsTemp = new ArrayList<>();
        if (participants != null) {
            for (Participant p : participants) {
                ParticipantTemp temp = new ParticipantTemp();
                temp.setId(p.getId());
                temp.setName(p.getName());
                temp.setRecipient(p.getRecipient());
                temp.setWish(p.getWish());
                participantsTemp.add(temp);
            }
            return ResponseEntity.ok(participantsTemp);
        }
        return ResponseEntity.badRequest().body("");
    }

    @GetMapping("/group/{groupId}/participant/{participantId}/recipient")
    public ResponseEntity<?> getRecipient(@PathVariable int participantId) {
        Participant participant = groupService.getRecipient(participantId);
        if (participant != null) {
            return ResponseEntity.ok(participant);
        }
        return ResponseEntity.badRequest().body("");
    }

    @Data
    public class GroupTemp {
        private int id;
        private String name;
        private String description;
        private List<ParticipantTemp> participants = new ArrayList<>();
    }
}
