import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.*;


public class Ex2MyTests {
    public static final double TOLERANCE = 0.00001;

    @Test
    public void invoiceTest1(){


        // INVOICE DATA
        String[] accountNumbers = {"1234", "2435", "36378", "8365", "098373", "4563", "2653"};
        String[] sortCodes = {"11-32-33", "11-72-33", "11-15-33", "11-88-93", "11-17-73", "11-87-33", "11-22-98"};
        double[] amounts = {100.50, 120.67, 150.2, 50.0, 1000.0, 2450.89, 464.33};

        int accountNLens = accountNumbers.length;

            ArrayList<Measurable> invoiceArray = new ArrayList<>(accountNLens);

            for (int i = 0; i < accountNLens; i++) {
                invoiceArray.add(new Invoice(accountNumbers[i], sortCodes[i], amounts[i]));
            }


            double actualInvoiceMax = Statistics.maximum(invoiceArray);
            double actualInvoiceAvg = Statistics.average(invoiceArray);
            double actualInvoiceSD = Statistics.standardDeviation(invoiceArray);

            double expectedMax = 2450.89;
            double expectedAvg = 619.51285714286;
            double expectedSD = 874.22464873575;

            assertEquals(expectedMax, actualInvoiceMax, TOLERANCE);
            assertEquals(expectedAvg, actualInvoiceAvg, TOLERANCE);
            assertEquals(expectedSD, actualInvoiceSD, TOLERANCE);



    }

    @Test
    public void patientTest1(){

        // PATIENT DATA

        String[] name = {"brad", "john", "ron", "lisa", "jane", "bella", "eddie"};
        int[] age = {12, 33, 45, 32, 22, 66, 51};
        double[] weight = {65.345, 78.12, 95.999999999, 55.0, 43.2300, 0.0, 73.7};

        int nameLen = name.length;

        ArrayList<Measurable> patientArray = new ArrayList<>(nameLen);

        for (int i = 0; i < nameLen; i++){

            patientArray.add(new Patient(name[i], age[i], weight[i]));

        }

        double actualPatientMax = Statistics.maximum(patientArray);
        double actualPatientAvg = Statistics.average(patientArray);
        double actualPatientSD = Statistics.standardDeviation(patientArray);

        double expectedPatientMax = 95.999999999;
        double expectedPatientAvg = 58.770714285571;
        double expectedPatientSD = 30.919918934117;

        assertEquals(expectedPatientMax, actualPatientMax, TOLERANCE);
        assertEquals(expectedPatientAvg, actualPatientAvg, TOLERANCE);
        assertEquals(expectedPatientSD, actualPatientSD, TOLERANCE);


    }

    @Test(expected = IllegalArgumentException.class)
    public void zeroValuesStatsTest(){

        String[] name = {"brad", "john", "ron", "lisa", "jane", "bella", "eddie"};
        int[] age = {12, 33, 45, 32, 22, 66, 51};
        double[] weight = {65.345, 78.12, 95.999999999, 55.0, 43.2300, 0.0, 73.7};

        int nameLen = name.length;

        ArrayList<Measurable> patientArray = new ArrayList<>(nameLen);

        for (int i = 0; i < nameLen; i++){

            patientArray.add(new Patient(name[i], age[i], weight[i]));

        }

        patientArray.clear();

        double actualMax = Statistics.maximum(patientArray);
        double actualAvg = Statistics.average(patientArray);
        double actualSD = Statistics.standardDeviation(patientArray);

    }

    @Test(expected = ArithmeticException.class)
    public void nanValuesStatsTest(){


        // INVOICE DATA
        String[] accountNumbers = {"1234", "2435", "36378", "8365", "098373", "4563", "2653"};
        String[] sortCodes = {"11-32-33", "11-72-33", "11-15-33", "11-88-93", "11-17-73", "11-87-33", "11-22-98"};
        double[] amounts = {Math.sqrt(-1), 120.67, 150.2, 50.0, 1000.0, Math.sqrt(-3.5), 464.33};

        int accountNLens = accountNumbers.length;

        ArrayList<Measurable> invoiceArray = new ArrayList<>(accountNLens);

        for (int i = 0; i < accountNLens; i++) {
            invoiceArray.add(new Invoice(accountNumbers[i], sortCodes[i], amounts[i]));
        }


        double actualInvoiceMax = Statistics.maximum(invoiceArray);
        double actualInvoiceAvg = Statistics.average(invoiceArray);
        double actualInvoiceSD = Statistics.standardDeviation(invoiceArray);

    }

    @Test(expected = ArithmeticException.class)
    public void infinitiesValuesStatsTest(){

        String[] name = {"brad", "john", "ron", "lisa", "jane", "bella", "eddie"};
        int[] age = {12, 33, 45, 32, 22, 66, 51};
        double[] weight = {65.345, 78.12, Double.POSITIVE_INFINITY, 55.0, 43.2300, 0.0, Double.NEGATIVE_INFINITY};

        int nameLen = name.length;

        ArrayList<Measurable> patientArray = new ArrayList<>(nameLen);

        for (int i = 0; i < nameLen; i++){

            patientArray.add(new Patient(name[i], age[i], weight[i]));

        }

        double actualMax = Statistics.maximum(patientArray);
        double actualAvg = Statistics.average(patientArray);
        double actualSD = Statistics.standardDeviation(patientArray);

    }

    // ADD NEGATIVE NUMBERS


}
