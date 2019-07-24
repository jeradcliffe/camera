package com.radcliffe.jacob.camera.service;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamException;
import com.github.sarxos.webcam.WebcamPanel;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;

import static com.github.sarxos.webcam.WebcamResolution.VGA;
import static java.awt.Component.BOTTOM_ALIGNMENT;
import static java.awt.Component.CENTER_ALIGNMENT;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

@Service
public class WebCamOrchestrator {

    private Webcam camera;
    private List<Webcam> cameraList;

    private JFrame frame;
    private WebcamPanel panel;
    private JComboBox comboBox;


    public WebCamOrchestrator() {
        this.cameraList = tryToFindCameras();
        this.camera = cameraList.get(0);

        this.frame = getjFrame();
        this.panel = getWebcamPanel();
        this.frame.add(panel);
        this.comboBox = getjComboBox(cameraList);
        this.frame.setVisible(true);
        this.panel.add(comboBox);
    }

    private JComboBox<String> getjComboBox(List<Webcam> cameraList) {
        JComboBox<String> cb = getStringJComboBox(cameraList);
        cb.setMaximumSize(cb.getPreferredSize());
        cb.setAlignmentX(CENTER_ALIGNMENT);
        cb.setAlignmentY(BOTTOM_ALIGNMENT);
        cb.addActionListener(e -> {
            String selectedCam = (String) cb.getSelectedItem();
            System.out.println("!!!!! selectedCam = " + selectedCam);
            camera = cameraList.stream()
                    .filter(x -> x.getName().equals(selectedCam))
                    .findFirst()
                    .orElse(null);
            frame.remove(panel);
            panel = getWebcamPanel();
            frame.add(panel);
            frame.pack();
        });
        return cb;
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

    private JComboBox<String> getStringJComboBox(List<Webcam> webcams) {
        String[] cameraNames = new String[webcams.size()];
        for (int i = 0; i < webcams.size(); i++) {
            cameraNames[i] = webcams.get(i).getName();
        }
        return new JComboBox<>(cameraNames);
    }

    private JFrame getjFrame() {
        JFrame frame = new JFrame("Test Panel");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocation(430, 100);
        return frame;
    }

    private WebcamPanel getWebcamPanel() {
        camera.setViewSize(VGA.getSize());

        WebcamPanel panel = new WebcamPanel(camera);
        panel.setFPSDisplayed(true);
        panel.setDisplayDebugInfo(true);
        panel.setImageSizeDisplayed(true);
        panel.setMirrored(true);
        return panel;
    }
}
