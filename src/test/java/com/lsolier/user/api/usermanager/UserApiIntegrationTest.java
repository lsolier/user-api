package com.lsolier.user.api.usermanager;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = {"/application-test.properties"})
public class UserApiIntegrationTest {
}
