package waterfall.flowfall.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;
import waterfall.flowfall.enums.Template;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        URL templateUrl = EmailUtils.class.getClassLoader().getResource("email-templates/" + name + ".html");

        try {
            return new String(
                    Files.readAllBytes(Paths.get(templateUrl.getPath()))
            );
        } catch (IOException e) {
            throw new RuntimeException("Cannot find template file for name: " + name, e);
        }
    }

}
