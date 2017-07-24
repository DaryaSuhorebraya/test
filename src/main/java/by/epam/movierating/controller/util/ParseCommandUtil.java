package by.epam.movierating.controller.util;

import by.epam.movierating.command.Command;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides parsing of the commands.xml and instantiates its Map
 */
public class ParseCommandUtil {
    private static final Logger logger = Logger.getLogger(ParseCommandUtil.class);
    private static final String COMMANDS_XML_PATH = "/commands.xml";
    private static CommandHandler commandHandler = new CommandHandler();
    private static final String COMMANDS="commands";
    private static final String NAME="name";
    private static final String CLASS="class";
    private static final String COMMAND="command";

    /**
     * Returns {@link Map} of the parsed commands.
     * @return {@link Map} object
     */
    public Map<String, Command> parse() {

        InputSource inputSource = new InputSource(getClass().getResourceAsStream(COMMANDS_XML_PATH));
        try {
            XMLReader xmlReader = XMLReaderFactory.createXMLReader();
            xmlReader.setContentHandler(commandHandler);
            xmlReader.parse(inputSource);
        } catch (IOException | SAXException e) {
            logger.error(e);
        }
        return commandHandler.getCommandMap();
    }

    /**
     * Class for handling events while parsing commands.
     */
    private static class CommandHandler extends DefaultHandler {
        private StringBuilder text;
        private String name;
        private Command command;
        private Map<String, Command> commandMap;

        Map<String, Command> getCommandMap() {
            return commandMap;
        }


        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            text.append(ch, start, length);
        }

        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) throws SAXException {
            text = new StringBuilder();
            if (qName.equals(COMMANDS)) {
                commandMap = new HashMap<>();
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            switch (qName) {
                case NAME: {
                    name = text.toString();
                    break;
                }
                case CLASS: {
                    try {
                        command = (Command) Class.
                                forName(text.toString()).newInstance();
                    } catch (InstantiationException |
                            IllegalAccessException |
                            ClassNotFoundException e) {
                        logger.error(e);
                    }
                }
                case COMMAND: {
                    commandMap.put(name, command);
                }
            }
        }

        @Override
        public void startDocument() throws SAXException {
        }

        @Override
        public void endDocument() throws SAXException {

        }
    }
}

