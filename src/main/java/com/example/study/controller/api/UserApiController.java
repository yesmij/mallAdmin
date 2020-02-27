package com.example.study.controller.api;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.UserApiResonse;
import com.example.study.service.UserApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserApiController implements CrudInterface<UserApiRequest, UserApiResonse> {

    @Autowired
    private UserApiLogicService userApiLogicService;

    @Override
    @PostMapping("")    // api/user
    public Header<UserApiResonse> create(@RequestBody Header<UserApiRequest> request) {
        log.info("{}, {}", request, "create");
        return userApiLogicService.create(request);
    }

    @Override
    @GetMapping("{param}")    // api/user/param
    public Header<UserApiResonse> read(@PathVariable(name="param") Long id) {
        log.info("read id : {}", id);
        return userApiLogicService.read(id);
    }

    @Override
    @PutMapping("")    // api/user
    public Header<UserApiResonse> update(@RequestBody Header<UserApiRequest> request) {
        log.info("update user : {}", request);
        return userApiLogicService.update(request);
    }

    @Override
    @DeleteMapping("{id}")  //  /api/user/{id}
    public Header<UserApiResonse> delete(@PathVariable Long id) {
        log.info("delete user id : {}", id);
        return userApiLogicService.delete(id);
    }

}
