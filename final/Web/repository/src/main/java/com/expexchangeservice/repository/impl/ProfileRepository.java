package com.expexchangeservice.repository.impl;

import com.expexchangeservice.model.entities.UserProfile;
import com.expexchangeservice.repository.interfaces.IProfileRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileRepository extends AbstractRepository<UserProfile> implements IProfileRepository {
}
