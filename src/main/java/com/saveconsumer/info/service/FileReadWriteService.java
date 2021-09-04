package com.saveconsumer.info.service;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.saveconsumer.info.domain.Consumer;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileWriter;
import java.util.Collections;
import java.util.List;

@Service
public class FileReadWriteService {

    public void writeDataToXmlFile(Consumer consumer) {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Consumer.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            File file = new File("consumer.xml");
            jaxbMarshaller.marshal(consumer, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void writeDataToCsvFile(Consumer consumer) {
        final String CSV_LOCATION = "Consumer.csv ";

        try {
            FileWriter writer = new
                    FileWriter(CSV_LOCATION);
            ColumnPositionMappingStrategy mappingStrategy =
                    new ColumnPositionMappingStrategy();
            mappingStrategy.setType(Consumer.class);

            String[] columns = new String[]
                    {"id", "name", "dob", "salary", "age"};
            mappingStrategy.setColumnMapping(columns);

            StatefulBeanToCsvBuilder<Consumer> builder =
                    new StatefulBeanToCsvBuilder(writer);
            StatefulBeanToCsv beanWriter =
                    builder.withMappingStrategy(mappingStrategy).build();

            beanWriter.write(consumer);

            writer.close();
        } catch (Exception e) {
            // TODO: Add Logger
        }
    }

    public List<Consumer> readDataFromFile() {
        File xmlFile = new File("consumer.xml");
        JAXBContext jaxbContext;
        List<Consumer> consumers = Collections.emptyList();
        try {
            jaxbContext = JAXBContext.newInstance(Consumer.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            consumers = (List<Consumer>) jaxbUnmarshaller.unmarshal(xmlFile);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return consumers;
    }
}
