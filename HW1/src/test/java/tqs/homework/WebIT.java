    package tqs.homework;

    import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.api.AfterEach;
    import org.junit.jupiter.api.BeforeEach;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

    import org.springframework.boot.test.web.server.LocalServerPort;
    import org.springframework.test.context.DynamicPropertyRegistry;
    import org.springframework.test.context.DynamicPropertySource;
    import org.testcontainers.containers.MySQLContainer;
    import org.testcontainers.junit.jupiter.Container;
    import org.testcontainers.junit.jupiter.Testcontainers;



    @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
    @Testcontainers
    @DisabledIfEnvironmentVariable(named = "GITHUB_ACTIONS", matches = "true") //these tests will be skipped when running on github actions (but not locally). This is because github actions lacks the drivers needed to run selenium.
    public class WebIT {
        @LocalServerPort
        int port; 

        private SeleniumManager selenium;

        @Container
        public static MySQLContainer<?> container= new MySQLContainer<>("mysql:latest")
            .withUsername("sa")
            .withPassword("password")
            .withDatabaseName("showcase");

        @DynamicPropertySource
        static void properties(DynamicPropertyRegistry registry){
            registry.add("spring.datasource.url",container::getJdbcUrl);
            registry.add("spring.datasource.password",container::getPassword);
            registry.add("spring.datasource.username",container::getUsername);
            registry.add("spring.datasource.driver-class-name",container::getDriverClassName);
        }

        @BeforeEach
        public void setup(){
            selenium=new SeleniumManager(port);
        }

        @AfterEach
        public void shutdown(){
            selenium.shutdown();
        }

        @Test
        void testLoginSuccessful(){
            selenium.clickLink("Login");
            selenium.typeById("Bruno", "username");
            selenium.typeById("password", "password");
            selenium.submit();
            selenium.assertHome();
        }

        @Test
        void testLoginWrongUsername(){
            selenium.clickLink("Login");
            selenium.typeById("something", "username");
            selenium.typeById("password", "password");
            selenium.submit();
            selenium.assertAlert("Login failed");
        }

        @Test
        void testLoginWrongPassword(){
            selenium.clickLink("Login");
            selenium.typeById("Bruno", "username");
            selenium.typeById("something", "password");
            selenium.submit();
            selenium.assertAlert("Login failed");
        }

        @Test
        void testRegisterSuccesful(){
            selenium.clickLink("Register");
            selenium.typeById("Rodrigo", "username");
            selenium.typeById("password", "password");
            selenium.submit();
            selenium.assertHome();
        }

        @Test
        void testRegisterUsernameTaken(){
            selenium.clickLink("Register");
            selenium.typeById("Bruno", "username");
            selenium.typeById("something", "password");
            selenium.submit(); 
            selenium.assertAlert("Register failed");
        }

        @Test
        void testBookingSucessful(){
            selenium.insertIntoLocalStorage("token", "3c469e9d6c5875d37a43f353d4f88e61fcf812c66eee3457465a40b0da4153e0");
            selenium.selectById(1, "fromLocation");
            selenium.selectById(2, "toLocation");
            selenium.typeById("2024-04-06","tripDate");
            selenium.clickById("searchBtn");
            selenium.typeById("12", "seat1");
            selenium.clickById("btn1");
            selenium.assertAlert("scheduling successful");
        }

        @Test
        void testBookingSeatOccupied(){
            selenium.insertIntoLocalStorage("token", "3c469e9d6c5875d37a43f353d4f88e61fcf812c66eee3457465a40b0da4153e0");
            selenium.selectById(1, "fromLocation");
            selenium.selectById(2, "toLocation");
            selenium.typeById("2024-04-06","tripDate");
            selenium.clickById("searchBtn");
            selenium.typeById("11", "seat1");
            selenium.clickById("btn1");
            selenium.assertAlert("scheduling failed");
        }
    }
