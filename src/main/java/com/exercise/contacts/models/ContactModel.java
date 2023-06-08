package com.exercise.contacts.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContactModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String secondName;
    private Date birthDate;
    @ElementCollection
    @MapKeyColumn(name = "address_name")
    @Column(name = "address")
    @CollectionTable(name = "address_contacts", joinColumns = @JoinColumn(name = "user_id"))
        private List<HashMap<String, String>> addresses;

    @ElementCollection
    @CollectionTable(name = "contacts_phones", joinColumns = @JoinColumn(name = "person_id"))
    @MapKeyColumn(name = "phone_name")
    @Column(name = "phone_number")
    private List<Addresses> phones;
    private String photo;

}
