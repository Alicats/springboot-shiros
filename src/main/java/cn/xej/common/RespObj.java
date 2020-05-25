package cn.xej.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespObj {

    private Integer code;
    private String message;
    private Object data;

    public static RespObj build(Integer code,String message,Object data){
        return new RespObj(code,message,data);
    }

}
