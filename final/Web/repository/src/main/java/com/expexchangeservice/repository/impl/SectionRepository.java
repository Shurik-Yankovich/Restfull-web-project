package com.expexchangeservice.repository.impl;

import com.expexchangeservice.model.entities.Section;
import com.expexchangeservice.repository.interfaces.ISectionRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SectionRepository extends AbstractRepository<Section> implements ISectionRepository {
}
