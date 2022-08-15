package ru.hogwarts.school.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.service.InfoService;

import java.util.stream.Stream;

@Service
public class InfoServiceImpl implements InfoService {

    @Value("${server.port}")
    private String port;

    public String getPort() {
        return port;
    }

    @Override
    public Integer bigInt() {
        int sum = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        return sum;
    }

}
