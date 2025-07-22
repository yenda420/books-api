package com.yenda.api.mappers;

import org.springframework.stereotype.Component;

@Component
public interface Mapper<A, B> {

    B mapTo(A a);

    A mapFrom(B b);
}
