package cz.idomatojde.dto.base;

import java.util.Objects;

/**
 * @author Michal Hazdra
 */
public class DurationDTO {
    private long minutes;

    public long getMinutes() {
        return minutes;
    }

    public void setMinutes(long minutes) {
        this.minutes = minutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DurationDTO)) return false;
        DurationDTO that = (DurationDTO) o;
        return getMinutes() == that.getMinutes();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMinutes());
    }
}
