package br.com.viavarejo.backendchallenge.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ResponseStatus(INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends BaseException {
    private static final long serialVersionUID = 4865615905718225792L;

    public InternalServerErrorException(String message) {
        super(message);
    }
}
