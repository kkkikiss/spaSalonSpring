package com.vsu.service;

import com.vsu.entity.Profile;
import com.vsu.exceptions.NotFound;
import com.vsu.exceptions.ProfileAlreadyExists;
import com.vsu.mapping.ProfileMapper;
import com.vsu.models.ProfileRequest;
import com.vsu.models.ProfileResponse;
import com.vsu.repo.ProfileRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProfileService {
    private static final String PROFILE_WITH_LOGIN_ALREADY_EXISTS = "Profile with login '%s' already exists";
    private static final String PROFILE_WITH_ID_NOT_FOUND = "Profile with id '%d' not found";
    private static final String PROFILE_WITH_LOGIN_NOT_FOUND = "Profile with login '%s' not found";

    private final ProfileRepo profileRepo;
    private final ProfileMapper profileMapper;

    private final PasswordEncoder passwordEncoder;


    public ProfileService(ProfileRepo profileRepo, ProfileMapper profileMapper, PasswordEncoder passwordEncoder) {
        this.profileRepo = profileRepo;
        this.profileMapper = profileMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<ProfileResponse> getAll(String login) {
        if (login == null) {
            return profileMapper.mapToProfileResponseList(profileRepo.findAll());
        }
        Profile profile = profileRepo.findProfileByLogin(login).orElseThrow(
                () -> new NotFound(PROFILE_WITH_LOGIN_NOT_FOUND.formatted(login))
        );
        return profileMapper.mapToProfileResponseList(profile);
    }

    public ProfileResponse getById(Long id) {
        Profile profile = profileRepo.findProfileById(id).orElseThrow(
                () -> new NotFound(PROFILE_WITH_ID_NOT_FOUND.formatted(id))
        );
        return profileMapper.mapToProfileResponse(profile);
    }

    public ProfileResponse insert(ProfileRequest profileRequest) {
        String login = profileRequest.getLogin();
        if (profileRepo.existsByLogin(login)) {
            throw new ProfileAlreadyExists(PROFILE_WITH_LOGIN_ALREADY_EXISTS.formatted(login));
        }
        Profile profile = getProfileWithEncodedPassword(profileRequest);
        Profile result = profileRepo.save(profile);
        return profileMapper.mapToProfileResponse(result);
    }

    public void delete(Long id) {
        if (!profileRepo.existsById(id)) {
            throw new NotFound(PROFILE_WITH_ID_NOT_FOUND.formatted(id));
        }
        profileRepo.deleteById(id);
    }

    public ProfileResponse update(Long id, ProfileRequest profileRequest) {
        if (!profileRepo.existsById(id)) {
            throw new NotFound(PROFILE_WITH_ID_NOT_FOUND.formatted(id));
        }
        String login = profileRequest.getLogin();
        Optional<Profile> profileWithSameLogin = profileRepo.findProfileByLogin(login);
        if (profileWithSameLogin.isPresent() && !Objects.equals(profileWithSameLogin.get().getId(), id)) {
            throw new ProfileAlreadyExists(PROFILE_WITH_LOGIN_ALREADY_EXISTS.formatted(login));
        }
        Profile profile = getProfileWithEncodedPasswordAndId(id, profileRequest);
        Profile result = profileRepo.save(profile);
        return profileMapper.mapToProfileResponse(result);
    }

    private Profile getProfileWithEncodedPassword(ProfileRequest profileRequest) {
        Profile profile = profileMapper.mapToProfile(profileRequest);
        profile.setPassword(passwordEncoder.encode(profile.getPassword()));
        return profile;
    }

    private Profile getProfileWithEncodedPasswordAndId(Long id, ProfileRequest profileRequest) {
        Profile profile = profileMapper.mapToProfile(id, profileRequest);
        profile.setPassword(passwordEncoder.encode(profile.getPassword()));
        return profile;
    }
}
