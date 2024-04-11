package dev.citralflo.xmldataloader;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xml.sax.SAXException;

@SpringBootApplication
public class XmlDataLoaderApplication {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        SpringApplication.run(XmlDataLoaderApplication.class, args);

        XmlService xmlService = new XmlService();

        Person personBySurname = xmlService.findPersonBySurname("Doe");

        System.out.println(personBySurname.toString());

        xmlService.createPerson(new Person("Michal", "Wojtas", "726802258", "wojtas.michal90@gmail.com", "90010112345", ContractType.INTERNAL));

        /*
        if (xmlService.deletePersonByPesel("97102655959")) {
            System.out.println("Person deleted");
        } else {
            System.out.println("Person not found");
        }
         */
    }

}
