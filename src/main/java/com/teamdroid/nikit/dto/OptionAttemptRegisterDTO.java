package com.teamdroid.nikit.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OptionAttemptRegisterDTO {

    private String id;
    private Boolean isSelected;

}
