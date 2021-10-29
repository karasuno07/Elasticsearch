package vn.alpaca.userservice.config;

import lombok.RequiredArgsConstructor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.context.annotation.Configuration;
import vn.alpaca.userservice.config.listener.RoleEventListener;
import vn.alpaca.userservice.config.listener.UserEventListener;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Configuration
@RequiredArgsConstructor
public class HibernateListenerConfig {

    @PersistenceUnit
    private final EntityManagerFactory factory;

    private final UserEventListener userListener;
    private final RoleEventListener roleListener;

    @PostConstruct
    protected void listenerRegistry() {
        SessionFactoryImpl sessionFactory
                = factory.unwrap(SessionFactoryImpl.class);

        EventListenerRegistry registry = sessionFactory.getServiceRegistry()
                .getService(EventListenerRegistry.class);

        registry.getEventListenerGroup(EventType.POST_INSERT)
                .appendListeners(userListener, roleListener);
        registry.getEventListenerGroup(EventType.POST_UPDATE)
                .appendListeners(userListener, roleListener);
        registry.getEventListenerGroup(EventType.POST_DELETE)
                .appendListeners(userListener, roleListener);
    }


}
