package com.vlad.embededDb.services;

import com.vlad.embededDb.models.Group;
import com.vlad.embededDb.models.Participant;
import com.vlad.embededDb.repos.GroupRepo;
import com.vlad.embededDb.repos.ParticipantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {
    @Autowired
    private GroupRepo groupRepo;
    @Autowired
    private ParticipantRepo participantRepo;

    public Group addGroup(Group group){
        if(group.getName().equals("")){
            return null;
        }
        return groupRepo.save(group);
    }
    public List<Group> getGroups(){
        List<Group> groups=groupRepo.findAll();
        return groupRepo.findAll();
    }
    public Group getGroup(int id){
        Group group=groupRepo.findById(id).orElse(null);
        return group;
    }
    public Group updateGroup(int id,Group group){
        Group group1=groupRepo.findById(id).orElse(null);
        if(group1!=null){
            group1.setName(group.getName());
            group1.setDescription(group.getDescription());
            if(group.getName().equals("")){
                return null;
            }
            return groupRepo.save(group1);
        }
        return null;
    }
    public void deleteGroup(int id){
        groupRepo.deleteById(id);
    }
    public int addParticipant(int id,Participant participant){
        Group group1=groupRepo.findById(id).orElse(null);
        if (group1 != null) {
            Participant created=participantRepo.save(participant);
            group1.getParticipants().add(participant);
            groupRepo.save(group1);
            return created.getId();
        }
        return -1;
    }
    public void deleteParticipant(int groupId,int pId){
        Group group=groupRepo.findById(groupId).orElse(null);
        Participant participant=participantRepo.findById(pId).orElse(null);
        if(group!=null&group.getParticipants().contains(participant)){
            group.getParticipants().remove(participant);
            groupRepo.save(group);
        }
    }
    public List<Participant> toss(int id){
        Group group1=groupRepo.findById(id).orElse(null);
        if(group1!=null&&group1.getParticipants().size()>=3){
            List<Participant> people=group1.getParticipants();
            List<Participant> temp=new ArrayList<>();
            people.get(group1.getParticipants().size()-1).setRecipient(people.get(0));
            for(int i=0;i<group1.getParticipants().size()-1;i++){
                if(!temp.contains(people.get(i))){
                    Participant participant=people.get(i);
                    people.get(i).setRecipient(people.get(i+1));
                    participant.setRecipient(people.get(i+1));
                    participantRepo.save(participant);
                    temp.add(people.get(i));

                }
            }

            group1.setParticipants(people);
//            groupRepo.save(group1);
            return group1.getParticipants();
        }

        return null;
    }
    public Participant getRecipient(int id){
        return participantRepo.findById(id).orElse(null);
    }
}
