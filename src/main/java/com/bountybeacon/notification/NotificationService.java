package com.bountybeacon.notification;

import com.bountybeacon.program.entity.Program;

public interface NotificationService {
    void sendNotification(Program program, NotificationType type);
}
