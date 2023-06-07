package com.exercise.contacts.repositories;

import com.exercise.contacts.models.ContactModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactsRepository extends CrudRepository<ContactModel, Long> {

    ContactModel findByFirstNameAndSecondName(String firstName, String secondName);
    List<ContactModel> findByFirstNameOrSecondName(String firstName, String secondName);

}
