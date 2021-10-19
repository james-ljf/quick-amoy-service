package com.game.common.entity;

import com.game.common.annotation.Description;
import com.game.common.entity.dto.Code;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lijf
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Description(value = "通用结果定义")
public class Response<T> implements Serializable {

    @Description(value = "自定义业务状态码，8888表示请求成功，非8888则表示请求异常")
    private String code;

    @Description(value = "错误信息，调用成功则为空")
    private String msg;

    @Description(value = "调用是否成功，true成功，false异常")
    private boolean isSuccess;

    @Description(value = "返回的结果")
    private T result;

    public static <T> Response<T> success(T result){
        return generateResponse(true, Code.SUCCESS, result);
    }

    public static <T> Response<T> fail(String code, String msg) {
        return fail(Code.business(code, msg));
    }

    public static <T> Response<T> fail(Code code) {
        return generateResponse(false, code, null);
    }

    private static <T> Response<T> generateResponse(boolean isSuccess, Code code, T result){
        Response<T> response = new Response<>();
        response.setCode(code.getCode())
                .setMsg(code.getMsg())
                .setResult(result)
                .setSuccess(isSuccess);
        return response;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", isSuccess=" + isSuccess +
                ", result=" + result +
                '}';
    }
}
