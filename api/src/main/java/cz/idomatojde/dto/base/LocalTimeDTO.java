package cz.idomatojde.dto.base;

import java.util.Objects;

/**
 * @author Michal Hazdra
 */
public class LocalTimeDTO {

    int hour;
    int minute;
    int second;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocalTimeDTO)) return false;
        LocalTimeDTO that = (LocalTimeDTO) o;
        return getHour() == that.getHour() && getMinute() == that.getMinute() && getSecond() == that.getSecond();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHour(), getMinute(), getSecond());
    }
}
