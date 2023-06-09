package com.exercise.contacts.dto;

import com.exercise.contacts.models.Address;
import com.exercise.contacts.models.Phone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContactDTO {
    private Long id;
    private String firstName;
    private String secondName;
    private Date birthDate;
    private List<Address> addresses;
    private List<Phone> phones;
    private String photo;
}
