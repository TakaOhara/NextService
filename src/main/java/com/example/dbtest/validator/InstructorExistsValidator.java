package com.example.dbtest.validator;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.dbtest.domain.entity.Instructor;
import com.example.dbtest.domain.repositories.InstructorRepository;

public class InstructorExistsValidator implements ConstraintValidator<InstructorExists, String> {

    @Autowired
    InstructorRepository instructorRepository;

    @Override
    public void initialize(InstructorExists annotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) return true;

        Optional<Instructor> inst = instructorRepository.findByEmail(value);

        return !inst.isPresent();

    }
}
