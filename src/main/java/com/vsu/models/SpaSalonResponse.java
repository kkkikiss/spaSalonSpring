package com.vsu.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpaSalonResponse {
    private final Long id;
    private final String nameSalon;
    private final int cost;
    private final Long idProfile;
}
