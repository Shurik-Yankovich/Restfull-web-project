package com.expexchangeservice.service.impl;

import com.expexchangeservice.model.dto.LessonDto;
import com.expexchangeservice.model.dto.ProfileDto;
import com.expexchangeservice.model.entities.Lesson;
import com.expexchangeservice.model.entities.User;
import com.expexchangeservice.model.entities.UserProfile;
import com.expexchangeservice.repository.interfaces.IProfileRepository;
import com.expexchangeservice.service.interfaces.IProfileService;
import com.expexchangeservice.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProfileService implements IProfileService {

    private IProfileRepository profileRepository;
    private IUserService userService;

    @Autowired
    public ProfileService(IProfileRepository profileRepository, IUserService userService) {
        this.profileRepository = profileRepository;
        this.userService = userService;
    }

    @Override
    public void addUserProfile(ProfileDto profileDto) {
        UserProfile userProfile = convertDtoToUserProfile(new UserProfile(), profileDto);
        profileRepository.create(userProfile);
    }

    @Override
    public void changeUserProfile(int profileId, ProfileDto profileDto) {
        UserProfile userProfile = convertDtoToUserProfile(profileRepository.read(profileId), profileDto);
        profileRepository.update(convertDtoToUserProfile(userProfile, profileDto));
    }

    @Override
    public void deleteUserProfile(int profileId) {
        UserProfile userProfile = profileRepository.read(profileId);
        profileRepository.delete(userProfile);
    }

    public UserProfile findProfileByUsername(String username) {
        User user = userService.loadUserByUsername(username);
        UserProfile userProfile = profileRepository.findByUser(user);
        return userProfile;
    }

    private UserProfile convertDtoToUserProfile(UserProfile userProfile, ProfileDto profileDto) {
        userProfile.setId(userProfile.getId());
        userProfile.setFullName(profileDto.getFullName());
        userProfile.setUser(userService.loadUserByUsername(profileDto.getUsername()));
        userProfile.setPlaceOfWork(profileDto.getPlaceOfWork());
        return userProfile;
    }

    private ProfileDto convertUserProfileToDto(UserProfile userProfile) {
        ProfileDto profileDto = new ProfileDto();
        profileDto.setId(userProfile.getId());
        profileDto.setFullName(userProfile.getFullName());
        profileDto.setUsername(userProfile.getUser().getUsername());
        profileDto.setPlaceOfWork(userProfile.getPlaceOfWork());
        return profileDto;
    }

    private List<ProfileDto> convertProfileListToDtoList(List<UserProfile> profiles) {
        List<ProfileDto> profileDtoList = new ArrayList<>();
        for (UserProfile profile : profiles) {
            profileDtoList.add(convertUserProfileToDto(profile));
        }
        return profileDtoList;
    }
}
