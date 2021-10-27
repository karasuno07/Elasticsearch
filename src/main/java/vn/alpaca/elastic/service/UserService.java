package vn.alpaca.elastic.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.alpaca.elastic.dto.request.UserFilter;
import vn.alpaca.elastic.dto.request.UserRequest;
import vn.alpaca.elastic.entity.es.UserES;
import vn.alpaca.elastic.entity.jpa.Role;
import vn.alpaca.elastic.entity.jpa.User;
import vn.alpaca.elastic.exception.ResourceNotFoundException;
import vn.alpaca.elastic.mapper.UserMapper;
import vn.alpaca.elastic.repository.es.UserESRepository;
import vn.alpaca.elastic.repository.jpa.RoleRepository;
import vn.alpaca.elastic.repository.jpa.UserRepository;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userJpaRepo;
    private final UserESRepository userEsRepo;

    private final RoleRepository roleJpaRepo;

    private final ElasticsearchRestTemplate restTemplate;
    private final IndexCoordinates index = IndexCoordinates.of("users");

    private final UserMapper userMapper;

    public Page<User> findAll(UserFilter filter, Pageable pageable) {
        userEsRepo.saveAll(userJpaRepo.findAll().stream().map(userMapper::userToUserES).collect(
                Collectors.toList()));
        Page<User> users;
        System.out.println(filter.getFrom().toInstant().toString());
        System.out.println(filter.getTo().toInstant().toString());
        BoolQueryBuilder query = QueryBuilders.boolQuery()
                .should(wildcardQuery("username",
                        "*" + filter.getUsername() + "*"))
                .should(matchQuery("full_name", filter.getFullName()))
                .should(termQuery("id_card_number", filter.getIdCardNumber()))
                .should(termQuery("phone_numbers", filter.getPhoneNumber()))
                .should(matchQuery("address", filter.getAddress()));

//        if (!ObjectUtils.isEmpty(filter.getFrom()) && !ObjectUtils.isEmpty(filter.getTo())) {
//            query.should(rangeQuery("date_of_birth")
//                    .gte(filter.get))
//        }

        if (!ObjectUtils.isEmpty(filter.getGender())) {
            query.should(matchQuery("gender", filter.getGender()));
        }

        if (!ObjectUtils.isEmpty(filter.getActive())) {
            query.should(matchQuery("active", filter.getActive()));
        }

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(query)
                .withPageable(pageable)
                .build();
        SearchHits<UserES> hits =
                restTemplate.search(searchQuery, UserES.class, index);

        if (hits.hasSearchHits()) {
            SearchPage<UserES> page =
                    SearchHitSupport.searchPageFor(hits, pageable);
            users = new PageImpl<>(
                    page.getContent().stream()
                            .map(SearchHit::getContent)
                            .map(userMapper::userESToUser)
                            .collect(Collectors.toList()),
                    page.getPageable(),
                    page.getTotalElements()
            );
            log.info("FOUND: " + users);
        } else {
            users = userJpaRepo.findAll(pageable);
            log.info("USE DB: " + users);
        }

        return users;
    }

    public User findById(int id) {
        User user;

        Optional<UserES> optional = userEsRepo.findById(id);
        if (optional.isPresent()) {
            user = userMapper.userESToUser(optional.get());
        } else {
            user = userJpaRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Not found user with id " + id
                    ));
        }

        return user;
    }

    public User findByUsername(String username) {
        User user;

        Optional<UserES> optional = userEsRepo.findByUsername(username);
        if (optional.isPresent()) {
            user = userMapper.userESToUser(optional.get());
        } else {
            user = userJpaRepo.findByUsername(username).orElseThrow(
                    () -> new ResourceNotFoundException(
                            "Not found user with username " +
                                    username
                    ));
        }

        return user;
    }

    public User createUser(UserRequest requestData) {
        User user = userMapper.userRequestToUser(requestData);

        Role role = roleJpaRepo.findById(requestData.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Not found role with id " + requestData.getRoleId()
                ));
        user.setRole(role);

        userJpaRepo.save(user);

        return user;
    }

    public User updateUser(int userId, UserRequest requestData) {
        User existingUser = userJpaRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Not found user with id " + userId
                ));

        User updatedUser = userMapper.userRequestToUser(requestData);
        updatedUser.setId(existingUser.getId());

        Role role = roleJpaRepo.findById(requestData.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Not found role with id " + requestData.getRoleId()
                ));
        updatedUser.setRole(role);

        userJpaRepo.save(updatedUser);

        return updatedUser;
    }

    public void activateUser(int userId) {
        User user = userJpaRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Not found user with id " + userId
                ));
        user.setActive(true);
        userJpaRepo.save(user);
    }

    public void deactivateUser(int userId) {
        User user = userJpaRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Not found user with id " + userId
                ));
        user.setActive(false);
        userJpaRepo.save(user);
    }
}
