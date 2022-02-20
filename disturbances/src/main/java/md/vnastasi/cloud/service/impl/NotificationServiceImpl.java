package md.vnastasi.cloud.service.impl;

import md.vnastasi.cloud.client.PublicTravelInfoClient;
import md.vnastasi.cloud.client.model.CalamityWrapper;
import md.vnastasi.cloud.client.model.DisturbanceTypeWrapper;
import md.vnastasi.cloud.endpoint.model.Notification;
import md.vnastasi.cloud.service.NotificationService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final PublicTravelInfoClient client;

    public NotificationServiceImpl(PublicTravelInfoClient client) {
        this.client = client;
    }

    @Override
    public Flux<Notification> getNotifications() {
        return client.getDisturbances(List.of(DisturbanceTypeWrapper.CALAMITY))
                .filter(it -> it instanceof CalamityWrapper)
                .map(CalamityWrapper.class::cast)
                .map(Mappings::map);
    }
}
