package dynamicprogramming.fastestway;

/**
 * @author bijiayang
 * @since 2019/02/26
 */

public class Station {
    private Integer linePos;
    private Integer stationPos;

    public Station(Integer linePos, Integer stationPos) {
        this.linePos = linePos;
        this.stationPos = stationPos;
    }

    public Integer getLinePos() {
        return linePos;
    }

    public void setLinePos(Integer linePos) {
        this.linePos = linePos;
    }

    public Integer getStationPos() {
        return stationPos;
    }

    public void setStationPos(Integer stationPos) {
        this.stationPos = stationPos;
    }

    @Override
    public String toString() {
        return "Station{" +
                "linePos=" + linePos +
                ", stationPos=" + stationPos +
                '}';
    }
}
