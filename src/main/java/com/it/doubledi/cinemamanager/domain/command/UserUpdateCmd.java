package com.it.doubledi.cinemamanager.domain.command;

import com.it.doubledi.cinemamanager._common.model.enums.Gender;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class UserUpdateCmd {
    private String fullName;
    private String email;
    private String phoneNumber;
    private LocalDate dayOfBirth;
    private Gender gender;
    private String title;
    private String departmentName;
    private String description;
    private String fileId;

    private List<String> locationIds;
    private List<String> roleIds;
}
