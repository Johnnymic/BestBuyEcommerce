package com.bestbuy.ecommerce.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.type.descriptor.DateTimeUtils;

import java.text.DateFormat;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private String message ;

    private String time ;

    private T data;

    public ApiResponse(T data) {
        this.message= "Processed successful";
        this.time= DateFormat.getDateTimeInstance().format(DateFormat.LONG);
        this.data = data;
    }
}
