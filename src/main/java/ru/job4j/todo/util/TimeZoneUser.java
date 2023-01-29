package ru.job4j.todo.util;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class TimeZoneUser {

    public static void setTimeZone(User user, Task task) {
        ZonedDateTime time;
        if (user.getUserZone() == null) {
            time = task.getCreated().withZoneSameInstant(TimeZone.getDefault().toZoneId());
        } else {
            time = task.getCreated().withZoneSameInstant(ZoneId.of(user.getUserZone()));
        }
        task.setCreated(time);
    }

    public static List<TimeZone> getZone() {
        List<TimeZone> zones = new ArrayList<>();
        for (String timeId : TimeZone.getAvailableIDs()) {
            zones.add(TimeZone.getTimeZone(timeId));
        }
        return zones;
    }
}