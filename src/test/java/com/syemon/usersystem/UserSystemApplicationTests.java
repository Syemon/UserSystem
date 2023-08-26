package com.syemon.usersystem;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(value = "test")
class UserSystemApplicationTests extends PostgresTestContainerResourceTest {

    @Test
    void contextLoads() {
    }

}
