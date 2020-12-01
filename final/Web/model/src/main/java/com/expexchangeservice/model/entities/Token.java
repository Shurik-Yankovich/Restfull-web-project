package com.expexchangeservice.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "Token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "value")
    private String value;

    @OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;

    public Token(String tokenKey, User user) {
        this.value = tokenKey;
        this.user = user;
    }

    public String getValue() {
        return value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

//public class Token {
//
//    private String token;
//
//    public Token(){}
//
//    public Token(String tokenBody){
//        this.token = tokenBody;
//    }
//
//    public String getValue() {
//        return token;
//    }
//
//    public void setValue(String token) {
//        this.token = token;
//    }
//}
