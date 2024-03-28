import org.example.domain.Nota;
import org.example.domain.Student;
import org.example.domain.Tema;
import org.example.repository.*;
import org.example.service.Service;
import org.example.validation.*;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class ClassTest {

    public static Service service;

    public static void createXML() {
        File xml = new File("studentiTest.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<Entitati>\n<student ID=\"1\">\n" +
                    "        <Nume>Ana</Nume>\n" +
                    "        <Grupa>221</Grupa>\n" +
                    "    </student>\n</Entitati>");
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeAll
    public static void setup() {
        createXML();

        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studentiTest.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

        ClassTest.service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @Test
    public void testAddStudent() {
        assert(ClassTest.service.saveStudent("70", "Ion", 934) == 0);
    }

    @Test
    public void testAddStudentFail() {
        assert(ClassTest.service.saveStudent("6", "John", 1000) == 1);
    }
    
    @AfterAll
    public static void removeXML() {
        new File("studentiTest.xml").delete();
    }
}
