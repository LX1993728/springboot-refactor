package com.refactor.spring.boot.error;

/**
 * @author liuxun
 * @apiNote 定义错误信息类
 *
 */
class ErrorInfo {
    private Integer code ;
    private String message ;
    private String url ;
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
