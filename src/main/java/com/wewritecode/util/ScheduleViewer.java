/**
 * @author Grant Clark
 */

package com.wewritecode.util;

import com.wewritecode.pan.schedule.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ScheduleViewer extends JFrame {

    private static final int TIME_INTERVAL_HEIGHT = 15;
    private static final int DAY_OF_WEEK_HEIGHT = 3 * TIME_INTERVAL_HEIGHT;
    private static final int COLUMN_WIDTH = 100;
    private static final int CANVAS_WIDTH = COLUMN_WIDTH * 5;
    private static final int CANVAS_HEIGHT = TIME_INTERVAL_HEIGHT * 100;

    private static final String DAYS = "MTWRF";
    private static final String[] DAYS_ARRAY = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private static final int EARLIEST_TIME = 480;

    private static final Font DEFAULT_FONT = new Font("Monospaced", Font.PLAIN, 12);

    private DrawSchedule canvas;

    public ScheduleViewer(int index, List<Schedule> schedules) {
        canvas = new DrawSchedule(schedules.get(index));
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        getContentPane().add(canvas);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setTitle("Schedule #" + index);
        setVisible(true);
    }

    private class DrawSchedule extends JPanel {
        private Schedule schedule;

        public DrawSchedule(Schedule schedule) { this.schedule = schedule; }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.WHITE);

            drawHeader(g);

            for (Course course : schedule.getCourses()) {
                Lecture lecture = course.getLecture(0);
                Section section = lecture.getSection(0);

                drawSession(g, lecture, course.getCourseID());
                drawSession(g, section, course.getCourseID());
            }
        }

        private void drawSession(Graphics g, Session s, String courseId) {
            TimeObj start = s.getTime().getStart();
            TimeObj end = s.getTime().getEnd();

            int startTimeInMins = (start.getHour() * 60) + start.getMinute();
            int endTimeInMins = (end.getHour() * 60) + start.getMinute();

            int y = TIME_INTERVAL_HEIGHT * (startTimeInMins - EARLIEST_TIME) / 15;
            int height = endTimeInMins - startTimeInMins;

            for (String day : s.getDays()) {
                int x = COLUMN_WIDTH * DAYS.indexOf(day);
                g.setColor(Color.ORANGE);
                g.setFont(DEFAULT_FONT);
                Rectangle session = new Rectangle(x, y, COLUMN_WIDTH, height);
                g.drawRect(x, y, COLUMN_WIDTH, height);
                drawCenteredString(g, courseId, session);
            }
        }

        private void drawCenteredString(Graphics g, String text, Rectangle rect) {
            FontMetrics metrics = g.getFontMetrics(DEFAULT_FONT);
            int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
            int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
            g.setColor(Color.BLACK);
            g.setFont(DEFAULT_FONT);
            g.drawString(text, x, y);
        }

        private void drawHeader(Graphics g) {
            for (int i = 0; i < DAYS_ARRAY.length; i++) {
                int x = i * COLUMN_WIDTH;
                Rectangle day = new Rectangle(x, 0, COLUMN_WIDTH, DAY_OF_WEEK_HEIGHT);
                g.setColor(Color.PINK);
                g.setFont(DEFAULT_FONT);
                g.drawRect(x, 0, COLUMN_WIDTH, DAY_OF_WEEK_HEIGHT);
                drawCenteredString(g, DAYS_ARRAY[i], day);
            }
        }
    }
}
