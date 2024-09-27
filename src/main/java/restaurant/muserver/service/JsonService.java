package restaurant.muserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;
import restaurant.muserver.pojo.InputJson;
import restaurant.muserver.pojo.QualifierDate;

/**
 *
 * @author Jorge J Arcila Santiago
 */
public class JsonService {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(JsonService.class);

    private List<InputJson> jsonList;
    private List<QualifierDate> dates;

    public List<InputJson> getJsonByDate(String qualifierDate) {
        this.createQualifierDatesList(qualifierDate);

        Comparator<InputJson> comparator = (input1, input2) -> {
            return input2.getMaturityDate().compareTo(input1.getMaturityDate());
        };

        List<InputJson> result = jsonList.stream().filter(input -> this.dates.stream().
                filter(qd -> validateDates(qd, input)).findAny().isPresent()).collect(Collectors.toList());
        Collections.sort(result, comparator);
        return result;
    }

    private boolean validateDates(QualifierDate qd, InputJson input) {
        Date inputDate = input.getMaturityDate();
        Date fromDate = qd.getFromDate();
        Date toDate = qd.getToDate();

        return (inputDate.compareTo(fromDate) > 0 || fromDate.compareTo(inputDate) == 0)
                && (inputDate.compareTo(toDate) < 0 || fromDate.compareTo(inputDate) == 0);
    }

    public static void main(String[] args) {
        String qualifier = "{'2023-01-01', '2020-12-31', '2028-01-01', '2025-12-31'}";
        JsonService jsonService = new JsonService();
        jsonService.processStringToJson(getJsonStr());
        jsonService.getJsonByDate(qualifier).forEach(input -> {
            System.out.println(input.getMaturityDate());
        });
    }

    private void createQualifierDatesList(String qualifierDateStr) {
        dates = new ArrayList<>();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        int dateLength = 11;
        String subString = qualifierDateStr.replace(" ", "").replace("{", "").replace(",", "");
        QualifierDate qualifierDate;
        String dateStr = "";

        try {
            while (subString.indexOf('\'') >= 0) {
                dateStr = subString.substring(1, dateLength);
                subString = subString.substring(dateLength + 1);
                if (!dates.isEmpty()) {
                    qualifierDate = (null != getLastDate().getToDate() && null != getLastDate().getFromDate()) ? new QualifierDate() : getLastDate();
                } else {
                    qualifierDate = new QualifierDate();
                }
                if (null == qualifierDate.getToDate()) {
                    qualifierDate.setToDate(formatter.parse(dateStr));
                    dates.add(qualifierDate);
                } else if (null == qualifierDate.getFromDate()) {
                    qualifierDate.setFromDate(formatter.parse(dateStr));
                }
            }
        } catch (ParseException ex) {
            log.error("Error parsing date: " + dateStr);
        }
    }

    private QualifierDate getLastDate() {
        return dates.stream().reduce((d1, d2) -> d2).get();
    }

    public void processStringToJson(String jsonStr) {
        try {
            //Object to mapper, test
            ObjectMapper mapper = new ObjectMapper();
            jsonList = mapper.readValue(jsonStr, new TypeReference<List<InputJson>>() {
            });
            System.out.println("");
        } catch (JsonProcessingException ex) {
            Logger.getLogger(JsonService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String getJsonStr() {
        File myObj = new File("src\\main\\resources\\jsonInputFile.txt");
        Scanner myReader;
        StringBuilder jsonStr = new StringBuilder();
        try {
            myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                jsonStr.append(data);
            }
            myReader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JsonService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonStr.toString();
    }
}
