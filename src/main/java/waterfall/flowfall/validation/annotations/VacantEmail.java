package waterfall.flowfall.validation.annotations;

import org.springframework.messaging.handler.annotation.Payload;
import waterfall.flowfall.validation.VacantEmailValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Constraint(validatedBy = VacantEmailValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VacantEmail {
    String message() default "Email is already taken";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
