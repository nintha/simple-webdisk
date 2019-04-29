package top.nintha.simplewebdisk.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {
    /**
     * 处理业务异常
     */
    @ExceptionHandler({BusinessException.class})
    public Results BusinessExceptionHandler(BusinessException ex, HttpServletRequest req, HttpServletResponse res) {
        List<StackTraceElement> stackTraceElements = Arrays.asList(ex.getStackTrace());
        String stack = stackTraceElements
                .subList(0, Math.min(stackTraceElements.size(), 10))
                .stream()
                .map(StackTraceElement::toString)
                .collect(Collectors.joining("\n\t", "\n\t", "\n\t...omitted"));

        log.error("business exception, code={}, message={}.{}", ex.getCode(), ex.getMessage(), stack);

        if (ex.getCode() == ExceptionType.ITEM_NOT_FOUND.getCode()) {
            res.setStatus(HttpStatus.NOT_FOUND.value());
        } else {
            res.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        return Results.failed(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus
    public Results UpperExceptionHandler(Exception ex, HttpServletRequest req) {
        log.error("system exception", ex);
        BusinessException exception = BusinessException.of(ExceptionType.SYSTEM_EXCEPTION);
        return Results.failed(exception.getCode(), exception.getMessage());
    }
}