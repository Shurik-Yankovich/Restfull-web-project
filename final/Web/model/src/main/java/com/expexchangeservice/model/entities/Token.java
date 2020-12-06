package com.expexchangeservice.model.entities;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return id == token.id &&
                Objects.equals(value, token.value) &&
                Objects.equals(user, token.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, user);
    }
}
