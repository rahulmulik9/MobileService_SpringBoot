package com.SpringBoot.MobileService.model;

import lombok.Data;

@Data
public class UserLoginRequestModel {
    private String email;
    private String password;
}
