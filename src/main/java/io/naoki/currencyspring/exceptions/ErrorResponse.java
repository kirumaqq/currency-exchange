package io.naoki.currencyspring.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse extends ProblemDetail {

    private final LocalDateTime timestamp;

    public ErrorResponse(HttpStatusCode code, String detail) {
        super(code.value());
        this.timestamp = LocalDateTime.now();
        setDetail(detail);
    }
}
