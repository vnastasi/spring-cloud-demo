package md.vnastasi.cloud.service;

import md.vnastasi.cloud.endpoint.model.Notification;
import reactor.core.publisher.Flux;

public interface NotificationService {

    Flux<Notification> getNotifications();
}
