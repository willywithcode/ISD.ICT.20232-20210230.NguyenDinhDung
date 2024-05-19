package Test.VNPaySubsystem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import VNPaySubsystem.Config;
public class ConfigTest {

    @Test
    public void testGetIp() {
        String expectedIp = "123.123.123";
        String actualIp = Config.getIp();
        Assertions.assertEquals(expectedIp, actualIp);
    }

    @Test
    public void testGetRandNumWithValidNum() {
        int num = 10;

        String randNum = null;
        try {
            randNum = Config.getRandNum(num);
        } catch (IllegalArgumentException e) {
        }

        // Assert
        Assertions.assertNotNull(randNum);
        Assertions.assertEquals(num, randNum.length());
    }

    @Test
    public void testGetRandNumWithInvalidNegativeNum() {
        int num = -1;
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Config.getRandNum(num);
        });
    }

    @Test
    public void testGetRandNumWithInvalidNumZero() {
        int num = 0;
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Config.getRandNum(num);
        });
    }
}