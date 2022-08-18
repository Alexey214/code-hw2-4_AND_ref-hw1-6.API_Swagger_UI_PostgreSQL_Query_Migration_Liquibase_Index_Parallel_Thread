package ru.hogwarts.school.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.service.InfoService;

@Service
public class InfoServiceImpl implements InfoService {

    @Value("${server.port}")
    private String port;

    public String getPort() {
        return port;
    }

}
