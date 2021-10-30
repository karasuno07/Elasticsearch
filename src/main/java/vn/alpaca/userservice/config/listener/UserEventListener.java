package vn.alpaca.userservice.config.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.event.spi.*;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.alpaca.userservice.entity.jpa.User;
import vn.alpaca.userservice.mapper.UserMapper;
import vn.alpaca.userservice.repository.es.UserESRepository;
import vn.alpaca.userservice.service.TokenService;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserEventListener implements
        PostInsertEventListener,
        PostUpdateEventListener,
        PostDeleteEventListener {

    private final UserESRepository repository;
    private final UserMapper mapper;

    private final TokenService tokenService;

    @Override
    @Transactional
    public void onPostInsert(PostInsertEvent event) {
        if (event.getEntity() instanceof User) {
            log.info("ON CREATED: " + event.getEntity());
            User user = (User) event.getEntity();
            repository.save(mapper.userToUserES(user));
        }
    }

    @Override
    @Transactional
    public void onPostUpdate(PostUpdateEvent event) {
        if (event.getEntity() instanceof User) {
            log.info("ON UPDATED: " + event.getEntity());
            User user = (User) event.getEntity();
            repository.save(mapper.userToUserES(user));

            tokenService.removeToken(user);
        }
    }

    @Override
    @Transactional
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
