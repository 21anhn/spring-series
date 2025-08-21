package com.anhltn.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private int statusCode = 200;
    private boolean success = false;
    private String message = "The operation has been successful.";
    private T data;
}
