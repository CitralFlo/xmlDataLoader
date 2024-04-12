package dev.citralflo.xmldataloader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class XmlDataLoaderApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testPerson() {
        Person person = new Person("Michal", "Wojtas", "wojtas.michal90@gmail.com", "19203798", "12345677701", ContractType.INTERNAL);

        assertEquals("Michal", person.getFirstName());
        assertEquals("Wojtas", person.getLastName());
        assertEquals("wojtas.michal90@gmail.com", person.getEmail());
        assertEquals("19203798", person.getPhoneNumber());
        assertEquals("12345677701", person.getPesel());
        assertEquals(ContractType.INTERNAL, person.getType());
    }

    @Test
    void findPersonBySurname() throws ParserConfigurationException {
        XmlService xmlService = new XmlService();
        Person person = xmlService.findPersonBySurname("Doe");

        assertEquals("John", person.getFirstName());
        assertEquals("Doe", person.getLastName());
        assertEquals("john.doe@gmail.com", person.getEmail());
        assertEquals("123456789", person.getPhoneNumber());
        assertEquals("97102655959", person.getPesel());
    }

    @Test
    void createPersonFile() throws ParserConfigurationException, TransformerException {
        XmlService xmlService = new XmlService();
        xmlService.createPerson(new Person("Michal", "Wojtas", "wojtas@gmail.com","726802258" , "90010112345", ContractType.INTERNAL));
        Person person = xmlService.findPersonBySurname("Wojtas");

        assertEquals("Michal", person.getFirstName());
        assertEquals("Wojtas", person.getLastName());
        assertEquals("wojtas@gmail.com", person.getEmail());
        assertEquals("726802258", person.getPhoneNumber());
        assertEquals("90010112345", person.getPesel());


    }
}
