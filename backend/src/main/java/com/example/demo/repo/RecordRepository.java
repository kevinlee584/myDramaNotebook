package com.example.demo.repo;

import com.example.demo.model.Drama;
import org.springframework.stereotype.Repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Repository
public class RecordRepository {
    private List<Drama> record = new ArrayList<>();
    private String fileName = "dramasRecord.dat";

    public RecordRepository() {

        if (Objects.nonNull(System.getenv("user.home"))) {
            fileName = String.format("%s\\%s", System.getenv("user.home"), fileName);
        }

        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileName))){
            record = (List<Drama>) input.readObject();
        }catch (Exception ignore){}
    }

    public List<Drama> getRecord() {
        return Collections.unmodifiableList(record);
    }

    public void addDrama(Drama drama) {
        record.add(drama);
        synchronizeRecord();
    }

    public void removeDrama(String providerName, String dramaName) {
        for (int i=0; i<record.size(); ++i) {
            if (record.get(i).getName().equals(dramaName) && record.get(i).getProviderName().equals(providerName)){
                record.remove(i);
                synchronizeRecord();
                return;
            }
        }
    }

    private synchronized void synchronizeRecord() {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("dramasRecord.dat"))){
            output.writeObject(record);
        }catch (Exception ignored){}
    }
}
