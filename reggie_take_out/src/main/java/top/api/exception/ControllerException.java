package top.api.exception;

/**
 * 自定义业务异常类
 */
public class ControllerException extends RuntimeException{

    public ControllerException(String message){
        super(message);
    }
}
