package waterfall.flowfall.model.dto;

import java.util.Date;
import java.util.List;

public class ErrorResponseDto {
    private String message;
    private String type;
    private int status;
    private List<ErrorDto> fieldErrors;
    private Date timestamp;

    public ErrorResponseDto() {
        this.timestamp = new Date();
    }

    public ErrorResponseDto(String message, String type, int status) {
        this.message = message;
        this.type = type;
        this.status = status;
        this.timestamp = new Date();
    }

    public ErrorResponseDto(String message, String type, int status, List<ErrorDto> fieldErrors) {
        this.message = message;
        this.type = type;
        this.status = status;
        this.fieldErrors = fieldErrors;
        this.timestamp = new Date();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ErrorDto> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<ErrorDto> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
