package br.project.com.microserviceemail.models;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class EmailModel {
    private String emailFrom;
    private String emailTo;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String text;
}
