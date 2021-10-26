package vn.alpaca.elastic.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.alpaca.elastic.dto.mapper.UserMapper;
import vn.alpaca.elastic.dto.response.UserResponse;
import vn.alpaca.elastic.entity.es.EsUser;
import vn.alpaca.elastic.entity.jpa.User;
import vn.alpaca.elastic.exception.ResourceNotFoundException;
import vn.alpaca.elastic.repository.es.UserESRepository;
import vn.alpaca.elastic.repository.jpa.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userJpaRepo;
    private final UserESRepository userEsRepo;

    private final UserMapper userMapper;

    public List<UserResponse> findAll() {
        List<User> users = userJpaRepo.findAll();

        List<EsUser> esUsers = users.stream()
                .map(userMapper::userToEsUser)
                .collect(Collectors.toList());
        userEsRepo.saveAll(esUsers);

        return users.stream()
                .map(userMapper::userToUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse findById(int id) {
        EsUser esUser = userEsRepo.findById(id)
                .orElse(null);
        log.info("Is Elastic data not null? " + !ObjectUtils.isEmpty(esUser));
        log.info(esUser);
        UserResponse response;
        if (esUser == null) {
            User user = userJpaRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Not found user with id " + id
                    ));
            response = userMapper.userToUserResponse(user);
        } else {
            response = userMapper.esUserToUserResponse(esUser);
        }

        return response;
    }
}
