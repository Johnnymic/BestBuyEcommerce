package com.bestbuy.ecommerce.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.springframework.http.HttpStatus;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private String message ;

    @TimeZoneStorage
    private LocalDateTime time ;

    private HttpStatus statusCode;

    private T data;

    public ApiResponse(T data) {
        this.message= "Processed successful";
        this.time= LocalDateTime.now();
        this.data = data;
    }
}
