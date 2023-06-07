package com.exercise.contacts.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Filter {

    private String firstName;
    private String secondName;

    private Integer from;
    private Integer to;

}
