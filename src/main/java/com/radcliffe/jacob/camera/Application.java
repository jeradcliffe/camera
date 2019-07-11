package com.radcliffe.jacob.camera;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamException;
import com.github.sarxos.webcam.WebcamPanel;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.util.List;

import static com.github.sarxos.webcam.WebcamResolution.VGA;
import static java.awt.Component.BOTTOM_ALIGNMENT;
import static java.awt.Component.CENTER_ALIGNMENT;
import static javax.swing.BoxLayout.Y_AXIS;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

@SpringBootApplication
public class Application {

    private static JFrame frame = null;
    private static Webcam camera = null;
    private static WebcamPanel panel = null;

    public static void main(String[] args) {
        List<Webcam> cameraList = tryToFindCameras();
        camera = cameraList.get(0);

        frame = getjFrame();
        panel = getWebcamPanel();
        frame.add(panel);

        getjComboBox(cameraList);

        frame.setVisible(true);
    }

    private static void getjComboBox(List<Webcam> cameraList) {
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
        panel.add(cb);
    }

    private static List<Webcam> tryToFindCameras() {
        List<Webcam> webcams = null;
        try {
            webcams = Webcam.getWebcams();
        } catch (WebcamException e) {
            System.err.println("!!!!! Unable to find any cameras.");
            e.printStackTrace();
        }
        return webcams;
    }

    private static JComboBox<String> getStringJComboBox(List<Webcam> webcams) {
        String[] cameraNames = new String[webcams.size()];
        for (int i = 0; i < webcams.size(); i++) {
            cameraNames[i] = webcams.get(i).getName();
        }
        return new JComboBox<>(cameraNames);
    }

    private static JFrame getjFrame() {
        JFrame frame = new JFrame("Test Panel");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocation(430, 100);
        return frame;
    }
    //		JFrame window = new JFrame("Test webcam panel");
//		window.add(panel);
//		window.setResizable(true);
//		window.setDefaultCloseOperation(EXIT_ON_CLOSE);
//		window.pack();
//		window.setVisible(true);

    private static WebcamPanel getWebcamPanel() {
        camera.setViewSize(VGA.getSize());

        WebcamPanel panel = new WebcamPanel(camera);
        panel.setFPSDisplayed(true);
        panel.setDisplayDebugInfo(true);
        panel.setImageSizeDisplayed(true);
        panel.setMirrored(true);
        return panel;
    }

    private static JPanel getjPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, Y_AXIS));
        return panel;
    }

}
