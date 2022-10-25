package guru.springframework.controllers;

import guru.springframework.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class ExceptionHandlingController {


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {

        String NOT_FOUND = "404 NOT FOUND";
        return genericExceptionMethod(exception, NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleBadRequest(Exception exception) {

        String BAD_REQUEST = "400 BAD REQUEST";
        return genericExceptionMethod(exception, BAD_REQUEST);
    }

    private ModelAndView genericExceptionMethod(Exception exception, String response) {

        ModelAndView mav = new ModelAndView();
        {
            mav.addObject("response", response);
            mav.addObject("message", exception.getMessage());
            mav.setViewName("error");
        }
        return mav;
    }
}
