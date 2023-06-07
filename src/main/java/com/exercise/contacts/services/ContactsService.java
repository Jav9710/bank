package com.exercise.contacts.services;

import com.exercise.contacts.models.ContactModel;
import com.exercise.contacts.models.Filter;
import com.exercise.contacts.repositories.ContactsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactsService {

    private final ContactsRepository contactsRepository;

    public ContactsService(ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
    }

    //CRUD features
    public ContactModel save(ContactModel contactModel){
        return contactsRepository.save(contactModel);
    }

    public List<ContactModel> retrieveAll(){
        return (List<ContactModel>) contactsRepository.findAll();
    }

    public ContactModel findById(Long id){
        return contactsRepository.findById(id).get();
    }

    public Void deleteById(Long id){
        contactsRepository.deleteById(id);
    }



}
