package spike;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class Demoing {

    public static void main(String argv[]) {

        try {
            File fXmlFile = new File("src/spike/demo.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList server = doc.getElementsByTagName("server");
            NodeList dynamicProxyPass = doc.getElementsByTagName("dynamic-proxy-pass");


            for (int temp = 0; temp < server.getLength(); temp++) {
                Node nNode = server.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("listen : " + eElement.getElementsByTagName("listen").item(0).getTextContent());
                    System.out.println("root : " + eElement.getElementsByTagName("root").item(0).getTextContent());
                    System.out.println("static-file-pattern : " + eElement.getElementsByTagName("static-file-pattern").item(0).getTextContent());
                }
            }

            for (int temp = 0; temp < dynamicProxyPass.getLength(); temp++) {
                Node dynamicProxy = dynamicProxyPass.item(temp);
                System.out.println("\nCurrent Element :" + dynamicProxy.getNodeName());
                if (dynamicProxy.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) dynamicProxy;
                    System.out.println("url-pattern : " + eElement.getElementsByTagName("url-pattern").item(0).getTextContent());
                    System.out.println("upstream-url " + eElement.getElementsByTagName("upstream-url").item(0).getTextContent());
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
