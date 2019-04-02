package dynamicprogramming.fastestway;

/**
 * @author bijiayang
 * @since 2019/02/26
 */

public class LineAndTime {
    private Integer line;
    private Integer time;

    public LineAndTime(Integer line, Integer time) {
        this.line = line;
        this.time = time;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "LineAndTime{" +
                "line=" + line +
                ", time=" + time +
                '}';
    }
}
