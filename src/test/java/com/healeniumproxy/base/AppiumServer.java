package com.healeniumproxy.base;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.File;

public class AppiumServer {
    public static AppiumDriverLocalService getServer() {
        AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();
        serviceBuilder.usingPort(4723).withIPAddress("127.0.0.1")
                .usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"))
                .withAppiumJS(new File("C:\\Users\\"+ System.getProperty("user.name") +"\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE).withLogFile(new File("AppiumLog.txt"))
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE);
        return AppiumDriverLocalService.buildService(serviceBuilder);
    }
}
