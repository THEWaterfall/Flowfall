package waterfall.flowfall.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "verification_token")
public class VerificationToken {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="token")
    private String token;

    @Column(name="expiration_date")
    private Date expirationDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    public VerificationToken() {

    }

    public VerificationToken(int expirationTimeInMinutes) {
        this.expirationDate = calculateExpirationDate(expirationTimeInMinutes);
    }

    public VerificationToken(User user, String token, int expirationTimeInMinutes) {
        this.token = token;
        this.user = user;
        this.expirationDate = calculateExpirationDate(expirationTimeInMinutes);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private Date calculateExpirationDate(final int expiryTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);

        return calendar.getTime();
    }

    public void refresh(String token, int expirationTimeInMinutes) {
        this.token = token;
        calculateExpirationDate(expirationTimeInMinutes);
    }

    @Override
    public int hashCode() {
        final int constant = 11;
        int result = 1;
        result = result * constant + expirationDate.hashCode();
        result = result * constant + token.hashCode();
        result = result * constant + user.hashCode();

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if (o == null)
            return false;
        if(!(o instanceof VerificationToken))
            return false;

        VerificationToken verificationToken = (VerificationToken) o;

        return verificationToken.expirationDate.equals(this.expirationDate) &&
                verificationToken.token.equals(this.token) &&
                verificationToken.user.equals(this.user) &&
                verificationToken.id.equals(this.id);
    }
}
