package com.hms.atbotizmozel.data.model.response;

import java.io.Serializable;

/**
 * Created by Batuhan on 26.02.2018.
 */

public class BaseResponse<T> implements Serializable {

    public boolean success;
    public T data;
    public ErrorModel error;

}
