package com.example.study.ifs;

import com.example.study.model.network.Header;

import java.time.LocalDateTime;

public interface CrudInterface<Request, Response> {

    Header<Response> create(Header<Request> request);

    Header<Response> read(Long id);

    Header<Response> update(Header<Request> request);

    Header delete(Long id);
}
