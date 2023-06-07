package com.exercise.contacts.controllers;

import com.exercise.contacts.models.ContactModel;
import com.exercise.contacts.services.ContactsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/contacts")
public class ContactsController {

    private final ContactsService contactsService;

    public ContactsController(ContactsService contactsService) {
        this.contactsService = contactsService;
    }

    //CRUD features
    @PostMapping
    public ResponseEntity<ContactModel> create(@RequestBody ContactModel contactModel){
        return ResponseEntity.ok(contactsService.save(contactModel));
    }

    @GetMapping
    public ResponseEntity<List<ContactModel>> retrieveAll(){
        return ResponseEntity.ok(contactsService.retrieveAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactModel> findById(@PathVariable Long id){
        return ResponseEntity.ok(contactsService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        contactsService.deleteById(id);
        return ResponseEntity.ok("Contact deleted successfully");
    }

    @PutMapping
    public ResponseEntity<ContactModel> update(@RequestBody ContactModel contactModel){
        return ResponseEntity.ok(contactsService.save(contactModel));
    }


}
