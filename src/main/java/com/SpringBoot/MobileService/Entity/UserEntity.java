package com.SpringBoot.MobileService.Entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "mobileserive")
@Data
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 6336991588005433929L;
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    //you can use here like this  nullable = false, length = 120,unique = true
    //so this unique check if email is present or not.It will throw error...
    //this error cant be handle
    //so better to check at in method so we can handle exception
    @Column(nullable = false, length = 120,unique = true)
    private String email;

    @Column(nullable = false)
    private String encryptedPassword;

    private String emailVerificationToken;

    @Column(nullable = false)
    private Boolean emailVerificationStatus=false;

//    @OneToMany(mappedBy = "UserDetails",cascade = CascadeType.ALL)
//    private List<AddressEntity> addresses;


}
