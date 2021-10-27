package vn.alpaca.userservice.config.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.event.spi.*;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;
import vn.alpaca.userservice.entity.jpa.Role;
import vn.alpaca.userservice.mapper.RoleMapper;
import vn.alpaca.userservice.repository.es.RoleESRepository;

@Component
@Log4j2
@RequiredArgsConstructor
public class RoleEventListener implements
        PostInsertEventListener,
        PostUpdateEventListener,
        PostDeleteEventListener {

    private final RoleESRepository repository;
    private final RoleMapper mapper;


    @Override
    public void onPostInsert(PostInsertEvent event) {
        if (event.getEntity() instanceof Role) {
            log.info("ON CREATED: " + event.getEntity());
            Role role = (Role) event.getEntity();
            repository.save(mapper.roleToRoleES(role));
        }
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        if (event.getEntity() instanceof Role) {
            log.info("ON UPDATED: " + event.getEntity());
            Role role = (Role) event.getEntity();
            repository.save(mapper.roleToRoleES(role));
        }
    }

    @Override
    public void onPostDelete(PostDeleteEvent event) {
        if (event.getEntity() instanceof Role) {
            log.info("ON DELETED: " + event.getEntity());
            Role role = (Role) event.getEntity();
            repository.delete(mapper.roleToRoleES(role));
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister persister) {
        log.info(persister.getEntityName());
        return true;
    }
}
