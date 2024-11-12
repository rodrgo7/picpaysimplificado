package com.oliveiradev.picpaysimplificado.dtos;

import java.math.BigDecimal;

import com.oliveiradev.picpaysimplificado.domain.user.UserType;

public record UserDTO(String firstName, String lastName, String document, BigDecimal balance, String email, String password, UserType userType) {
    
}
