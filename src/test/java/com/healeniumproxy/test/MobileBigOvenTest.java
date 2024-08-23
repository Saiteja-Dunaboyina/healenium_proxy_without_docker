package com.healeniumproxy.test;

import com.healeniumproxy.base.Base;
import org.testng.annotations.Test;

public class MobileBigOvenTest extends Base {

    @Test
    public void loginTest() {
        test = extent.createTest("Login with valid credentials").assignAuthor("Sai Teja");
        System.out.println("Mobile Testing");
    }

}
