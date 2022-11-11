package com.it.doubledi.cinemamanager.domain.command;

import com.it.doubledi.cinemamanager._common.model.enums.Gender;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class UserCreateCmd {
    private String username;
    private String password;
    private String repeatPassword;
    private String fullName;
    private String email;
    private String phoneNumber;
    private LocalDate dayOfBirth;
    private Gender gender;
    private String employeeCode;
    private String title;
    private String departmentName;
    private String description;
    private String avatarFileId;

    private List<String> locationIds;
}
