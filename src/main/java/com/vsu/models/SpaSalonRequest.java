package com.vsu.models;

import com.vsu.validation.ProfileExists;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Date;

@Data
@Builder
public class SpaSalonRequest {
        @NotBlank(message = "Name can't be blank")
        @Size(max = 50, message = "Name must be shorter than 50 characters")
        private final String nameSalon;

        @Positive(message = "Cost must be positive number")
        private final int cost;

        @NotNull(message = "idProfile can't be null")
        @Positive(message = "idProfile must be positive number")
        @ProfileExists(message = "Profile with idProfile not exists")
        private final Long idProfile;
}

