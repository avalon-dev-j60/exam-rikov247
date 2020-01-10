package ru.cartogram;
// https://stackoverflow.com/questions/41800279/unable-to-edit-svg-using-batik-in-java
// https://stackoverflow.com/questions/13229690/checking-and-deleting-attributes-in-svg-using-batik-in-java
// https://www.drawsvg.org/drawsvg.html Рисовать svg онлайн

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.anim.dom.SVGDOMImplementation;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.util.XMLResourceDescriptor;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CreateCartogram {

    private String fullFileName; // полный путь (включая имя и расширение файла) к таблице
    private String fileName; // новое указываемое имя для SVG файла
    private String typeOfDirection; // тип направления (3, 4, кольцо и т.п.)
    private String uriPattern; // путь к шаблону картограммы

    private JSVGCanvas svgCanvas = new JSVGCanvas();
    private File file; // файл новой создаваемой картограммы

    private Document doc = (SVGDOMImplementation.getDOMImplementation()).
            createDocument(SVGDOMImplementation.SVG_NAMESPACE_URI, "svg", null);

    private SVGGraphics2D svgGenerator = new SVGGraphics2D(doc);

    public void paint(Graphics2D g2d, String s, int x, int y) {
        g2d.setPaint(Color.BLACK);
        Font font = new Font("Ariel", Font.BOLD, 12);
        g2d.setFont(font);
        g2d.drawString(s, x, y);
    }

    public CreateCartogram(String fullFileName, String typeOfDirection) {
        this.fullFileName = fullFileName;
        this.typeOfDirection = typeOfDirection;
    }

    // fileName - индивидуальная добавка к имени каждой картограммы
    // Из fullFileName получаем путь к месту хранения excel
    public CreateCartogram(String fullFileName, String typeOfDirection, String fileName) {
        this.fullFileName = fullFileName;
        this.typeOfDirection = typeOfDirection;
        this.fileName = fileName;
    }

    public JPanel initialize() throws IOException, URISyntaxException {

        // Получаем файл шаблон
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);

        if (typeOfDirection.equalsIgnoreCase("4")) {
            uriPattern = this.getClass().getResource("/resources/cartogram/cartogram4.svg").toURI().toString();
        }
        if (typeOfDirection.equalsIgnoreCase("4Circle")) {
            uriPattern = this.getClass().getResource("/resources/cartogram/cartogram4circle.svg").toURI().toString();
        }
        if (typeOfDirection.equalsIgnoreCase("3Right")) {
            uriPattern = this.getClass().getResource("/resources/cartogram/cartogram3right.svg").toURI().toString();
        }
        if (typeOfDirection.equalsIgnoreCase("3Up")) {
            uriPattern = this.getClass().getResource("/resources/cartogram/cartogram3up.svg").toURI().toString();
        }
        if (typeOfDirection.equalsIgnoreCase("3Down")) {
            uriPattern = this.getClass().getResource("/resources/cartogram/cartogram3down.svg").toURI().toString();
        }
        if (typeOfDirection.equalsIgnoreCase("3Left")) {
            uriPattern = this.getClass().getResource("/resources/cartogram/cartogram3left.svg").toURI().toString();
        }
        if (typeOfDirection.equalsIgnoreCase("2")) {
            uriPattern = this.getClass().getResource("/resources/cartogram/cartogram3down.svg").toURI().toString();
        }
        // Парсим его (читаем) и сохраняем в новый файл - который в дальнейшем будет использоваться
        doc = f.createDocument(uriPattern); // парсим передаваемый файл и создаем из него документ (то есть таким образом его выводим)
        svgGenerator = new SVGGraphics2D(doc);
        // Если приписка к файлу есть, то дописываем ее
        if (fileName != null) {
            file = new File(fullFileName.substring(0, fullFileName.lastIndexOf("."))
                    + "_"
                    + fileName
                    + ".svg");
        } else {
            file = new File(fullFileName.substring(0, fullFileName.lastIndexOf("\\"))
                    + "/"
                    + fullFileName.substring(fullFileName.lastIndexOf("\\"), fullFileName.lastIndexOf("."))
                    + ".svg");
        }
        // создаем файл в той же директории, что и таблица и с тем же именем
        saveSvgDocumentToFile(doc, file);

        return createCartogramPanel();
    }

    // Сохранить документ SVG в файл .svg
    private void saveSvgDocumentToFile(Document document, File file)
            throws FileNotFoundException, IOException {
        svgGenerator = new SVGGraphics2D(document);
        try (Writer out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8")) {
            svgGenerator.stream(document.getDocumentElement(), out);
            out.close();
        }
    }

    private JPanel createCartogramPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        svgCanvas = new JSVGCanvas();
        svgCanvas.setDocumentState(svgCanvas.ALWAYS_DYNAMIC);
        svgCanvas.setURI(file.toURI().toString());

        panel.add(svgCanvas);

        return panel;
    }

    // Меняем значение элемента, найденного по ID. Меняем текст для одного его tspan, если он есть.
    public void changeValueWithoutSave(String ID, String text) {
        Element svg = doc.getElementById(ID); // получаем элемент из svg документа по ID
        if (ID != null && svg != null) {
            // Если у объекта есть текст, то для него и меняем текст
            if (svg.getElementsByTagName("tspan").item(0) == null) {
                svg.setTextContent(text);
            }
            // Если у объекта есть свойство span, и для него есть текст, то для него и меняем текст
            if (svg.getElementsByTagName("tspan").item(0) != null) {
                svg.getElementsByTagName("tspan").item(0).setTextContent(text); // записываем новое значение для элемента с выбранным ID. Изменения записываются в структуру файла
            }
        }
    }

    // Меняем значение элемента, найденного по ID. Меняем текст для его tspan1, если он есть.
    public void changeValueWithoutSaveTspan1(String ID, String text) {
        Element svg = doc.getElementById(ID); // получаем элемент из svg документа по ID
        if (ID != null && svg != null) {
            if (svg.getElementsByTagName("tspan").item(0) != null) {
                svg.getElementsByTagName("tspan").item(0).setTextContent(text); // записываем новое значение для элемента с выбранным ID. Изменения записываются в структуру файла
            }
        }
    }

    // Меняем значение элемента, найденного по ID. Меняем текст для его tspan2, если он есть.
    public void changeValueWithoutSaveTspan2(String ID, String text) {
        Element svg = doc.getElementById(ID); // получаем элемент из svg документа по ID
        if (ID != null && svg != null) {
            if (svg.getElementsByTagName("tspan").item(1) != null) {
                svg.getElementsByTagName("tspan").item(1).setTextContent(text); // записываем новое значение для элемента с выбранным ID. Изменения записываются в структуру файла
            }
        }
    }

    // Меняем значение элемента, найденного по ID. Меняем текст для двух его tspan, если они есть.
    public void changeValueWithoutSave(String ID, String text1, String text2) {
        Element svg = doc.getElementById(ID); // получаем элемент из svg документа по ID

        // Если у объекта есть свойство span, и для него есть текст, то для него и меняем текст
        if (ID != null && svg != null) {
            if (svg.getElementsByTagName("tspan").item(0) != null && svg.getElementsByTagName("tspan").item(1) != null) {
                svg.getElementsByTagName("tspan").item(0).setTextContent(text1); // записываем новое значение для элемента с выбранным ID. Изменения записываются в структуру файла
                svg.getElementsByTagName("tspan").item(1).setTextContent(text2); // записываем новое значение для элемента с выбранным ID. Изменения записываются в структуру файла
            }
        }
    }

    // Сохраняем изменения в документе SVG и загружаем его на SVGCanvas, таким образом обновляя картинку
    public void saveChangeValue() {
        try {
            saveSvgDocumentToFile(doc, file); // сохраняем изменения в файл
            svgCanvas.setURI(file.toURI().toString()); // устанавливаем на canvas файл с сохраненными изменениями (таким образом обновляем изображение svg
        } catch (IOException ex) {
            Logger.getLogger(CreateCartogram.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Получаем текст по ID 
    public String getFullValue(String ID) {
        Element svg = doc.getElementById(ID); // получаем элемент из svg документа по ID
        String text, text1, text2;
        if (ID != null && svg != null) {
            // Если у объекта есть свойство span, и для него есть текст, то для него и меняем текст
            if (svg.getElementsByTagName("tspan").item(0) != null && svg.getElementsByTagName("tspan").item(1) != null) {
                text1 = svg.getElementsByTagName("tspan").item(0).getTextContent(); // записываем новое значение для элемента с выбранным ID. Изменения записываются в структуру файла
                text2 = svg.getElementsByTagName("tspan").item(1).getTextContent(); // записываем новое значение для элемента с выбранным ID. Изменения записываются в структуру файла
                return (text1 + text2);
            }
            if (svg.getElementsByTagName("tspan").item(0) != null && svg.getElementsByTagName("tspan").item(1) == null) {
                text1 = svg.getElementsByTagName("tspan").item(0).getTextContent(); // записываем новое значение для элемента с выбранным ID. Изменения записываются в структуру файла
                return text1;
            }
            if (svg.getElementsByTagName("tspan").item(0) == null && svg.getElementsByTagName("tspan").item(1) == null) {
                text = svg.getTextContent(); // записываем новое значение для элемента с выбранным ID. Изменения записываются в структуру файла
                return text;
            }
        }
        return "";
    }

    public String getValueTspan1(String ID) {
        Element svg = doc.getElementById(ID); // получаем элемент из svg документа по ID
        String text;
        // Если у объекта есть свойство span, и для него есть текст, то для него и меняем текст
        if (ID != null && svg != null) {
            if (svg.getElementsByTagName("tspan").item(0) != null) {
                text = svg.getElementsByTagName("tspan").item(0).getTextContent(); // записываем новое значение для элемента с выбранным ID. Изменения записываются в структуру файла
                return text;
            }
        }
        return "";
    }

    public String getValueTspan2(String ID) {
        Element svg = doc.getElementById(ID); // получаем элемент из svg документа по ID
        String text;
        // Если у объекта есть свойство span, и для него есть текст, то для него и меняем текст
        if (ID != null && svg != null) {
            if (svg.getElementsByTagName("tspan").item(0) != null && svg.getElementsByTagName("tspan").item(1) != null) {
                text = svg.getElementsByTagName("tspan").item(1).getTextContent(); // записываем новое значение для элемента с выбранным ID. Изменения записываются в структуру файла
                return text;
            }
        }
        return "";
    }

}
