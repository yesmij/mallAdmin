package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entity.User;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.UserApiResonse;
import com.example.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserApiLogicService implements CrudInterface<UserApiRequest, UserApiResonse> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Header<UserApiResonse> create(Header<UserApiRequest> request) {

        UserApiRequest userApiRequest = request.getData();

        User user = User.builder()
                .account(userApiRequest.getAccount())
                .password(userApiRequest.getPassword())
                .status("REGISTERED")
                .phoneNumber(userApiRequest.getPhoneNumber())
                .email(userApiRequest.getEmail())
                .registeredAt(LocalDateTime.now())
                .build();
        User newUser = userRepository.save(user);

        return response(newUser);
    }

    @Override
    public Header<UserApiResonse> read(Long id) {

        Optional<User>optionalUser = userRepository.findById(id);

        return optionalUser.map(
                user -> response(user)
        )
        .orElseGet(
                () -> Header.ERROR("No Data")
        );
    }

    @Override
    public Header<UserApiResonse> update(Header<UserApiRequest> request) {

        UserApiRequest userApiRequest = request.getData();

        Optional<User> optionalUser = userRepository.findById(userApiRequest.getId());

        return optionalUser.map( user -> {
            user.setAccount(userApiRequest.getAccount())
                    .setPassword(userApiRequest.getPassword())
                    .setStatus(userApiRequest.getStatus())
                    .setPhoneNumber(userApiRequest.getPhoneNumber())
                    .setEmail(userApiRequest.getEmail())
                    .setRegisteredAt(userApiRequest.getRegisteredAt())
                    .setUnregisteredAt(userApiRequest.getUnregisteredAt());
            return user;
            //User updatedUser = userRepository.u
        })
        .map( user -> userRepository.save(user))    // update (user가 id값이 있기에 save가 아닌 update 일어남
        .map( updateUser -> response(updateUser))
        .orElseGet( () -> Header.ERROR("No User"));
    }

    @Override
    public Header delete(Long id) {

        Optional<User> optionalUser = userRepository.findById(id);

        return optionalUser.map( user -> {
            userRepository.delete(user);
            return Header.OK();
        })
        .orElseGet(() -> Header.ERROR("No User"));
    }

    private Header<UserApiResonse> response(User user) {
        UserApiResonse userApiResonse = UserApiResonse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .password(user.getPassword())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .registeredAt(user.getRegisteredAt())
                .unregisteredAt(user.getUnregisteredAt())
                .build();

        // Header + data
        return Header.OK(userApiResonse);
    }
}
