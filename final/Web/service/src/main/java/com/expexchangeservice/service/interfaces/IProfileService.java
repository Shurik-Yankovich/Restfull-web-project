package com.expexchangeservice.service.interfaces;

import com.expexchangeservice.model.dto.ProfileDto;

public interface IProfileService {

    void addUserProfile(ProfileDto profileDto);
    void changeUserProfile(int profileId, ProfileDto profileDto);
    void deleteUserProfile(int profileId);
}
