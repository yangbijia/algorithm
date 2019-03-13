package fastestway;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 动态规划--装配线调度
 * m条装配线，每条装配线下n个装配站，计算从进入装配线到出去的最短时间和路径
 * @author bijiayang
 */
public class Main {

//    enter[i],exit[i],station[i][j],move[i][j]
//
//
//    f(i,1) = min(enter[i] + station[i][1]) (i=1...m,j=1)
//
//    f(i,2) = min(min(f(1,1),...,f(i,1)) + station[i][2])	(i=1...m,j=2)
//
//    f(i,j) = min(min(f(1,j - 1),...,f(i,j - 1)) + station[i][j])	(i=1...m,j=2...n)
//
//    f(i,n) = min(min(f(1,n - 1),...,f(i,n - 1)) + station[i][n] + exit[i]) (i-1...m,j=n)
//
//    l[i][j] = result i of min

    /**
     * 输入
     */
    static List<Integer> enter = Stream.of(new Integer[]{2, 4}).collect(Collectors.toList());
    static List<Integer> exit = Stream.of(new Integer[]{3, 2}).collect(Collectors.toList());
    static List<List<Integer>> station = Arrays.stream(new Integer[][]{{7, 9, 3, 4, 8, 4},{8, 5, 6, 4, 5, 7}})
            .map(integers -> Arrays.asList(integers))
            .collect(Collectors.toList());
    static List<List<Integer>> move = Arrays.stream(new Integer[][]{{2, 3, 1, 3, 4},{2, 1, 2, 2, 1}})
            .map(integers -> Arrays.asList(integers))
            .collect(Collectors.toList());
    static Integer line_size = enter.size(), station_size = station.get(0).size();

    /**
     * 输出
     */
    static List<List<Integer>> f = Arrays.stream(new Integer[][]{{-1, -1, -1, -1, -1, -1},{-1, -1, -1, -1, -1, -1}})
            .map(integers -> Arrays.asList(integers))
            .collect(Collectors.toList());
    static List<List<Integer>> l = Arrays.stream(new Integer[][]{{-1, -1, -1, -1, -1},{-1, -1, -1, -1, -1}})
            .map(integers -> Arrays.asList(integers))
            .collect(Collectors.toList());
    static List<Station> way = new LinkedList<>();


    public static void main(String[] args) {
        // 非递归求最短时间和路径
        fastway();

        // 递归求最短时间和路径
//        List<Integer> lastTimeList  = new ArrayList<>();
//        for (int i = 0; i < line_size; i++) {
//            lastTimeList.add(fastestwayRecursion(station_size - 1, i));
//        }
//        LineAndTime min = min(lastTimeList);
//        System.out.println("fatestTime = " + min.getTime());
//        printWay(min);


        // 时间表打印
        for (int i = 0; i < f.size(); i++) {
            List<Integer> list = f.get(i);
            for (Integer e : list) {
                System.out.print(String.format("%" + 4 + "s", e.toString()));
            }
            System.out.println();
        }

        // 线路表打印
        for (int i = 0; i < l.size(); i++) {
            List<Integer> list = l.get(i);
            for (Integer e : list) {
                System.out.print(String.format("%" + 4 + "s", e.toString()));
            }
            System.out.println();
        }
    }

    /**
     * 递归计算计算到给定节点的最短路径
     * @param stationPos
     * @param linePos
     * @return
     */
    public static Integer fastestwayRecursion(Integer stationPos, Integer linePos) {
        LineAndTime lineAndTime;
        // 中间节点
        if (stationPos > 0 && stationPos < station_size - 1) {
            Map<Integer, Integer> map = new HashMap<Integer, Integer>();
            for (int k = 0; k < line_size; k++) {
                Integer time = k == linePos ? f.get(linePos).get(stationPos) : -1, line = linePos;
                if (time == -1) {
                    Integer currentTime = fastestwayRecursion(stationPos - 1, k) + (k == linePos ?
                            0 : move.get(k).get(stationPos - 1)) + station.get(linePos).get(stationPos);
                    time = currentTime;
                    line = k;
                }
                map.put(line, time);
            }
            lineAndTime = min(map);
        // 开始节点
        } else if (stationPos == 0){
            Integer time = f.get(linePos).get(stationPos);
            if (time == -1) {
                Integer currentTime = enter.get(linePos) + station.get(linePos).get(stationPos);
                time = currentTime;
            }
            lineAndTime = new LineAndTime(linePos, time);
        // 结束节点
        } else {
            Map<Integer, Integer> map = new HashMap<Integer, Integer>();
            for (int k = 0; k < line_size; k++) {
                Integer time = k == linePos ? f.get(linePos).get(stationPos) : -1, line = linePos;
                if (time == -1) {
                    time = fastestwayRecursion(stationPos - 1, k) + (k == linePos ?
                            0 : move.get(k).get(stationPos - 1)) + station.get(linePos).get(stationPos) + exit.get(linePos);
                    line = k;
                }
                map.put(line, time);
            }
            lineAndTime = min(map);
        }

        Integer line = lineAndTime.getLine();
        Integer time = lineAndTime.getTime();

        if (f.get(linePos).get(stationPos) == -1) {
            f.get(linePos).set(stationPos, time);
        }

        if (stationPos > 0 && l.get(linePos).get(stationPos - 1) == -1) {
            l.get(linePos).set(stationPos - 1, line + 1);
        }

        return time;
    }

    /**
     * 非递归最短时间和路径
     */
    public static void fastway() {
        int i,j;
        for (i = 0; i < line_size; i++) {
            Integer fi1 = enter.get(i) + station.get(i).get(0);
            f.get(i).set(0, fi1);
        }
        LineAndTime currentLineAndTime;
        for (j = 1; j < station_size; j++) {
            for (i = 0; i < line_size; i++) {
                List<Integer> times = new ArrayList<Integer>();
                for (int k = 0; k < line_size; k++) {
                    times.add(f.get(k).get(j - 1) + (k == i ? 0 : move.get(k).get(j - 1)) + station.get(i).get(j));
                }
                currentLineAndTime = min(times);
                f.get(i).set(j, currentLineAndTime.getTime());
                l.get(i).set(j - 1, currentLineAndTime.getLine() + 1);
            }
        }
        List<Integer> times = new ArrayList<Integer>();
        for (i = 0; i < line_size; i++) {
            Integer exitTime = exit.get(i) + f.get(i).get(station_size - 1);
            times.add(exitTime);
        }
        currentLineAndTime = min(times);
        System.out.println("fasttime = " + currentLineAndTime.getTime().toString());

        printWay(currentLineAndTime);
    }

    public static void printWay(LineAndTime lastStation) {
        way.add(new Station(lastStation.getLine() + 1, station_size));
        for (int i = station_size - 2; i >= 0; i--) {
            way.add(new Station(l.get(way.get(station_size - 2 - i).getLinePos() - 1).get(i), i + 1));
        }
        System.out.println("fastway = " + way.toString());
    }

    public static LineAndTime min(List<Integer> times) {
        if (times.size() == 0) {
            return null;
        }
        Integer min = times.get(0),line = 0;
        for (int i = 0; i < line_size; i++) {
            Integer time = times.get(i);
            if (time < min) {
                min = time;
                line = i;
            }
        }
        return new LineAndTime(line, min);
    }

    public static LineAndTime min(Map<Integer, Integer> times) {
        if (times.size() == 0) {
            return null;
        }
        Integer min = times.get(0), line = 0;
        if (times.size() == 1) {
            line = times.keySet().stream().findFirst().get();
            return new LineAndTime(line, times.get(line));
        }
        for (int i = 0; i < line_size; i++) {
            Integer time = times.get(i);
            if (time < min) {
                min = time;
                line = i;
            }
        }
        return new LineAndTime(line, min);
    }

}
