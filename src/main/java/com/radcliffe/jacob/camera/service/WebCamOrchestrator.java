package com.radcliffe.jacob.camera.service;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import org.springframework.stereotype.Service;

import javax.swing.*;

import java.awt.event.ActionListener;

import static com.github.sarxos.webcam.WebcamResolution.VGA;
import static java.awt.Component.BOTTOM_ALIGNMENT;
import static java.awt.Component.CENTER_ALIGNMENT;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

@Service
public class WebCamOrchestrator {



    private JFrame frame;
    private WebcamPanel panel;
    private JComboBox comboBox;
    private WebCamService webCamService;

    public WebCamOrchestrator(WebCamService webCamService) {
        this.webCamService = webCamService;
        this.frame = getjFrame();
        this.frame = getjFrame();

        this.panel = getWebcamPanel();
        this.frame.add(panel);
        this.comboBox = getjComboBox();
        this.panel.add(comboBox);
        this.frame.setVisible(true);
    }

    private JFrame getjFrame() {
        JFrame jFrame = new JFrame("Test Panel");
        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jFrame.setSize(500, 500);
        jFrame.setLocation(430, 100);
        return jFrame;
    }

    private JComboBox<String> getjComboBox() {
        JComboBox<String> cb = new JComboBox<>(webCamService.getCamerasArray());
        cb.setMaximumSize(cb.getPreferredSize());
        cb.setAlignmentX(CENTER_ALIGNMENT);
        cb.setAlignmentY(BOTTOM_ALIGNMENT);
        cb.addActionListener(getComboBoxActionListener(cb));
        return cb;
    }

    public ActionListener getComboBoxActionListener(JComboBox<String> cb) {
        return e -> {
            String selectedCam = (String) cb.getSelectedItem();
            System.err.println("!!!!! selectedCam = " + selectedCam);
            webCamService.setCamera(webCamService.getCamerasList().stream()
                    .filter(x -> x.getName().equals(selectedCam))
                    .findFirst()
                    .orElse(null)
            );
            frame.remove(panel);
            panel = getWebcamPanel();
            frame.add(panel);
            frame.pack();
        };
    }


    private WebcamPanel getWebcamPanel() {
        Webcam camera = webCamService.getCamera();
        camera.setViewSize(VGA.getSize());

        WebcamPanel panel = new WebcamPanel(camera);
        panel.setFPSDisplayed(true);
        panel.setDisplayDebugInfo(true);
        panel.setImageSizeDisplayed(true);
        panel.setMirrored(true);
        return panel;
    }
}
