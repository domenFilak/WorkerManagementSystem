package com.wms.code.worker;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="workers")
public class Worker {

    @Id
    @Column(name="id")
    @NotNull(message = "Worker's id cannot be null.")
    private Long id;
    @Column(name="firstname")
    @NotEmpty(message = "Worker's first name cannot be empty.")
    private String firstName;
    @Column(name="lastname")
    @NotEmpty(message = "Worker's last name cannot be empty.")
    private String lastName;
    @Column(name="email")
    @NotEmpty(message = "Worker's email cannot be empty.")
    private String email;

    public Worker(){

    }

    public Worker(Long id){
        this.id = id;
    }

    public Worker(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
