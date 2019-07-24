package com.radcliffe.jacob.camera;

import com.radcliffe.jacob.camera.service.WebCamOrchestrator;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        WebCamOrchestrator service = new WebCamOrchestrator();
    }
}
