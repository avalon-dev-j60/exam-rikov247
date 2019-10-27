package tests.batik;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import org.apache.batik.swing.JSVGCanvas;


import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.svg.SVGDocumentLoaderAdapter;
import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;
import org.apache.batik.swing.svg.GVTTreeBuilderAdapter;
import org.apache.batik.swing.svg.GVTTreeBuilderEvent;

public class SVGApplication {

    private JFrame frame;
    private JLabel label = new JLabel();
    private JSVGCanvas svgCanvas = new JSVGCanvas();

    public SVGApplication() {
        frame = new JFrame("Просмотр векторной SVG-графики");
        // Display the frame.
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setSize(600, 600);
        frame.getContentPane().add(createGUI());
        frame.repaint();
        frame.setVisible(true);
    }

    private void addJSVGCanvasListeners() {
        // Set the JSVGCanvas listeners.
        svgCanvas.addSVGDocumentLoaderListener(new SVGDocumentLoaderAdapter() {
            public void documentLoadingStarted(SVGDocumentLoaderEvent e) {
                label.setText("Document loading ...");
            }

            public void documentLoadingCompleted(SVGDocumentLoaderEvent e) {
                label.setText("");
            }
        });

        svgCanvas.addGVTTreeBuilderListener(new GVTTreeBuilderAdapter() {
            public void gvtBuildStarted(GVTTreeBuilderEvent e) {
                label.setText("Build started ...");
            }

            public void gvtBuildCompleted(GVTTreeBuilderEvent e) {
                label.setText("");
                frame.pack();
            }
        });

        svgCanvas.addGVTTreeRendererListener(new GVTTreeRendererAdapter() {
            public void gvtRenderingPrepare(GVTTreeRendererEvent e) {
                label.setText("Rendering ...");
            }

            public void gvtRenderingCompleted(GVTTreeRendererEvent e) {
                label.setText("");
            }
        });
    }

    private JPanel createGUI() {
        final JPanel panel = new JPanel(new BorderLayout());

        svgCanvas = new JSVGCanvas();
        addJSVGCanvasListeners();

        JButton button = new JButton("Загрузить");
        label = new JLabel();

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(button);
        top.add(label);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fc = new JFileChooser(".");
                int choice = fc.showOpenDialog(panel);
                if (choice == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    svgCanvas.setURI(file.toURI().toString());
                }
            }
        });
        panel.add("Center", svgCanvas);
        panel.add("South", top);

        return panel;
    }

    public static void main(String[] args) {
        new SVGApplication();
    }
}
