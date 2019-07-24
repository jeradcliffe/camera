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

        this.comboBox = getjComboBox();

        this.panel = getWebcamPanel();
        this.panel.add(comboBox);

        this.frame.add(panel);
        this.frame.pack();
        this.frame.setVisible(true);
    }

    private JFrame getjFrame() {
        JFrame jFrame = new JFrame("Test Panel");
        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jFrame.setSize(500, 500);
        jFrame.setLocation(430, 100);
        return jFrame;
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

    private JComboBox<String> getjComboBox() {
        JComboBox<String> cb = new JComboBox<>(webCamService.getCamerasArray());
        cb.setMaximumSize(cb.getPreferredSize());
        cb.setAlignmentX(CENTER_ALIGNMENT);
        cb.setAlignmentY(BOTTOM_ALIGNMENT);
        cb.addActionListener(getComboBoxActionListener(cb));
        return cb;
    }

    private ActionListener getComboBoxActionListener(JComboBox<String> cb) {
        System.err.println("!!!!! selectedCam = " + cb.getSelectedItem());
        return e -> {
            webCamService.getCamera().close();
            webCamService.setCamera(webCamService.getCamerasList().stream()
                    .filter(x -> x.getName().equals(cb.getSelectedItem()))
                    .findFirst()
                    .orElse(null)
            );
            frame.remove(panel);
            panel = getWebcamPanel();
            frame.add(panel);
            panel.add(cb);
            frame.pack();
        };
    }
}
