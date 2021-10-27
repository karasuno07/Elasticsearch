package vn.alpaca.elastic.listener;

import lombok.RequiredArgsConstructor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

@Service
@RequiredArgsConstructor
public class HibernateListener {

    private final EntityManagerFactory factory;

    private final UserEventListener listener;

    @PostConstruct
    public void init() {
        SessionFactoryImpl sessionFactory
                = factory.unwrap(SessionFactoryImpl.class);

        EventListenerRegistry registry
                = sessionFactory.getServiceRegistry()
                .getService(EventListenerRegistry.class);

//        registry.getEventListenerGroup(EventType.POST_INSERT)
//                .appendListener(listener);
        registry.getEventListenerGroup(EventType.POST_UPDATE)
                .appendListener(listener);
//        registry.getEventListenerGroup(EventType.POST_DELETE)
//                .appendListener(listener);
    }


}
