package com.it.doubledi.cinemamanager.application.dto.request;

import com.it.doubledi.cinemamanager._common.model.dto.request.Request;
import com.it.doubledi.cinemamanager._common.model.enums.Gender;
import com.it.doubledi.cinemamanager.infrastructure.support.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserCreateRequest extends Request {
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
    private String fileId;

    private List<String> locationIds;
    private List<String> roleIds;
}
