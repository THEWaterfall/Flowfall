package waterfall.flowfall.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import waterfall.flowfall.service.UserService;
import waterfall.flowfall.validation.annotations.VacantEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class VacantEmailValidator implements ConstraintValidator<VacantEmail, String> {

    private UserService userService;

    @Autowired
    public VacantEmailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !userService.existsByEmail(email);
    }
}
