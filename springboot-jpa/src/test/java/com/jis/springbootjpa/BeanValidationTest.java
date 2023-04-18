package com.jis.springbootjpa;

import com.jis.springbootjpa.domain.FileItem;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.Assert.*;

public class BeanValidationTest {


    @Test
    public void fileItemBeanValidation(){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        FileItem fileItem = new FileItem();
        fileItem.setItemName("");


        Set<ConstraintViolation<FileItem>>  violations = validator.validate(fileItem);

        for(ConstraintViolation<FileItem> violation : violations){
            System.out.println("violation : " + violation);
            System.out.println("violation msg: " + violation.getMessage());

        }


    }
}