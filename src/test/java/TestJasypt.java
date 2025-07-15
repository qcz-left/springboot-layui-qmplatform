import com.qcz.qmplatform.QmplatformApplication;
import jakarta.annotation.Resource;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = QmplatformApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestJasypt {

    @Resource
    private StringEncryptor encryptor;

    @Test
    public void test() {
        System.out.println(encryptor.encrypt("password"));
    }

}
