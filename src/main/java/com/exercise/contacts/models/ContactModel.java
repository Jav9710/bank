package com.exercise.contacts.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    @MapKeyColumn(name = "address")
    @Column(name = "address")
    @CollectionTable(name = "address_contacts", joinColumns = @JoinColumn(name = "id"))
    private List<Address> addresses;

    @ElementCollection
    @CollectionTable(name = "contacts_phones", joinColumns = @JoinColumn(name = "id"))
    @MapKeyColumn(name = "phones_number")
    @Column(name = "phones_number")
    private List<Phone> phones;
    private String photo;

}
