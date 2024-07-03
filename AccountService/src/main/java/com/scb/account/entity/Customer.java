package com.scb.account.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Size(min = 4, max = 30, message = "Name should contain at least 4 characters.")
    private String name;

    @NotEmpty
    @Email
    private String email;

    private Long accountId;

    @NotNull
    @Size(min = 6, message = "Password should be minimum 6 characters length !!")
    private String password;

    private String gender;
    private LocalDate bdate;

    private String branch;

    private String city;

    private String state;

    @Transient
    private Account account;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", accountId=" + accountId +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", bdate=" + bdate +
                ", branch='" + branch + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", account=" + account +
                '}';
    }
}
