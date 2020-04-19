package waterfall.flowfall.service;

import waterfall.flowfall.model.VerificationToken;

import java.util.Date;
import java.util.Optional;

public interface VerificationTokenService {
    Optional<VerificationToken> findById(Long id);
    Optional<VerificationToken> findByToken(String token);

    VerificationToken save(VerificationToken token);
    VerificationToken update(VerificationToken token);
    void delete(VerificationToken token);
    void deleteAllByExpirationDateBefore(Date date);
}
