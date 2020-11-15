package com.expexchangeservice.service.interfaces;

import com.expexchangeservice.model.entities.Section;
import com.expexchangeservice.model.entities.Theme;

import java.util.Set;

public interface IDictionaryService {

    Set<Theme> getThemeDictionary();
    Set<Theme> getThemeDictionaryOnTheSection(Section section);
    Set<Section> getSectionDictionary();
    void changeTheme(Theme theme);
    void changeSection(Section section);
    void addTheme(Theme theme);
    void addSection(Section section);
    void deleteTheme(Integer primaryKey);
    void deleteSection(Integer primaryKey);
}
