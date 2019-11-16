package tests.domXML;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SettingsTest {

    // Читаем в консоль файл XML
    public static void doSmtg() {
        try {
            // Создается построитель документа
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // Создается дерево DOM документа из файла
            Document document = documentBuilder.parse(getUserTableDirectory() + "Settings.xml");

            System.out.println(getValueNode(document, "ActionPlayPause")); // Получаем значение выбранного свойства (тега)
            setValueNode(document, "ActionPlayPause", "Yes");
            writeDocument(document);

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace(System.out);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(SettingsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Чтение значения из указанного тега (node)
    public static String getValueNode(Document document, String nodeName) throws XPathExpressionException {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        XPathExpression expr = xpath.compile("//" + nodeName); // Находим xpathВыражение с указанным нами тегом
        NodeList nl = (NodeList) expr.evaluate(document, XPathConstants.NODESET); // Переводим xpathВыражение с указанным нами тегом в список элементов с таким тегом
        return nl.item(0).getTextContent().trim(); // Получаем текстовое значение внутри области тега
    }

    // Установка значения для указанного тега (node)
    public static void setValueNode(Document document, String nodeName, String value) throws XPathExpressionException {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        XPathExpression expr = xpath.compile("//" + nodeName); // Находим xpathВыражение с указанным нами тегом
        NodeList nl = (NodeList) expr.evaluate(document, XPathConstants.NODESET); // Переводим xpathВыражение с указанным нами тегом в список элементов с таким тегом
        nl.item(0).setTextContent(value.trim()); // Изменяем текстовое значение внутри области тега
    }

    // Функция для сохранения DOM в файл
    private static void writeDocument(Document document) throws TransformerFactoryConfigurationError {
        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            FileOutputStream fos = new FileOutputStream(getUserTableDirectory() + "Settings.xml"); // указание имени и дериктории нового файла xml (стараемся перезаписать)
            StreamResult result = new StreamResult(fos);
            tr.transform(source, result);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(SettingsTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException | TransformerException ex) {
            Logger.getLogger(SettingsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Получаем директорию Пользователя (User/nameUser)
    public static String getUserDataDirectory() {
        return System.getProperty("user.home") + File.separator;
    }

    // Получаем директорию Рабочего стола Пользователя
    public static String getUserTableDirectory() {
        return System.getProperty("user.home") + File.separator + "Desktop" + File.separator;
    }

}
