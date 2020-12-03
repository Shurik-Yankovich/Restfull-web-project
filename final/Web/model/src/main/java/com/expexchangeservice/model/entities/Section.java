package com.expexchangeservice.model.entities;

import javax.persistence.*;
import java.util.List;

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
}
