package com.excilys.cdb.springController;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {
 
    @GetMapping(value = "/errors")
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {
        ModelAndView errorPage = new ModelAndView("404");
        int httpErrorCode = getErrorCode(httpRequest);
        
        switch (httpErrorCode) {
        case 403: {
        	errorPage = new ModelAndView("403");
            break;
        }
        case 404: {
        	errorPage = new ModelAndView("404");
            break;
        }
        case 500: {
        	errorPage = new ModelAndView("500");
            break;
        }
    }
        return errorPage;
    }
    
   private int getErrorCode(HttpServletRequest httpRequest) {
       return (Integer) httpRequest
         .getAttribute("javax.servlet.error.status_code");
   }
}