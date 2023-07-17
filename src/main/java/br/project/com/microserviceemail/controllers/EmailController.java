package br.project.com.microserviceemail.controllers;

import br.project.com.microserviceemail.dtos.EmailDto;
import br.project.com.microserviceemail.models.EmailModel;
import br.project.com.microserviceemail.services.EmailService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class EmailController {

    private EmailService emailService;

    @PostMapping("/sending-email")
    public ResponseEntity<Object> sendingEmail(@RequestBody @Valid EmailDto emailDto) {
        try {
            EmailModel emailModel = convertDtoToModel(emailDto);
            sendMessageToMenssenger(emailModel);
            return new ResponseEntity<>(emailModel, HttpStatus.CREATED);
        } catch (Exception err) {
            return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void sendMessageToMenssenger(EmailModel emailModel) {
        emailService.sendMessageToMessenger(emailModel);
    }

    private EmailModel convertDtoToModel(EmailDto emailDto) {
        EmailModel emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDto, emailModel);
        return emailModel;
    }
}
