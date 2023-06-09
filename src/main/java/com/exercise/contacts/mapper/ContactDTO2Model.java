package com.exercise.contacts.mapper;

import com.exercise.contacts.dto.ContactDTO;
import com.exercise.contacts.models.ContactModel;
import org.springframework.stereotype.Component;

@Component
public class ContactDTO2Model implements IMapper<ContactDTO, ContactModel>{

    @Override
    public ContactModel map(ContactDTO contactDTO){
        ContactModel contactModel = new ContactModel();
        if(contactDTO.getId() != null)
            contactModel.setId(contactDTO.getId());
        contactModel.setFirstName(contactDTO.getFirstName());
        contactModel.setSecondName(contactDTO.getSecondName());
        contactModel.setBirthDate(contactDTO.getBirthDate());
        contactModel.setAddresses(contactDTO.getAddresses());
        contactModel.setPhones(contactDTO.getPhones());
        return contactModel;
    }

}
