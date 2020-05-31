package waterfall.flowfall.util;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;
import waterfall.flowfall.enums.Template;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class EmailUtils {

    private JavaMailSender javaMailSender;

    @Autowired
    public EmailUtils(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void send(String to, String from, String subject, String message) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setFrom(from);
        msg.setSubject(subject);
        msg.setText(message);

        javaMailSender.send(msg);
    }

    public String renderTemplate(Template templateName, Map<String, String> valuesToBind) {
        ST stringTemplate = new ST(getTemplate(templateName.getLiteral()), '$', '$');
        valuesToBind.forEach(stringTemplate::add);

        return stringTemplate.render();
    }

    private static String getTemplate(String name) {
        InputStream template = EmailUtils.class.getClassLoader().getResourceAsStream("email-templates/" + name + ".html");
        try {
            return IOUtils.toString(template, StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            throw new RuntimeException("Cannot find template file for name: " + name, e);
        }
    }

}
