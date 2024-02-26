package com.example.immediatemeetupbe.domain.map.service;

import com.example.immediatemeetupbe.domain.map.vo.Point;
import com.example.immediatemeetupbe.domain.participant.entity.Participant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

@Service
public class Graham {

    // first = y좌표가 가장 작은 점이나 x좌표값이 가장 작은 점을 기준점으로 잡음
    Point first = new Point(Long.MAX_VALUE, Long.MAX_VALUE);

    public List<Point> calculate(List<Participant> participantList) {

        ArrayList<Point> participantPointList = new ArrayList<>();
        // 1. 점들을 입력받는다.

        for (Participant participant : participantList) {
            participantPointList.add(
                new Point(participant.getLongitude(), participant.getLatitude()));
        }

        // w. 기준점을 구한다. 기준점은 y좌표가 작은순 -> x좌표가 작은 순
        for (Point point : participantPointList) {
            if (point.getY() < first.getY()
                || (point.getY() == first.getY() && point.getX() < first.getX())) {
                first = point;
            }
        }

        // 3. 기준점과 나머지점들이 ccw로 반시계방향(좌회전)이 되도록 정렬을 시키고, 만약 일직선상에 있으면 거리가 증가하게끔 정렬을 시킴
        participantPointList.sort(new Comparator<Point>() {

            @Override
            public int compare(Point second, Point third) {
                long ccw_Result = ccw(first, second, third);

                if (ccw_Result > 0) {
                    return -1; // second -> third
                } else if (ccw_Result < 0) {
                    return 1; // third -> second
                } else {
                    double dist1 = dist(first, second);
                    double dist2 = dist(first, third);
                    return Double.compare(dist1, dist2);
                }
            }

        });

        // 4. 그라함 스캔 Start
        Stack<Point> s = new Stack<>();
        s.add(first);

        for (int i = 1; i < participantPointList.size(); i++) {
            while (s.size() > 1
                && ccw(s.get(s.size() - 2), s.get(s.size() - 1), participantPointList.get(i))
                <= 0) {
                s.pop();
            }
            s.add(participantPointList.get(i));
        }

        return new ArrayList<>(s);

    }

    static long ccw(Point a, Point b, Point c) {
        return (a.getX() * b.getY() + b.getX() * c.getY() + c.getX() * a.getY()) - (
            b.getX() * a.getY()
                + c.getX() * b.getY() + a.getX() * c.getY());
    }

    static double dist(Point a, Point b) {
        return Math.sqrt(
            (b.getX() - a.getX()) * (b.getX() - a.getX()) + (b.getY() - a.getY()) * (b.getY()
                - a.getY()));
    }

}
