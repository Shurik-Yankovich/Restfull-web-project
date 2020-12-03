package com.expexchangeservice.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "Token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "value")
    private String value;

    @OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;

    public Token(String tokenKey, User user) {
        this.value = tokenKey;
        this.user = user;
    }

    public Token() {
    }

    public String getValue() {
        return value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
