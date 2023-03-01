package com.example.obscure.truth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value= {NotFoundException.class})
	protected ResponseEntity<?> handleNotFoundError(RuntimeException ex, WebRequest request) {
		String stack = getStack(ex);
		log.error("Not found Exception: {}", stack);
		ErrorResponse response = new ErrorResponse(ex, HttpStatus.NOT_FOUND, "Not Found", request);
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ExceptionHandler(value= {BadStateException.class})
	protected ResponseEntity<?> handleBadStateExceptionError(RuntimeException ex, WebRequest request) {
		String stack = getStack(ex);
		log.error("Bad State Exception : {}", stack);
		ErrorResponse response = new ErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Error occured whilst handling request", request);
		return new ResponseEntity<>(response, response.getStatus());
	}
	
	@ExceptionHandler(value= {Exception.class})
	protected ResponseEntity<?> handleGeneralExceptionError(RuntimeException ex, WebRequest request) {
		String stack = getStack(ex);
		log.error("General Exception : {}", stack);
		ErrorResponse response = new ErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Error occured whilst handling request", request);
		return new ResponseEntity<>(response, response.getStatus());
	}
	

	private String getStack(Exception ex) {
		if (ex == null) {
			return "[No stack]";
		}
		
		String stack = ex.getMessage() + "\n";
		StackTraceElement[] frames =  ex.getStackTrace();
		if (frames.length == 0) {
			return "[No stack]";
		}
		
		int f = 0;
		boolean coreFrameFound = false;
		boolean prevFrameAdded = false;
		
		while (f < frames.length) {
			
			String frame = frames[f].toString();
			boolean isCoreFrame = frame.contains("com.fivephasestra.core");

			if (f < 1) {
				stack = stack + "[Frame]:" + frame;
				prevFrameAdded = true;
			} else {
				if (isCoreFrame) {
					stack = stack + "\n" + "[Frame]:" + frame;
					prevFrameAdded = true;
				} else if (!coreFrameFound) {
					if (prevFrameAdded) {
						stack = stack + "\n[Skipped Frames...]";
					}
					prevFrameAdded = false;
				} else {
					return stack;
				}
			}
			
			f++;
			coreFrameFound = coreFrameFound || isCoreFrame; 
		}
		
		if (coreFrameFound) {
			return stack;
		} else if (frames.length > 0) {
			return frames[0].toString();
		}
		return "[No stack trace]";
	}
}
