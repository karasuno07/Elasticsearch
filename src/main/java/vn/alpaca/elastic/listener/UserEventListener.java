package vn.alpaca.elastic.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.event.spi.*;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;
import vn.alpaca.elastic.entity.es.UserES;
import vn.alpaca.elastic.entity.jpa.User;
import vn.alpaca.elastic.mapper.UserMapper;
import vn.alpaca.elastic.repository.es.UserESRepository;

@Component
@Log4j2
@RequiredArgsConstructor
public class UserEventListener implements
        PostInsertEventListener, PostUpdateEventListener, PostDeleteEventListener {

    private final UserESRepository userESRepo;
    private final UserMapper userMapper;

    @Override
    public void onPostInsert(PostInsertEvent event) {
        log.debug(event.getEntity() instanceof User);
        log.debug(event.getState());
        if (event.getEntity() instanceof User) {
            User user = (User) event.getEntity();
            log.info(user);
            UserES userES = userMapper.userToUserES(user);
            userESRepo.save(userES);
        }
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        log.debug(event.getEntity() instanceof User);
        log.debug(event.getOldState());
        log.debug(event.getState());
        if (event.getEntity() instanceof User) {
            User user = (User) event.getEntity();
            log.info(user);
            UserES userES = userMapper.userToUserES(user);
            userESRepo.save(userES);
        }
    }

    @Override
    public void onPostDelete(PostDeleteEvent event) {
        log.debug(event.getEntity() instanceof User);
        log.debug(event.getDeletedState());
        if (event.getEntity() instanceof User) {
            User user = (User) event.getEntity();
            log.info(user);
            UserES userES = userMapper.userToUserES(user);
            userESRepo.delete(userES);
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister persister) {
        log.info(persister.getClassMetadata().getMappedClass());
        return true;
    }
}
