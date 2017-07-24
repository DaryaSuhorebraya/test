package by.epam.movierating.controller.util;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parses role.xml and instantiates its Map
 */
public class ParseRoleUtil {
    private static final Logger logger = Logger.getLogger(ParseCommandUtil.class);
    private static final String ROLES_XML_PATH = "/roles.xml";
    private static final String USER="user";
    private static final String GUEST="guest";
    private static final String COMMAND="command";

    /**
     * Returns {@link Map} of the parsed roles
     * @return {@link Map} object
     */
    public  Map<String,List<String>> parse() {

        InputSource inputSource = new InputSource(getClass().getResourceAsStream(ROLES_XML_PATH));
        DOMParser domParser = new DOMParser();
        try {
            domParser.parse(inputSource);
        } catch (SAXException | IOException e) {
            logger.error(e);
        }
        Document document = domParser.getDocument();
        Element root = document.getDocumentElement();
        List<String> userRoles = getUserRoles(root);
        List<String> guestRoles = getGuestRoles(root);
        Map<String,List<String>> roleList = new HashMap<>();
        roleList.put("user",userRoles);
        roleList.put("guest",guestRoles);
        return roleList;
    }

    /**
     * Puts users' roles to {@link List}
     * @param root root of the document
     * @return {@link List} object with users' roles
     */
    private List<String> getUserRoles(Element root) {
        List<String> userRoles = new ArrayList<>();
        NodeList nodeList = root.getElementsByTagName(USER);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element nodeListElement = (Element) nodeList.item(i);

            List<Element> userRoleElements = getChildren(nodeListElement, COMMAND);
            for (Element element : userRoleElements) {
                userRoles.add(element.getTextContent().trim());
            }
        }
        return userRoles;
    }

    /**
     * Puts guests' roles to {@link List}
     * @param root root of the document
     * @return {@link List} object with guests' roles
     */
    private List<String> getGuestRoles(Element root) {
        List<String> guestRoles = new ArrayList<>();
        NodeList nodeList = root.getElementsByTagName(GUEST);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element nodeListElement = (Element) nodeList.item(i);

            List<Element> guestRoleElements = getChildren(nodeListElement, COMMAND);
            for (Element element : guestRoleElements) {
                guestRoles.add(element.getTextContent().trim());
            }
        }
        return guestRoles;
    }

    /**
     * Returns all children for current element with determined children's name
     * @param element element for retrieving the children
     * @param childName name of the children
     * @return {@link List<Element>} object
     */
    private List<Element> getChildren(Element element, String childName) {
        NodeList nodeList = element.getElementsByTagName(childName);
        List<Element> elements = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element child = (Element) nodeList.item(i);
            elements.add(child);
        }
        return elements;
    }

}

