package com.exercise.contacts.repositories;

import com.exercise.contacts.models.ContactModel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactsRepository extends CrudRepository<ContactModel, Long>, JpaSpecificationExecutor<ContactModel> {

}
