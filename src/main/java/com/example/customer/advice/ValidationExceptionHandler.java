package com.example.customer.advice;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.customer.exception.CustomerExist;
import com.example.customer.exception.CustomerNameOrEmail;
import com.example.customer.exception.CustomerNotFound;
import com.example.customer.exception.DeletedCustomer;
import com.example.customer.exception.InvalidInput;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleValidationErrors(MethodArgumentNotValidException ex ){
         Map<String, String>  errorMap = new HashMap<>();
         ex.getBindingResult().getFieldErrors().forEach(error ->{
            errorMap.put(error.getField(),error.getDefaultMessage());
         });
         return errorMap;  
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomerNotFound.class )
    public Map<String ,String > handleCustomerNotFound( CustomerNotFound  ex){
        Map<String, String>  errorMap = new HashMap<>();
        errorMap.put("Error message ",ex.getMessage());
        return errorMap;
    }
    
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CustomerNameOrEmail.class)
    public Map<String, String> handleCustomerNameOrEmail( CustomerNameOrEmail ex){
    	Map<String,String> errorMap = new HashMap<>();
    	errorMap.put("Found Error ", ex.getMessage());
    	return errorMap;
    }

    @ResponseStatus( code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidInput.class)
    public Map<String,String> handleInvalidInput(InvalidInput ex){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("Error is",ex.getMessage());
        return  errorMap;

    }
  
    @ResponseStatus(code = HttpStatus.OK)
    @ExceptionHandler(DeletedCustomer.class)
    public Map<String,String> handleDeletedCustomer( DeletedCustomer ex){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put(" Message for deletion ", ex.getMessage());
        return errorMap;
    }
	@ExceptionHandler(StringIndexOutOfBoundsException.class)
    public Map<String,String> handleStringIndexOutOfBoundsException(StringIndexOutOfBoundsException ex ){
    	Map<String,String> errorMap = new HashMap<>();
    	errorMap.put("Error for outof index", ex.getMessage());
    	return errorMap;
    	
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Map<String,String> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex){
        Map<String, String>  errorMap = new HashMap<>();
        errorMap.put("Error of Missing Data ", ex.getMessage());
         return errorMap;
    }

    @ExceptionHandler(CustomerExist.class)
    public Map<String,String> handleCustomerExist(CustomerExist ex){
        Map<String, String>  errorMap = new HashMap<>();
        errorMap.put("Error message ", ex.getMessage());
         return errorMap;
    }

   
	

}
