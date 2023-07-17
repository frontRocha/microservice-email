package br.project.com.microserviceemail.services;

import br.project.com.microserviceemail.enums.StatusEmail;
import br.project.com.microserviceemail.models.EmailModel;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    private RabbitTemplate rabbitTemplate;
    private JavaMailSender javaMailSender;

    @Transactional
    public void sendEmailToEmail(EmailModel emailModel) throws Exception {
        MimeMessage message = createMimeMessage(emailModel);
        sendMessage(message);
    }

    public void sendMessageToMessenger(EmailModel emailModel) {
        rabbitTemplate.convertAndSend("ms.email", emailModel);
    }

    private MimeMessage createMimeMessage(EmailModel emailModel) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(emailModel.getEmailTo());
        helper.setFrom(emailModel.getEmailFrom());
        helper.setSubject(emailModel.getSubject());
        helper.setText(emailModel.getText(), true);

        return message;
    }

    private void sendMessage(MimeMessage message) {
        javaMailSender.send(message);
    }
}


