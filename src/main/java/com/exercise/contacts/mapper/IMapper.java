package com.exercise.contacts.mapper;

public interface IMapper<I, O> {
    O map(I in);

}
