package com.expexchangeservice.repository.impl;

import com.expexchangeservice.model.entities.Theme;
import com.expexchangeservice.repository.interfaces.IThemeRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ThemeRepository extends AbstractRepository<Theme> implements IThemeRepository {
}
