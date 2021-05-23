package cz.idomatojde.dto.user;

/**
 * DTO holding the amount of credits user has available
 *
 * @author Michal Hazdra
 */
public class UserCreditsDTO {
    private int credits;

    private int bonusCredits;

    public UserCreditsDTO(int credits, int bonusCredits) {
        this.credits = credits;
        this.bonusCredits = bonusCredits;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getBonusCredits() {
        return bonusCredits;
    }

    public void setBonusCredits(int bonusCredits) {
        this.bonusCredits = bonusCredits;
    }
}
