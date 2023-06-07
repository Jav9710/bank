package com.exercise.contacts.repositories;

import com.exercise.contacts.models.ContactModel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContactsRepository extends CrudRepository<ContactModel, Long>, JpaSpecificationExecutor<ContactModel> {

    ContactModel findByFirstNameAndSecondName(String firstName, String secondName);
    List<ContactModel> findByFirstNameOrSecondName(String firstName, String secondName);
    List<ContactModel> searchByFirstName(String firstName);
    List<ContactModel> searchBySecondName(String firstName);
    List<ContactModel> searchByFirstNameAndSecondName(String firstName, String secondName);
    List<ContactModel> findByBirthDateBetween(LocalDate fromDate, LocalDate toDate);
    List<ContactModel> findByBirthDateBefore(LocalDate toDate);
    List<ContactModel> findByBirthDateAfter(LocalDate fromDate);
}
