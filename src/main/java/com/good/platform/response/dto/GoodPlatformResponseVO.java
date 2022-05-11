package com.good.platform.response.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodPlatformResponseVO<T> {

    /**
     * Generic data which will adopt to the different resources
     */
    private T data;

    /**
     * @param String message
     * The success/error message of the API requested.
     */
    private String message;

    /**
     * @param boolean success
     * The parameter which indicates the status of API response.
     */
    private boolean success;

    /**
     * @param String errorCode
     * The application specific error codes.
     */
    private String errorCode;

    /**
     * @param String path
     * The hateoas resource link
     */
    private String path;

}
