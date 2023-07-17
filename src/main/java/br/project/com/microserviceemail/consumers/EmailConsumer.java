package br.project.com.microserviceemail.consumers;

import br.project.com.microserviceemail.dtos.EmailDto;
import br.project.com.microserviceemail.models.EmailModel;
import br.project.com.microserviceemail.services.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmailConsumer {
    private EmailService emailService;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listen(@Payload EmailDto emailDto) throws Exception {
        EmailModel emailModel = convertDtoToModel(emailDto);
        sendMessageToEmail(emailModel);
    }

    private EmailModel convertDtoToModel(EmailDto emailDto) {
        EmailModel emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDto, emailModel);
        return emailModel;
    }

    private void sendMessageToEmail(EmailModel emailModel) throws Exception {
        emailService.sendEmailToEmail(emailModel);
    }
}
