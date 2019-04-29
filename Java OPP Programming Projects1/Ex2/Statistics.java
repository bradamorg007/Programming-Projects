import java.util.ArrayList;

/**
 * This class provides three static methods that calculate statics using the cumulative getvalue results from
 * each object of type Measurable stored within an Array list of type measurable.
 *
 **/
public class Statistics {

    /**
     * Calculate the maximum value of an array list of objects of type Measurable.
     * @param elements arrayList of Measurable objects - Invoice or patient
     * @return the maximum value getValue of the list Measurable objects
     */
    public static double maximum(ArrayList<Measurable> elements){

        double max = 0;
        if (elements.size() != 0) {

            for (Measurable item : elements) {

                if(!Double.isNaN(item.getValue()) && !Double.isInfinite(item.getValue())) {
                    if (item.getValue() > max) {
                        max = item.getValue();
                    }
                }else{
                    throw new ArithmeticException();
                }
            }
        }else {
            throw new IllegalArgumentException();
        }

        return max;
    }

    /**
     *  Calculate the average value of an array list of objects of type Measurable.
     * @param elements arrayList of Measurable objects - Invoice or patient
     * @return the average value getValue of the list Measurable objects
     */
    public static double average(ArrayList<Measurable> elements){

        double sum = 0;
        double numElements = (double) elements.size();

        if (numElements != 0) {

            for (Measurable item : elements) {
                if(!Double.isNaN(item.getValue()) && !Double.isInfinite(item.getValue())) {
                    sum += item.getValue();
                }else{
                    throw new ArithmeticException();
                }
            }
        }else{
            throw new IllegalArgumentException();
        }

        return sum/numElements;

    }

    /**
     *  Calculate the standard deviation  value of an array list of objects of type Measurable.
     * @param elements arrayList of Measurable objects - Invoice or patient
     * @return the standard deviation value getValue of the list Measurable objects
     */

    public static double standardDeviation(ArrayList<Measurable> elements){

        double avg = average(elements);
        double arrayLen = (double) elements.size();
        double sum = 0;

        if (arrayLen != 0) {

            for (Measurable item : elements) {
                if(!Double.isNaN(item.getValue()) && !Double.isInfinite(item.getValue())) {
                    sum += Math.pow(item.getValue() - avg, 2.0);
                }else {
                    throw new ArithmeticException();
                }
            }

        }else {
            throw  new IllegalArgumentException();
        }

        return Math.sqrt(sum / (arrayLen - 1));

    }
}
