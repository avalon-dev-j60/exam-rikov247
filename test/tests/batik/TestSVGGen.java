package tests.batik;
// https://stackoverflow.com/questions/41800279/unable-to-edit-svg-using-batik-in-java
//https://stackoverflow.com/questions/13229690/checking-and-deleting-attributes-in-svg-using-batik-in-java

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.anim.dom.SVGDOMImplementation;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.util.XMLResourceDescriptor;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TestSVGGen {

    private static JFrame frame;
    private static JSVGCanvas svgCanvas = new JSVGCanvas();
    private static File file;

    private static Document doc = (SVGDOMImplementation.getDOMImplementation()).
            createDocument(SVGDOMImplementation.SVG_NAMESPACE_URI, "svg", null);

    private static SVGGraphics2D svgGenerator = new SVGGraphics2D(doc);

    public void paint(Graphics2D g2d, String s, int x, int y) {
        g2d.setPaint(Color.BLACK);
        Font font = new Font("Ariel", Font.BOLD, 12);
        g2d.setFont(font);
        g2d.drawString(s, x, y);
    }

    public TestSVGGen() {
        frame = new JFrame("Просмотр векторной SVG-графики");
        // Display the frame.
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setSize(600, 600);
        frame.getContentPane().add(createGUI());
        frame.repaint();
        frame.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        // Get a DOMImplementation.
//        DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
//
//        // Create an instance of org.w3c.dom.Document.
//        final String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
//        Document document = domImpl.createDocument(svgNS, "svg", null);
//
//        // Создаем представление SVG генератора (рисуем в нем)
//
//        // Ask the test to render into the SVG Graphics2D implementation.
//        TestSVGGen test = new TestSVGGen();
//
//        test.paint(svgGenerator, "0-1-111-5", 50, 50);
//        test.paintd(svgGenerator, "0-1-111-5", 550, 150, new Font("Ariel", Font.BOLD, 22));

        // Получаем файл шаблон
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
        String uri = "TrafficWithText.svg";
        // Парсим его (читаем) и сохраняем в новый файл - который в дальнейшем будет использоваться
        try {
            doc = f.createDocument(uri); // парсим передаваемый файл и создаем из него документ (то есть таким образом его выводим)
            svgGenerator = new SVGGraphics2D(doc);
            file = new File("C:\\Users\\270419\\Desktop\\img.svg");
            saveSvgDocumentToFile(doc, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Добавляем svg изображение куда нужно и делаем с ним что нужно (динамически обновляем)
        new TestSVGGen();
    }

    // Сохранить документ SVG в файл .svg
    private static void saveSvgDocumentToFile(Document document, File file)
            throws FileNotFoundException, IOException {
        svgGenerator = new SVGGraphics2D(document);
        try (Writer out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8")) {
            svgGenerator.stream(document.getDocumentElement(), out);
            out.close();
        }
    }

    private static JPanel createGUI() {
        JPanel panel = new JPanel(new BorderLayout());

        svgCanvas = new JSVGCanvas();
        svgCanvas.setDocumentState(svgCanvas.ALWAYS_DYNAMIC);
        svgCanvas.setURI(file.toURI().toString());

        panel.add(svgCanvas, BorderLayout.CENTER);
        JButton button = new JButton("+1");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeValue("Total_outside_Left", "156-874-222");
                changeValue("Left_around", "156-874-222");
            }
        });
        panel.add(button, BorderLayout.SOUTH);
        frame.add(panel);

        return panel;
    }

    private static void changeValue(String ID, String text) {
        Element svg = doc.getElementById(ID); // получаем элемент из svg документа по ID
        String textOld = svg.getElementsByTagName("tspan").item(0).getTextContent(); // получаем текст этого элемента
//        Integer textOldInt = Integer.valueOf(textOld); // переводим в число
//        textOldInt = textOldInt + 1;
        svg.getElementsByTagName("tspan").item(0).setTextContent(text); // записываем новое значение для элемента с выбранным ID. Изменения записываются в структуру файла

        try {
            saveSvgDocumentToFile(doc, file); // сохраняем изменения в файл
            svgCanvas.setURI(file.toURI().toString()); // устанавливаем на canvas файл с сохраненными изменениями (таким образом обновляем изображение svg
        } catch (IOException ex) {
            Logger.getLogger(TestSVGGen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
