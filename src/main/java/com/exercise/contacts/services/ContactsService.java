package com.exercise.contacts.services;

import com.exercise.contacts.dto.ContactDTO;
import com.exercise.contacts.exception.ResourceException;
import com.exercise.contacts.mapper.ContactDTO2Model;
import com.exercise.contacts.models.Address;
import com.exercise.contacts.models.ContactModel;
import com.exercise.contacts.repositories.ContactsRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class ContactsService {
    @Value("${upload.directory}")
    private String uploadDirectory;
    private static final String ADDRESSES = "addresses";
    private static final String BIRTHDATE = "birthDate";
    private final ContactsRepository contactsRepository;
    private final ContactDTO2Model contactDTO2Model;
    public ContactsService(ContactsRepository contactsRepository, ContactDTO2Model contactDTO2Model) {
        this.contactsRepository = contactsRepository;
        this.contactDTO2Model = contactDTO2Model;
    }

    //CRUD features
    public ContactModel saveController(ContactDTO contactDTO) {
        ContactModel contactModel = contactDTO2Model.map(contactDTO);
        return save(contactModel);
    }

    public ContactModel save(ContactModel contactModel) {
        return contactsRepository.save(contactModel);
    }

    public List<ContactModel> retrieveAll(){
        return (List<ContactModel>) contactsRepository.findAll();
    }

    public ContactModel findById(Long id){
        return contactsRepository.findById(id).orElseThrow(
                () -> new ResourceException("There is no contact with that id.", HttpStatus.NOT_FOUND)
        );
    }

    public void deleteContactsByIds(List<Long> ids) throws ResourceException {
        List<Long> failedIds = new ArrayList<>();
        Stream<ContactModel> stream = StreamSupport.stream(contactsRepository.findAllById(ids).spliterator(), false);
        List<Long> existingIds = stream.map(ContactModel::getId).collect(Collectors.toList());
        contactsRepository.deleteAllById(existingIds);
        for (Long id : ids) {
            if (!existingIds.contains(id)) {
                failedIds.add(id);
                if (!failedIds.isEmpty()) {
                    // Si algunos IDs fallaron, lanza una excepción personalizada con los IDs fallidos
                    throw new ResourceException("Failed to delete all ids: " + failedIds.toString(), HttpStatus.NOT_FOUND);
                }
            }
        }
    }

    //Search feature
    public List<ContactModel> search(String firstName,
                                     String secondName,
                                     Address address,
                                     Integer from,
                                     Integer to){
        List<ContactModel> searchResult = contactsRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (firstName != null) {
                predicates.add(cb.equal(root.get("firstName"), firstName));
            }
            if (secondName != null) {
                predicates.add(cb.equal(root.get("secondName"), secondName));
            }
            if (from != null && to != null) {
                Calendar calendarTo = Calendar.getInstance();
                calendarTo.add(Calendar.YEAR, -to);
                Date maxBirthDate = calendarTo.getTime();

                Calendar calendarFrom = Calendar.getInstance();
                calendarFrom.add(Calendar.YEAR, -from);
                Date minBirthDate = calendarFrom.getTime();
                predicates.add(cb.between(root.get(BIRTHDATE), maxBirthDate, minBirthDate));
            }
            else if (from != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.YEAR, -from);
                Date minBirthDate = calendar.getTime();
                predicates.add(cb.lessThanOrEqualTo(root.get(BIRTHDATE), minBirthDate));
            }
            else if (to != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.YEAR, -to);
                Date maxBirthDate = calendar.getTime();
                predicates.add(cb.greaterThanOrEqualTo(root.get(BIRTHDATE), maxBirthDate));
            }
            Join<ContactModel, Address> addressJoin = root.join(ADDRESSES);
            if (address.getName() != null) {
                predicates.add(cb.like(addressJoin.get("name"), address.getName()));
            }
            if (address.getCountry() != null) {
                predicates.add(cb.like(addressJoin.get("country"), address.getCountry()));
            }
            if (address.getState() != null) {
                predicates.add(cb.like(addressJoin.get("state"), address.getState()));
            }
            if (address.getCity() != null) {
                predicates.add(cb.like(addressJoin.get("city"), address.getCity()));
            }
            if (address.getStreet() != null) {
                predicates.add(cb.like(addressJoin.get("street"), address.getStreet()));
            }
            if (address.getZip() != null) {
                predicates.add(cb.like(addressJoin.get("zip"), address.getZip()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
        if (searchResult.isEmpty()) throw new ResourceException("There are no results for those search elements", HttpStatus.NOT_FOUND);
        return searchResult;
    }

    public void savePhoto(MultipartFile file, Long id) throws IOException {
        ContactModel contactModel = findById(id);
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String fileExtension = FilenameUtils.getExtension(fileName);
            assert fileExtension != null;
            if (fileExtension.equals("jpg") || fileExtension.equals("jpeg")) {
                if(contactModel.getPhoto() != null ) {
                    File photo = new File(contactModel.getPhoto());
                    if (photo.exists()) {
                        // Borra el archivo
                        photo.delete();
                    }
                }
                File tempFile = File.createTempFile("photo-" + id.toString() + "-", "." + fileExtension, new File(uploadDirectory));
                FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);
                contactModel.setPhoto(tempFile.getPath());
                save(contactModel);
            }
            else
                throw new ResourceException("The file is not a jpg or jpeg", HttpStatus.BAD_REQUEST);
        }
        else
            throw new ResourceException("Empty file", HttpStatus.BAD_REQUEST);
    }

}
