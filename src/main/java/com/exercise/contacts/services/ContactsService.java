package com.exercise.contacts.services;

import com.exercise.contacts.models.ContactModel;
import com.exercise.contacts.repositories.ContactsRepository;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

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

    public void deleteById(Long id){
        contactsRepository.deleteById(id);
    }

    //Search feature
    public List<ContactModel> search(String firstName,
                                     String secondName,
                                     String kindAddress,
                                     String address,
                                     Integer from,
                                     Integer to){
        return contactsRepository.findAll((root, query, cb) -> {
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

                predicates.add(cb.between(root.get("birthDate"), maxBirthDate, minBirthDate));
            }
            else if (from != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.YEAR, -from);
                Date minBirthDate = calendar.getTime();

                predicates.add(cb.lessThanOrEqualTo(root.get("birthDate"), minBirthDate));
            }
            else if (to != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.YEAR, -to);
                Date maxBirthDate = calendar.getTime();

                predicates.add(cb.greaterThanOrEqualTo(root.get("birthDate"), maxBirthDate));
            }
            if (address != null) {
                root.join("addresses").get("values");
                predicates.add(cb.like(root.join("addresses").get("values"), "%" + address + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }

}
