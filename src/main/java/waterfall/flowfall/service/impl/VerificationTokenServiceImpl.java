package waterfall.flowfall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import waterfall.flowfall.model.VerificationToken;
import waterfall.flowfall.repository.VerificationTokenRepository;
import waterfall.flowfall.service.VerificationTokenService;

import java.util.Date;
import java.util.Optional;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public VerificationTokenServiceImpl(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public Optional<VerificationToken> findById(Long id) {
        return verificationTokenRepository.findById(id);
    }

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Override
    public VerificationToken save(VerificationToken token) {
        return verificationTokenRepository.save(token);
    }

    @Override
    public VerificationToken update(VerificationToken token) {
        return verificationTokenRepository.save(token);
    }

    @Override
    public void delete(VerificationToken token) {
        verificationTokenRepository.delete(token);
    }

    @Override
    public void deleteAllByExpirationDateBefore(Date date) {
        verificationTokenRepository.deleteAllByExpirationDateBefore(date);
    }
}
