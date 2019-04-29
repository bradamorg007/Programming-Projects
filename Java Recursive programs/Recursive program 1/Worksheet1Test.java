// ADD MORE TESTS MAYBE

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Worksheet1Test {


    @Before
    public void setUp() {

    }

// =========================== POWER ===============================================
    @Test
    public void powerTest1(){

        int expected1 = 1;
        int actual1 = Worksheet1.power(6, 0);

        assertEquals(expected1, actual1);
    }

    @Test
    public void powerTest2(){

        int expected1 = 1;
        int expected2 = 7;

        int actual1 = Worksheet1.power(1, 100);
        int actual2 = Worksheet1.power(7, 1);

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    public void powerTest3(){

        int expected1 = 1024;
        int expected2 = 125;
        int expected3 = 729000000;
        int expected4 = 387420489;


        int actual1 = Worksheet1.power(2, 10);
        int actual2 = Worksheet1.power(5, 3);
        int actual3 = Worksheet1.power(30, 6);
        int actual4 = Worksheet1.power(3, 18);

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
        assertEquals(expected3, actual3);
        assertEquals(expected4, actual4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void powerTest4(){
        Worksheet1.power(30, 8);
    }

    @Test(expected = IllegalArgumentException.class)
    public void powerTest5(){
        Worksheet1.power(30, -2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void powerTest6(){
        Worksheet1.power(-5, 3);
    }

    // =========================== FAST POWER ===============================================
    @Test
    public void fastPowerTest1(){

        int expected1 = 1;
        int actual1 = Worksheet1.fastPower(3, 0);

        assertEquals(expected1, actual1);
    }

    @Test
    public void fastPowerTest2(){

        int expected1 = 1;
        int expected2 = 3457;
        int actual1 = Worksheet1.fastPower(1, 100);
        int actual2 = Worksheet1.fastPower(3457, 1);

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    public void fastPowerTest3(){

        int expected1 = 32768;
        int expected2 = 625;
        int expected3 = 170859375;
        int expected4 =387420489;


        int actual1 = Worksheet1.fastPower(2, 15);
        int actual2 = Worksheet1.fastPower(5, 4);
        int actual3 = Worksheet1.fastPower(15, 7);
        int actual4 = Worksheet1.fastPower(3, 18);

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
        assertEquals(expected3, actual3);
        assertEquals(expected4, actual4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void fastPowerTest4(){
        Worksheet1.fastPower(30, 9);
    }

    @Test(expected = IllegalArgumentException.class)
    public void fastPowerTest5(){
        Worksheet1.fastPower(30, -2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void fastPowerTest6(){
        Worksheet1.fastPower(-5, 3);
    }

    @Test
    public void fastPower7(){
        int numOfRuns = 1000;

        long[] sumTimeElapsed = new long[2];

        for (int i = 0; i < numOfRuns; i++){
            long startTime1 = System.nanoTime();
            int pow1 = Worksheet1.power(2, 27);
            long endTime1 = System.nanoTime();
            sumTimeElapsed[0] += (endTime1 - startTime1);

            long startTime2 = System.nanoTime();
            int pow2 = Worksheet1.fastPower(2, 27);
            long endTime2 = System.nanoTime();
            sumTimeElapsed[1] += (endTime2 - startTime2);

        }

        long[] avgTimeElapsed = {(sumTimeElapsed[0]/numOfRuns), (sumTimeElapsed[1]/numOfRuns)};
        assertTrue(avgTimeElapsed[1] < avgTimeElapsed[0]);

    }

// =========================== NEGATE ALL ===============================================

    @Test
    public void negateTest1(){
        // standard check, to see if negation works

        List<Integer> listA = new List<>(2, new List<>(5, new List<>(5, new List<>(8, new List<>()))));
        List<Integer> listB = new List<>(-5, new List<>(7, new List<>(-8, new List<>(9, new List<>()))));
        List<Integer> listC = new List<>(6, new List<>());
        List<Integer> listD = new List<>(-2, new List<>(-5, new List<>(-5, new List<>(-5, new List<>(-7, new List<>(-8, new List<>(-8, new List<>(-9, new List<>()))))))));

        List<Integer> expectedA = new List<>(-2, new List<>(-5, new List<>(-5, new List<>(-8, new List<>()))));
        List<Integer> expectedB = new List<>(5, new List<>(-7, new List<>(8, new List<>(-9, new List<>()))));
        List<Integer> expectedC = new List<>(-6, new List<>());
        List<Integer> expectedD = new List<>(2, new List<>(5, new List<>(5, new List<>(5, new List<>(7, new List<>(8, new List<>(8, new List<>(9, new List<>()))))))));

        List<Integer> actual1 = Worksheet1.negateAll(listA);
        List<Integer> actual2 = Worksheet1.negateAll(listB);
        List<Integer> actual3 = Worksheet1.negateAll(listC);
        List<Integer> actual4 = Worksheet1.negateAll(listD);

        assertTrue(expectedA.equals(actual1));
        assertTrue(expectedB.equals(actual2));
        assertTrue(expectedC.equals(actual3));
        assertTrue(expectedD.equals(actual4));

    }

    @Test
    public void negateTest2(){
        // test the boundries

        List<Integer> listA = new List<>(-1, new List<>(1, new List<>(0, new List<>())));

        List<Integer> expectedList = new List<>(1, new List<>(-1, new List<>(0, new List<>())));

        List<Integer> actualList = Worksheet1.negateAll(listA);

        assertTrue(expectedList.equals(actualList));
    }

    @Test(expected = IllegalArgumentException.class)
    public void negateTest3(){
        // check for empty list
        List<Integer> empty = Worksheet1.negateAll(new List<>());
    }
    @Test
    public void negateTest4(){
        // test int overflow results with negateALL.
        List<Integer> listA = new List<>(2, new List<>(-5, new List<>(5, new List<>(1000000000 * 1000, new List<>()))));
        List<Integer> expected = new List<>(-2, new List<>(5, new List<>(-5, new List<>(727379968, new List<>()))));

        List<Integer> listB = new List<>(2, new List<>(-5, new List<>(5, new List<>(-1000000000 * 1000, new List<>()))));
        List<Integer> expected2 = new List<>(-2, new List<>(5, new List<>(-5, new List<>(-727379968, new List<>()))));

        List<Integer> actual = Worksheet1.negateAll(listA);
        List<Integer> actual2 = Worksheet1.negateAll(listB);

        assertTrue(expected.equals(actual));
        assertTrue(expected2.equals(actual2));
    }

    // =========================== FIND  ===============================================

    @Test
    public void findTest1(){

        // Check that function works with standard inputs
        List<Integer> listA = new List<>(7, new List<>(5, new List<>(3, new List<>(8, new List<>()))));
        List<Integer> listB = new List<>(8, new List<>());

        int x1 = 3;
        int x1_2 = 8;
        int x2 = 8;

        int expPos1 = 2;
        int expPos2 = 3;
        int expPos3 = 0;

        int pos1 = Worksheet1.find(x1, listA);
        int pos2 = Worksheet1.find(x1_2, listA);
        int pos3 = Worksheet1.find(x2, listB);

        assertEquals(expPos1, pos1);
        assertEquals(expPos2, pos2);
        assertEquals(expPos3, pos3);
    }

    @Test
    public void findTest2() {
        // check that find first occurance of duplicates
        List<Integer> listA = new List<>(7, new List<>(5, new List<>(3, new List<>(8, new List<>(6553, new List<>(6553, new List<>()))))));
        int x = 6553;
        int expPos = 4;
        int pos = Worksheet1.find(x, listA);

        assertEquals(expPos, pos);

    }

    @Test(expected = IllegalArgumentException.class)
    public void findTest3() {
        // check that find first occurance of duplicates
        List<Integer> listA = new List<>(7, new List<>(5, new List<>(3, new List<>(8, new List<>(6553, new List<>(6553, new List<>()))))));
        int x = 6554;
        int pos = Worksheet1.find(x, listA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findTest4() {
        // check that find first occurance of duplicates
        List<Integer> listA = new List<>(7, new List<>(5, new List<>(3, new List<>(8, new List<>(6553, new List<>(6553, new List<>()))))));
        int x = 6552;
        int pos = Worksheet1.find(x, listA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findTest5() {
        // check that find first occurance of duplicates
        int empty = Worksheet1.find(1, new List<>());
    }

    // =========================== ALL POSITIVE  ===============================================

    @Test
    public void allPosTest1(){
        // check if it works with standard inputs

        List<Integer> listA = new List<>(1, new List<>(3, new List<>(5, new List<>())));
        List<Integer> listB = new List<>(1, new List<>(56, new List<>(-17, new List<>())));

        assertTrue(Worksheet1.allPositive(listA));
        assertFalse(Worksheet1.allPositive(listB));

    }

    @Test
    public void allPosTest2(){
        // test values near the decision boundary. check boundary near 0 and also check numeric
        // over spill effects

        List<Integer> listA = new List<>(1, new List<>(-1, new List<>(5, new List<>( 17, new List<>()))));
        List<Integer> listB = new List<>(1, new List<>(56, new List<>(1, new List<>())));
        List<Integer> listD = new List<>(1, new List<>(5, new List<>(1, new List<>(1000000000 * 1000000, new List<>()))));
        List<Integer> listE = new List<>(1, new List<>(5, new List<>(1, new List<>(1000000000 * 999999, new List<>()))));
        List<Integer> listF = new List<>(1, new List<>(5, new List<>(1, new List<>(-1000000000 * 1000000, new List<>()))));
        List<Integer> listG = new List<>(1, new List<>(5, new List<>(1, new List<>(-1000000000 * 999999, new List<>()))));

        assertFalse(Worksheet1.allPositive(listA));
        assertFalse(Worksheet1.allPositive(listD));
        assertFalse(Worksheet1.allPositive(listG));

        // 0 returns false as the function validates the question whether all elements in
        // the list are positive. 0 is undefined and is neither negative nor positive definitively.

        assertTrue(Worksheet1.allPositive(listB));
        assertTrue(Worksheet1.allPositive(listE));
        assertTrue(Worksheet1.allPositive(listF));


    }

    @Test(expected = IllegalArgumentException.class)
    public void allPosTest3(){
        boolean empty = Worksheet1.allPositive(new List<>());
    }

    @Test(expected = ArithmeticException.class)
    public void  allPosTest4(){
        List<Integer> listC = new List<>(1, new List<>(0, new List<>(1, new List<>())));
        assertTrue(Worksheet1.allPositive(listC));
    }

    // =========================== FIND POSITIVE  ===============================================

    @Test
    public void findPosTest1(){
        // check if it works with standard inputs

        List<Integer> listA = new List<>(1, new List<>(3, new List<>(5, new List<>())));
        List<Integer> listB = new List<>(1, new List<>(56, new List<>(-17, new List<>())));

        List<Integer> ExpectedA = new List<>(1, new List<>(3, new List<>(5, new List<>())));
        List<Integer> ExpectedB = new List<>(1, new List<>(56, new List<>()));

        assertTrue(ExpectedA.equals(Worksheet1.positives(listA)));
        assertTrue(ExpectedB.equals(Worksheet1.positives(listB)));

    }

    @Test
    public void findPosTest2(){
        // test values near the decision boundary. check boundary near 0 and also check numeric
        // over spill effects

        // Check negative 1
        List<Integer> listA = new List<>(1, new List<>(-1, new List<>(5, new List<>( 17, new List<>()))));

        List<Integer> listB = new List<>(1, new List<>(56, new List<>(1, new List<>())));
        // Check 0
        List<Integer> listC = new List<>(1, new List<>(0, new List<>(1, new List<>())));

        List<Integer> listD = new List<>(1, new List<>(5, new List<>(1, new List<>(1000000000 * 1000000, new List<>()))));
        List<Integer> listE = new List<>(1, new List<>(5, new List<>(1, new List<>(1000000000 * 999999, new List<>()))));
        List<Integer> listF = new List<>(1, new List<>(5, new List<>(1, new List<>(-1000000000 * 1000000, new List<>()))));
        List<Integer> listG = new List<>(1, new List<>(5, new List<>(1, new List<>(-1000000000 * 999999, new List<>()))));

        List<Integer> expListA = new List<>(1, new List<>(5, new List<>( 17, new List<>())));
        List<Integer> expListB = new List<>(1, new List<>(56, new List<>(1, new List<>())));
        List<Integer> expListC = new List<>(1, new List<>(1, new List<>()));
        List<Integer> expListD = new List<>(1, new List<>(5, new List<>(1, new List<>())));
        List<Integer> expListE = new List<>(1, new List<>(5, new List<>(1, new List<>(1000000000 * 999999, new List<>()))));
        List<Integer> expListF = new List<>(1, new List<>(5, new List<>(1, new List<>(-1000000000 * 1000000, new List<>()))));
        List<Integer> expListG = new List<>(1, new List<>(5, new List<>(1, new List<>())));

        assertTrue(expListA.equals(Worksheet1.positives(listA)));
        assertTrue(expListB.equals(Worksheet1.positives(listB)));
        assertTrue(expListC.equals(Worksheet1.positives(listC)));
        assertTrue(expListD.equals(Worksheet1.positives(listD)));
        assertTrue(expListE.equals(Worksheet1.positives(listE)));
        assertTrue(expListF.equals(Worksheet1.positives(listF)));
        assertTrue(expListG.equals(Worksheet1.positives(listG)));

        // 0 returns false as the function validates the question whether all elements in
        // the list are positive. 0 is undefined and is neither negative nor positive definitively.


    }

    @Test
    public void findPosTest3(){
        // make sure duplicates are kept
        List<Integer> listA = new List<>(-1, new List<>(0, new List<>(5, new List<>( 5, new List<>(-23, new List<>(5, new List<>()))))));
        List<Integer> expList = new List<>(5, new List<>(5, new List<>(5, new List<>())));

        assertTrue(expList.equals(Worksheet1.positives(listA)));

    }

    @Test(expected = IllegalArgumentException.class)
    public void findPosTest4(){
        List<Integer> empty = Worksheet1.positives(new List<>());
    }

    // =========================== SORTED ===============================================


    @Test
    public void sortTest1(){

        List<Integer> listA = new List<>(1, new List<>(0, new List<>(5, new List<>())));
        List<Integer> listB = new List<>(1, new List<>(2, new List<>(0, new List<>())));
        List<Integer> listC = new List<>(1, new List<>(2, new List<>(5, new List<>())));
        List<Integer> listD = new List<>(9, new List<>());

        assertFalse(Worksheet1.sorted(listA));
        assertFalse(Worksheet1.sorted(listB));
        assertTrue(Worksheet1.sorted(listC));
        assertTrue(Worksheet1.sorted(listD));

    }

    @Test
    public void sortTest2(){

        List<Integer> listA = new List<>(1, new List<>(5, new List<>(100, new List<>(1000000000 * 1000000, new List<>()))));
        List<Integer> listB = new List<>(1, new List<>(3263, new List<>(236536, new List<>(1000000000 * 999999, new List<>()))));
        List<Integer> listC = new List<>(1, new List<>(53, new List<>(163, new List<>(-1000000000 * 1000000, new List<>()))));
        List<Integer> listD = new List<>(1, new List<>(5, new List<>(10, new List<>(-1000000000 * 999999, new List<>()))));

        assertFalse(Worksheet1.sorted(listA));
        assertTrue(Worksheet1.sorted(listB));
        assertTrue(Worksheet1.sorted(listC));
        assertFalse(Worksheet1.sorted(listD));

    }

    @Test
    public void sortTest3(){

        List<Integer> listA = new List<>(1, new List<>(7, new List<>(7, new List<>(8, new List<>()))));
        List<Integer> listB = new List<>(1, new List<>(7, new List<>(8, new List<>(7, new List<>()))));

        assertTrue(Worksheet1.sorted(listA));
        assertFalse(Worksheet1.sorted(listB));
    }

    @Test
    public void sortTest4(){

        List<Integer> listA = new List<>(1, new List<>());

        assertTrue(Worksheet1.sorted(listA));
    }

    @Test(expected = IllegalArgumentException.class)
    public void sortedTest5(){
        boolean empty = Worksheet1.sorted(new List<>());
    }

    // =========================== MERGE ===============================================

    @Test
    public void mergeTest(){

        List<Integer> listA = new List<>(2, new List<>(5, new List<>(5, new List<>(8, new List<>()))));
        List<Integer> listB = new List<>(5, new List<>(7, new List<>(8, new List<>(9, new List<>()))));

        List<Integer> listC = new List<>(9, new List<>());


        List<Integer> expectedList = new List<Integer>(2, new List<Integer>(5, new List<Integer>(5, new List<Integer>(5, new List<Integer>(7, new List<Integer>(8, new List(8, new List<Integer>(9, new List()))))))));
        List<Integer> expectedList2 = new List<Integer>(2, new List<Integer>(5, new List<Integer>(5, new List<Integer>(8, new List<Integer>(9, new List<Integer>())))));

        assertTrue(expectedList.equals(Worksheet1.merge(listA, listB)));
        assertTrue(expectedList2.equals(Worksheet1.merge(listA, listC)));


    }

    @Test
    public void  mergeTest2(){

        List<Integer> listA = new List<>(2, new List<>(5, new List<>(5, new List<>(8, new List<>()))));

        List<Integer> expected = new List<>(2, new List<>(5, new List<>(5, new List<>(8, new List<>()))));

        assertTrue(expected.equals(Worksheet1.merge(listA, new List<>())));
        assertTrue(Worksheet1.merge(new List<>(), new List<>()).isEmpty());

    }

    // =========================== REMOVE DUPLICATES  ===============================================

    @Test
    public void removeDupTest(){

        List<Integer> listA = new List<>(2, new List<>(5, new List<>(5, new List<>(8, new List<>()))));
        List<Integer> listB = new List<>(5, new List<>(7, new List<>(9, new List<>(9, new List<>()))));
        List<Integer> listC = new List<>(1, new List<>(1, new List<>(5, new List<>(9, new List<>()))));

        List<Integer> listD = new List<>(9, new List<>());
        List<Integer> listE = new List<>(1, new List<>(1, new List<>(2, new List<>(2, new List<>()))));
        List<Integer> listF = new List<>(1, new List<>(1, new List<>(1, new List<>(1, new List<>()))));

        List<Integer> expectedListA = new List<>(2, new List<>(5, new List<>(8, new List<>())));
        List<Integer> expectedListB = new List<>(5, new List<>(7, new List<>(9, new List<>())));
        List<Integer> expectedListC = new List<>(1, new List<>(5, new List<>(9, new List<>())));
        List<Integer> expectedListF = new List<>(1, new List<>());

        List<Integer> expectedListD = new List<>(9, new List<>());
        List<Integer> expectedListE = new List<>(1, new List<>(2, new List<>()));

        assertTrue(expectedListA.equals(Worksheet1.removeDuplicates(listA)));
        assertTrue(expectedListB.equals(Worksheet1.removeDuplicates(listB)));
        assertTrue(expectedListC.equals(Worksheet1.removeDuplicates(listC)));
        assertTrue(expectedListD.equals(Worksheet1.removeDuplicates(listD)));
        assertTrue(expectedListE.equals(Worksheet1.removeDuplicates(listE)));
        assertTrue(expectedListF.equals(Worksheet1.removeDuplicates(listF)));


    }

    @Test(expected = IllegalArgumentException.class)
    public void  removeDupTest2(){

        List<Integer> listA = Worksheet1.removeDuplicates(new List<>());

    }

}

