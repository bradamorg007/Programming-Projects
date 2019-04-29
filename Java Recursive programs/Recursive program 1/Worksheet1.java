/**
 * @author <B r A D L e Y m O r G A n> This class contains the solution for Worksheet1
 */

public class Worksheet1 {


    public static void main(String[] args){

    }

    /**
     *
     * @param m Is the base value that the exponent will be applied to.
     * @param n The exponent that the base will be raised to the power by.
     * @return returns m to the power of n as an int
     */

    static int power(int m, int n) throws IllegalArgumentException{
    // O(n)
        if (n < 0 || m < 0){
            throw new IllegalArgumentException("Input parameters n and m must be non-negative");
        }

        if (n == 0) {
            return 1; // replace this by your code
        }else{
            return quickPowCheck(m * power(m, n-1));
        }

    }

    /**
     * Use integer division to deal with odd exponents, as int division always rounds down.
     * This way fast power doesn't need an additional recursive call :D :D
     * @param m Is the base value that the exponent will be applied to.
     * @param n The exponent that the base will be raised to the power by.
     * @return returns m to the power of n as an int
     */
    static int fastPower(int m, int n) throws IllegalArgumentException{

        if (n < 0 || m < 0){
            throw new IllegalArgumentException("Input parameters n and m must be non-negative");
        }

        // O (log n)
        if (n == 0){
            return 1;
        }

        int nSubDivision = fastPower(m, n/2);
        if (n % 2 == 0){

            return quickPowCheck(nSubDivision * nSubDivision);

        }else { // odd
            return quickPowCheck(m * nSubDivision * nSubDivision);
        }

    }

    static int quickPowCheck(int input){
        if (input < 0) {
            throw new IllegalArgumentException("The input parameters creates a result that exceeds the memory capacity of int.");
        }else {
            return input;
        }

    }

    // Decided to include a check if initial input is empty statement in Most functions.
    // Throwing an exception up to any calling function
    // allows such function to decide for itself how it wants to handle empty input with its own catch statements,
    // it can choose to stop program execution
    // ignore the Error, create some placeholder list or maybe fill in the empty list with correct values from a fail safe
    // function. handling the empty List input within the Worksheet1 methods may lead to a decrease in how generalisability
    // as all calling functions need to be aware of how it handles exceptional data, which might be tricky if people
    // havent looked at these functions in a while. So it seemed a better option to the throw exceptions back to
    // the calling function and allow them to decide how to handle it relative to their own use cases.

    /**
     * This function converts non-negative elements to positive and vice versa.
     * @param a List object containing a set of negative, 0 or positive elements.
     * @return A new negated list.
     */
    static List<Integer> negateAll(List<Integer> a) throws IllegalArgumentException {

        checkEmpty(a);

        if (a.getTail().isEmpty()){
            return new List<Integer>(a.getHead()*-1, new List<>());
        }else {
            //list.head = list.getHead() * -1;
           return new List<Integer>(a.getHead()*-1, negateAll(a.getTail()));
        }
    }

    // Exercise 3

    /**
     *
     * @param x the element to find within List a.
     * @param a List object containing a series of sorted or unsorted Integer elements.
     * @return if x is found the function returns its index within the list.
     */
    static int find(int x, List<Integer> a) throws IllegalArgumentException {

        checkEmpty(a);
        if (a.getHead() == x){
                return 0;
        }else if(a.getTail().isEmpty()){
            throw new IllegalArgumentException("The Element Could Not Be found");
        } else {

            return 1 + find(x, a.getTail());
        }
    }

    // Exercise 4

    /**
     * the function verifies if the input List contains all positive elements. 0 is undefined and can be considered
     * relative to ones use case as a positive, negative, neither at all or both value states simultaneously. Therefore the function
     * returns an arthimaticException to allow methods further up the stack to handle this issue as seen fit relative to the
     * other methods use case of allPositive.

     * @param a List object containing a series of sorted or unsorted, negative or positive Integer elements.
     * @return true if all elements are positive and false otherwise.
     */
    static boolean allPositive(List<Integer> a) throws IllegalArgumentException, ArithmeticException{

        checkEmpty(a);
        if(!a.isEmpty() && a.getTail().isEmpty()){
            return a.getHead() > 0;

        }else if(a.getHead() > 0) {
            return allPositive(a.getTail());
        }else if(a.getHead() == 0){
            throw new ArithmeticException("0 is undefined as either a positive or negative value: Please handle relative to the use case");
        }else {
            return false;
        }

    }

    // Exercise 5

    /**
     * Function removes all non-positive elements from a list.
     * @param a List object containing a series of sorted or unsorted Integer elements.
     * @return a new List object containing only the elements that were  positive  in the input list.
     */
    static List<Integer> positives(List<Integer> a) throws IllegalArgumentException {

        checkEmpty(a);
        if (a.getTail().isEmpty() && a.getHead() > 0){
            return new List<>(a.getHead(), new List<>());

        }else if(!a.getTail().isEmpty() && a.getHead() > 0){
            return new List<>(a.getHead(), positives(a.getTail()));

        } else if (a.getTail().isEmpty() && a.getHead() < 0){
            return new List<>();
        }else {
            return positives(a.getTail());
        }


    }

    // Exercise 6

    /**
     * The idea of sorted is a binary comparator between two elements and a boolean criterion
     * a binary comparator can not be defined for a single element list with respect to numerical value comparisons..
     * One could argue possibly that all elements by default hold a binary comparison of state,
     * between being non-existent(empty element) or not. With all other binary comparators undefined
     * The element defaults to the comparison of being empty or not. Nonetheless the question of
     * whether nothing precedes something is again undefined. Given all that I dont think
     * it really matters whether the function returns true or false, it seems more dependent
     * on the use case of the function.
     *
     * @param a List object containing a series of sorted or unsorted Integer elements.
     * @return a new sorted list of the elements within list a.
     */
    static boolean sorted(List<Integer> a) throws IllegalArgumentException {

        checkEmpty(a);

        if (a.getTail().isEmpty()){
            return true;
        }else if(a.getHead() > a.getTail().getHead()){
            return false;
        }else {
           return sorted(a.getTail());
        }
    }

    // Exercise 7

    // Merge function does not throw and Illegal argument exception for empty input list as merging two empty list should logically result
    // in one empty list being returned back. Like wise merging  one filled list  with an empty list should result
    // in only the filled list being returned back, as you have effectively merged nothing with it kind of like 1 + 0 = 1.

    /**
     * This function will create a new  sorted list object contaning elements from both input list a and b. Includes
     * Duplicates.
     * @param a List object containing a series of sorted or unsorted Integer elements.
     * @param b List object containing a series of sorted or unsorted Integer elements.
     * @return the new sorted merged list
     */
    static List<Integer> merge(List<Integer> a, List<Integer> b) {

        if (a.isEmpty()){
            return b;

        }else if(b.isEmpty()){
            return a;

        } else if(a.getHead() < b.getHead()){
            return new List<Integer>(a.getHead(), merge(a.getTail(), b));

        }else {
             //b.getHead is smaller than a.getHead
            return new List<Integer>(b.getHead(), merge(a, b.getTail()));
        }

        //return new List<Integer>(); // replace this by your code
    }

    // Exercise 8

    /**
     * function removes all duplicate elements from a list
     * @param a List object containing a series of sorted Integer elements.
     * @return copy of List a with all the duplicate values removed.
     */
    static List<Integer> removeDuplicates(List<Integer> a) throws IllegalArgumentException {

        checkEmpty(a);
        if (a.getTail().isEmpty()){
            return new List<Integer>(a.getHead(), new List<>());
        }else if(a.getHead().equals(a.getTail().getHead())){
            return removeDuplicates(a.getTail());
        }else{
            return new List<Integer>(a.getHead(), removeDuplicates(a.getTail()));
        }
    }

    static void checkEmpty(List<Integer> input){

            if (input.isEmpty()){
                throw new IllegalArgumentException("The Input Data is an empty List");
            }

    }

}
