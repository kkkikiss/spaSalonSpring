package com.vsu.repo;

import com.vsu.entity.SpaSalon;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SpaSalonRepo extends CrudRepository<SpaSalon, Long> {
    @NonNull
    List<SpaSalon> findAll();

    Optional<SpaSalon> findSpaSalonById(Long id);

    Optional<SpaSalon> findSpaSalonsByIdProfile(Long idProfile);

}
