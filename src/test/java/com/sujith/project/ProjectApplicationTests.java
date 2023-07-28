package com.sujith.project;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectApplicationTests {
    @Test
    void testMain() {
        ProjectApplication.main(new String[]{});
        assertTrue(true);
    }
}
