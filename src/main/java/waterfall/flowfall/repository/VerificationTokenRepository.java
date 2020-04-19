package waterfall.flowfall.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import waterfall.flowfall.model.VerificationToken;

import java.util.Date;
import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
    void deleteAllByExpirationDateBefore(Date date);
}
