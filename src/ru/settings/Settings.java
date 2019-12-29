package ru.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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

public class Settings {

    private File newDirectory = new File(getUserDataDirectory() + "TrafficClicker");
    private Document document;
    private InputStream defaultSettings = Settings.class.getResourceAsStream("/resources/propertiesFile/Settings.xml");

    // Чтение значения из указанного тега (node)
    public String getValueNode(String nodeName) throws XPathExpressionException, SAXException, IOException, ParserConfigurationException {
        // Создается построитель документа
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        // Создается дерево DOM документа из файла
        try {
            document = documentBuilder.parse(getNewUserDirectoryToUri() + "Settings.xml");
        } catch (FileNotFoundException e) {
            defaultSettings = Settings.class.getResourceAsStream("/resources/propertiesFile/Settings.xml"); // Открываем поток для предотвращения его закрытия
            document = documentBuilder.parse(defaultSettings);
        }

        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        XPathExpression expr = xpath.compile("//" + nodeName); // Находим xpathВыражение с указанным нами тегом
        NodeList nl = (NodeList) expr.evaluate(document, XPathConstants.NODESET); // Переводим xpathВыражение с указанным нами тегом в список элементов с таким тегом
        if (nl.item(0) != null) {
            return nl.item(0).getTextContent().trim(); // Получаем текстовое значение внутри области тега
        } else {
            return ""; // Если указанного node не существует, то возвращаем пустую строку
        }
    }

    // Установка значения для указанного тега (node)
    public void setValueNode(String nodeName, String value) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
        // Создается построитель документа
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        // Создается дерево DOM документа из файла
        try {
            document = documentBuilder.parse(getNewUserDirectoryToUri() + "Settings.xml");
        } //        catch (MalformedURLException malfURL) {
        //            newDirectory = new File(getUserDataDirectoryWithPrefix() + "TrafficClicker");
        //            document = documentBuilder.parse(getNewUserDirectoryToUri() + "Settings.xml");
        //        } 
        catch (FileNotFoundException e) {
            defaultSettings = Settings.class.getResourceAsStream("/resources/propertiesFile/Settings.xml"); // Открываем поток для предотвращения его закрытия
            document = documentBuilder.parse(defaultSettings);
        }

        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        XPathExpression expr = xpath.compile("//" + nodeName); // Находим xpathВыражение с указанным нами тегом
        NodeList nl = (NodeList) expr.evaluate(document, XPathConstants.NODESET); // Переводим xpathВыражение с указанным нами тегом в список элементов с таким тегом
        nl.item(0).setTextContent(value.trim()); // Изменяем текстовое значение внутри области тега
    }

    // Функция для сохранения DOM в файл
    public void writeDocument() throws TransformerFactoryConfigurationError, SAXException, IOException, ParserConfigurationException {
        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            try ( FileOutputStream fos = new FileOutputStream(getNewUserDirectory() + "Settings.xml")) // указание имени и дериктории нового файла xml (стараемся перезаписать)
            {
                StreamResult result = new StreamResult(fos);
                tr.transform(source, result);
            }
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException | TransformerException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeFirstDocument() throws TransformerFactoryConfigurationError, SAXException, IOException, ParserConfigurationException {
        try {
            // Создается построитель документа
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // Создается дерево DOM документа из файла
            try {
                document = documentBuilder.parse(getNewUserDirectoryToUri() + "Settings.xml");
            } catch (FileNotFoundException e) {
                defaultSettings = Settings.class.getResourceAsStream("/resources/propertiesFile/Settings.xml"); // Открываем поток для предотвращения его закрытия
                document = documentBuilder.parse(defaultSettings);
            }
            // Запись файла
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            try (FileOutputStream fos = new FileOutputStream(getNewUserDirectory() + "Settings.xml")) // указание имени и дериктории нового файла xml (стараемся перезаписать)
            {
                StreamResult result = new StreamResult(fos);
                tr.transform(source, result);
            }
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException | TransformerException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Получаем директорию Пользователя (User/nameUser)
    public String getUserDataDirectory() {
        return System.getProperty("user.home") + File.separator;
    }

//    public String getUserDataDirectoryWithPrefix() throws MalformedURLException {
//        return System.getProperty("user.home") + File.separator;
//    }
    // Получаем директорию Рабочего стола Пользователя
    public String getUserTableDirectory() {
        return System.getProperty("user.home") + File.separator + "Desktop" + File.separator;
    }

    // Создаем папку в папке пользователя (каждый раз)
    public String getNewUserDirectoryToUri() {
        newDirectory.mkdir(); // Создаем директорию F
        return (newDirectory.toURI() + File.separator); // Возвращаем новую папку со слешом "/"
    }

    public String getNewUserDirectory() {
        return (newDirectory + File.separator); // Возвращаем новую папку со слешом "/"
    }
}
