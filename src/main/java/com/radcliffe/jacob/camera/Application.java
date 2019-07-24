package com.radcliffe.jacob.camera;

import com.radcliffe.jacob.camera.service.WebCamOrchestrator;
import com.radcliffe.jacob.camera.service.WebCamService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        WebCamService webCamService = new WebCamService();
        WebCamOrchestrator service = new WebCamOrchestrator(webCamService);
    }
}
