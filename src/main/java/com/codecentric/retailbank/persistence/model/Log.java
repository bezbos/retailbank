package com.codecentric.retailbank.persistence.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "application_logs")
public class Log {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    @Column(name = "log_message")
    private String message;

    @Transient
    private Exception exception;

    @Column(name = "log_date")
    private Date date;


    public Log() {
        super();
        this.date = Calendar.getInstance().getTime();
        this.uuid = UUID.randomUUID();
    }

    public Log(String message) {
        super();
        this.message = message;
        this.date = Calendar.getInstance().getTime();
        this.uuid = UUID.randomUUID();
    }

    public Log(Exception exception) {
        super();
        this.message = exception.getMessage();
        this.date = Calendar.getInstance().getTime();
        this.uuid = UUID.randomUUID();
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public static Log generateLog(String message){
        return new Log(message);
    }

    public static Log generateLog(Exception exception){
        return new Log(exception);
    }
}
