package com.expexchangeservice.model.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Theme")
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title")
    private String title;
//    @ManyToOne (optional=false)
//    @JoinColumn (name="id_section")
//    private Section section;

    public Theme(String title) {
        this.title = title;
    }

    public Theme() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Theme theme = (Theme) o;
        return id == theme.id &&
                Objects.equals(title, theme.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
