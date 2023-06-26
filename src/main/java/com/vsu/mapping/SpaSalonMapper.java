package com.vsu.mapping;

import com.vsu.entity.SpaSalon;
import com.vsu.models.SpaSalonRequest;
import com.vsu.models.SpaSalonResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SpaSalonMapper {
    public SpaSalonResponse mapToSalonResponse(SpaSalon salon) {
        return SpaSalonResponse
                .builder()
                .id(salon.getId())
                .nameSalon(salon.getNameSalon())
                .cost(salon.getCost())
                .idProfile(salon.getIdProfile())
                .build();
    }

    public SpaSalon mapToSpaSalon(SpaSalonRequest salonRequest) {
        return SpaSalon
                .builder()
                .nameSalon(salonRequest.getNameSalon())
                .cost(salonRequest.getCost())
                .idProfile(salonRequest.getIdProfile())
                .build();
    }

    public SpaSalon mapToSpaSalon(Long id, SpaSalonRequest salonRequest) {
        return SpaSalon
                .builder()
                .id(id)
                .nameSalon(salonRequest.getNameSalon())
                .cost(salonRequest.getCost())
                .idProfile(salonRequest.getIdProfile())
                .build();
    }

    public List<SpaSalonResponse> mapToToadResponseList(List<SpaSalon> spaSalons) {
        return spaSalons
                .stream()
                .map(this::mapToSalonResponse)
                .collect(Collectors.toList());
    }
}
