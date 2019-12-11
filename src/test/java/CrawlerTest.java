import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerTest {
    @Before
    public void printPoem(String poem) {
        System.out.println(poem);
    }

    @Test
    public void printFuck() {
        System.out.println("草泥马");
    }
}