package com.expexchangeservice.model.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Section")
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title")
    private String title;
    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "Section_Themes",
            joinColumns = @JoinColumn(name = "id_section"),
            inverseJoinColumns = @JoinColumn(name = "id_theme"))
    private List<Theme> themes;

    public Section(String title) {
        this.title = title;
    }

    public Section() {
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

    public List<Theme> getThemes() {
        return themes;
    }

    public void setThemes(List<Theme> themes) {
        this.themes = themes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return id == section.id &&
                Objects.equals(title, section.title) &&
                Objects.equals(themes, section.themes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, themes);
    }
}
