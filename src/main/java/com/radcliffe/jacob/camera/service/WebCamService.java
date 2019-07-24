package com.radcliffe.jacob.camera.service;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamException;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class WebCamService {

    private Webcam camera;
    private List<Webcam> camerasList;

    public WebCamService() {
        this.camerasList = tryToFindCameras();
        this.camera = camerasList.get(0);
    }

    private List<Webcam> tryToFindCameras() {
        List<Webcam> webcams = null;
        try {
            webcams = Webcam.getWebcams();
        } catch (WebcamException e) {
            System.err.println("!!!!! Unable to find any cameras.");
            e.printStackTrace();
        }
        return webcams;
    }

    public String[] getCamerasArray() {
        String[] cameraNames = new String[camerasList.size()];
        for (int i = 0; i < camerasList.size(); i++) {
            cameraNames[i] = camerasList.get(i).getName();
        }
        return cameraNames;
    }
}
