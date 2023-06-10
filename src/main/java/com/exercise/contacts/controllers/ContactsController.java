package com.exercise.contacts.controllers;

import com.exercise.contacts.dto.ContactDTO;
import com.exercise.contacts.exception.ResourceException;
import com.exercise.contacts.models.*;
import com.exercise.contacts.services.ContactsService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/v1/api/contacts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ContactsController {

    private final ContactsService contactsService;

    public ContactsController(ContactsService contactsService) {
        this.contactsService = contactsService;
    }

    //CRUD features
    @PostMapping
    public ResponseEntity<ContactID> create(@RequestBody ContactDTO contactDTO) {
        ContactID contactID = new ContactID(contactsService.saveController(contactDTO).getId());
        return ResponseEntity.ok(contactID);
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody ContactDTO contactDTO) {
        return ResponseEntity.ok("Successfully updated");
    }

    @GetMapping
    public ResponseEntity<List<ContactModel>> retrieveAll(){
        return ResponseEntity.ok(contactsService.retrieveAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactModel> findById(@PathVariable Long id){
        return ResponseEntity.ok(contactsService.findById(id));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteById(@RequestBody IDs ids){
        contactsService.deleteContactsByIds(ids.getIds());
        return ResponseEntity.ok("Contact deleted successfully");
    }

   //Search feature
    @GetMapping("/search")
    public List<ContactModel> searchContacts(@RequestParam(required = false) String firstName,
                                             @RequestParam(required = false) String secondName,
                                             @RequestParam(required = false) Integer from,
                                             @RequestParam(required = false) Integer to,
                                             @RequestBody(required = false) Address address) {
        if(from == null && to == null && firstName == null && secondName == null && address == null) throw new ResourceException("Insufficient arguments to perform the search", HttpStatus.BAD_REQUEST);
        if(from != null && to != null && from > to) throw new ResourceException("From is greater than To", HttpStatus.BAD_REQUEST);
        return contactsService.search(firstName, secondName, address, from, to);
    }

    @PostMapping("/upload/{id}")
    public Boolean handleFileUpload(@PathVariable Long id,
                                   @RequestParam("file") MultipartFile file) throws IOException {
        contactsService.savePhoto(file, id);
        return true;
    }

    @PostMapping("/download")
    public ResponseEntity<InputStreamResource> obtenerImagen(@RequestBody PathPic pathPic) throws IOException {
        File imagen = new File(pathPic.getPath());
        InputStream in = Files.newInputStream(imagen.toPath());// carga la imagen como un InputStream
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(imagen.length());
        return new ResponseEntity<>(new InputStreamResource(in), headers, HttpStatus.OK);
    }
}


