package org.nightworld.UlearnProject.DTO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.nightworld.UlearnProject.DTO.models.SportObject;

public class CSVReader {

    public List<SportObject> parse(String url) throws IOException {
        List<SportObject> sportObjects = new ArrayList<>();
        try (var bufferedReader = new BufferedReader(new FileReader(url))) {
            var line = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                var sportObjectData = line.split(",\"");

                if (sportObjectData.length != 5) {
                    continue;
                }

                for (var i = 0; i < sportObjectData.length; i++) {
                    if (sportObjectData[i].charAt(sportObjectData[i].length() - 1) == '"') {
                        sportObjectData[i] =
                            sportObjectData[i].substring(0, sportObjectData[i].length() - 1);
                    }
                }

                var dateArray = Arrays.stream(sportObjectData[4].split("\\."))
                    .mapToInt(Integer::parseInt).toArray();

                var sportObject = new SportObject(
                    Integer.parseInt(sportObjectData[0]),
                    sportObjectData[1],
                    sportObjectData[2],
                    sportObjectData[3],
                    Date.valueOf(LocalDate.of(
                        dateArray[2],
                        dateArray[1],
                        dateArray[0]
                    ))
                );
                sportObjects.add(sportObject);
            }
        }
        return sportObjects;
    }
}
