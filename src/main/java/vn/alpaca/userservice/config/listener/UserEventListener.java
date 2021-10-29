package vn.alpaca.userservice.config.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.event.spi.*;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;
import vn.alpaca.userservice.entity.jpa.User;
import vn.alpaca.userservice.mapper.UserMapper;
import vn.alpaca.userservice.repository.es.UserESRepository;

@Component
@Log4j2
@RequiredArgsConstructor
public class UserEventListener implements
        PostInsertEventListener,
        PostUpdateEventListener,
        PostDeleteEventListener {

    private final UserESRepository repository;

    private final UserMapper mapper;

    @Override
    public void onPostInsert(PostInsertEvent event) {
        if (event.getEntity() instanceof User) {
            log.info("ON CREATED: " + event.getEntity());
            User user = (User) event.getEntity();
            repository.save(mapper.userToUserES(user));
        }
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        if (event.getEntity() instanceof User) {
            log.info("ON UPDATED: " + event.getEntity());
            User user = (User) event.getEntity();
            repository.save(mapper.userToUserES(user));
        }
    }

    @Override
    public void onPostDelete(PostDeleteEvent event) {
        if (event.getEntity() instanceof User) {
            log.info("ON DELETED: " + event.getEntity());
            User user = (User) event.getEntity();
            repository.delete(mapper.userToUserES(user));
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister persister) {
        log.info(persister.getEntityName());
        return true;
    }


}
