package top.api.exception;


import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.SystemException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.api.common.R;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * springMVC异常处理器
 */
@RestControllerAdvice
@Slf4j
public class ProjectExceptionAdvice {


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> doSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e){
        log.error("--->"+e.getMessage());

        if (e.getMessage().contains("Duplicate entry")){
            String[] split = e.getMessage().split(" ");
            String msg = split[2] + "已存在";
            return R.error(msg);
        }
        return R.error("未知错误");
    }

    /**
     * 系统异常
     * @param ex
     * @return
     */
    @ExceptionHandler(SystemException.class)
    public R<String> doSystemException(SystemException ex){
        log.error(ex.getMessage());
        return R.error(ex.getMessage());
    }

    /**
     * 业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler(ControllerException.class)
    public R<String> doControllerException(ControllerException ex){
        log.error(ex.getMessage());
        
        return R.error(ex.getMessage());
    }

}
