package com.vsu.validation;

import com.vsu.repo.ProfileRepo;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidator;

public class ProfileExistsValidator implements ConstraintValidator<ProfileExists, Long> {
    private final ProfileRepo profileRepo;

    public ProfileExistsValidator(ProfileRepo profileRepo) {
        this.profileRepo = profileRepo;
    }

    @Override
    public boolean isValid(Long idProfile, ConstraintValidatorContext context) {
        if (idProfile == null) {
            return false;
        }
        return profileRepo.existsById(idProfile);
    }
}
