package dev.citralflo.xmldataloader;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Service
public class XmlService {

    private final Set<String> externalDataPath = new HashSet<>();
    private final Set<String> internalDataPath = new HashSet<>();

    // Adding existing data files

    XmlService() {
        externalDataPath.add("src/main/java/dev/citralflo/data/external/person1.xml");
        externalDataPath.add("src/main/java/dev/citralflo/data/external/person2.xml");
        externalDataPath.add("src/main/java/dev/citralflo/data/external/person3.xml");
        internalDataPath.add("src/main/java/dev/citralflo/data/internal/person1.xml");
        internalDataPath.add("src/main/java/dev/citralflo/data/internal/person2.xml");
        internalDataPath.add("src/main/java/dev/citralflo/data/internal/person3.xml");
    }

    public Person findPersonBySurname(String surname) throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Person person = new Person();

        externalDataPath.forEach(path -> {
            try {
                Document data = db.parse(new File(path));
                data.normalizeDocument();

                Node personNode = data.getElementsByTagName("person").item(0);

                NodeList personData = personNode.getChildNodes();


                for (int i = 0; i < personData.getLength(); i++) {

                    Node detail = personData.item(i);
                    if (detail.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) detail;

                        if (element.getTagName().equals("surname")
                            && element.getAttribute("value").equals(surname)) {
                            for (int j = 0; j < personData.getLength(); j++) {
                                Node detail2 = personData.item(j);
                                if (detail2.getNodeType() == Node.ELEMENT_NODE) {
                                    Element element2 = (Element) detail2;
                                    switch (element2.getTagName()) {
                                        case "name":
                                            person.setFirstName(element2.getAttribute("value"));
                                            break;
                                        case "surname":
                                            person.setLastName(element2.getAttribute("value"));
                                            break;
                                        case "email":
                                            person.setEmail(element2.getAttribute("value"));
                                            break;
                                        case "phone":
                                            person.setPhoneNumber(element2.getAttribute("value"));
                                            break;
                                        case "pesel":
                                            person.setPesel(element2.getAttribute("value"));
                                            break;
                                        default:

                                            person.setType(ContractType.EXTERNAL);
                                    }
                                }
                            }

                        }

                    }
                }
            } catch (SAXException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return person;
    }

    void createPerson(Person person) throws ParserConfigurationException, TransformerException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document data = db.newDocument();
        Element root = data.createElement("person");
        data.appendChild(root);

        Element name = data.createElement("name");
        name.setAttribute("value", person.getFirstName());

        root.appendChild(name);

        Element surname = data.createElement("surname");
        surname.setAttribute("value", person.getLastName());

        root.appendChild(surname);

        Element email = data.createElement("email");
        email.setAttribute("value", person.getEmail());

        root.appendChild(email);

        Element phone = data.createElement("phone");
        phone.setAttribute("value", person.getPhoneNumber());

        root.appendChild(phone);

        Element pesel = data.createElement("pesel");
        pesel.setAttribute("value", person.getPesel());

        root.appendChild(pesel);

        data.getDocumentElement().normalize();

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        DOMSource source = new DOMSource(data);
        StreamResult result = null;

        if (person.getType().equals(ContractType.EXTERNAL)) {
            String fileNumber = this.externalDataPath.size() + 1 + "";
            result = new StreamResult(new File("src/main/java/dev/citralflo/data/external/person" + fileNumber + ".xml"));
            this.externalDataPath.add("src/main/java/dev/citralflo/data/external/person" + fileNumber + ".xml");
        }   else {
            String fileNumber = this.internalDataPath.size() + 1 + "";
            result = new StreamResult(new File("src/main/java/dev/citralflo/data/internal/person" + fileNumber + ".xml"));
            this.internalDataPath.add("src/main/java/dev/citralflo/data/internal/person" + fileNumber + ".xml");
        }

        transformer.transform(source, result);


    }

    boolean deletePersonByPesel(String pesel) {


        String filePath = "";


        for (String path : externalDataPath) {
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document data = db.parse(new File(path));
                data.normalizeDocument();

                Node personNode = data.getElementsByTagName("person").item(0);

                NodeList personData = personNode.getChildNodes();

                for (int i = 0; i < personData.getLength(); i++) {
                    Node detail = personData.item(i);
                    if (detail.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) detail;
                        if (element.getTagName().equals("pesel")
                            && element.getAttribute("value").equals(pesel)) {
                            filePath = path;
                            break;
                        }
                    }
                }
            } catch (SAXException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
        // Create a File object representing the XML file
        File file = new File(filePath);

        // Check if the file exists
        if (file.exists()) {
            // Attempt to delete the file
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

}
