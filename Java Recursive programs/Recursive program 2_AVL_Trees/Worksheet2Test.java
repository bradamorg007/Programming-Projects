import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Mr sleep deprived ......morgan B
 *
 *
 */

public class Worksheet2Test {


    // ADD EMPTY LIST CHECKS TO EVERYTHING

    @Test
    public void negateAllTest1(){

        // all pos
        Tree<Integer> tree1 = new Tree<Integer>(5, new Tree<>(3, new Tree<>(1), new Tree<>(4)),
                new Tree<>(8, new Tree<>(7, new Tree<>(6), new Tree<>()), new Tree<>()));

        // all negative
        Tree<Integer> tree2 = new Tree<Integer>(-5, new Tree<>(-3, new Tree<>(-1), new Tree<>(-4)),
                new Tree<>(-8, new Tree<>(-7, new Tree<>(-6), new Tree<>()), new Tree<>()));

        // boundary 1 plus 1 and minus one
        Tree<Integer> tree3 = new Tree<Integer>(5, new Tree<>(3, new Tree<>(1), new Tree<>(4)),
                new Tree<>(8, new Tree<>(7, new Tree<>(-1), new Tree<>()), new Tree<>()));

        // zero
        Tree<Integer> tree4 = new Tree<Integer>(0, new Tree<>(3, new Tree<>(1), new Tree<>(4)),
                new Tree<>(8, new Tree<>(7, new Tree<>(-1), new Tree<>()), new Tree<>()));

        // compute actuals
        Tree<Integer> tree1New = Worksheet2.negateAll(tree1);
        Tree<Integer> tree2New = Worksheet2.negateAll(tree2);
        Tree<Integer> tree3New = Worksheet2.negateAll(tree3);
        Tree<Integer> tree4New = Worksheet2.negateAll(tree4);

        // create the expected
        // all pos
        Tree<Integer> tree1exp = new Tree<Integer>(-5, new Tree<>(-3, new Tree<>(-1), new Tree<>(-4)),
                new Tree<>(-8, new Tree<>(-7, new Tree<>(-6), new Tree<>()), new Tree<>()));

        // all negative
        Tree<Integer> tree2exp = new Tree<Integer>(5, new Tree<>(3, new Tree<>(1), new Tree<>(4)),
                new Tree<>(8, new Tree<>(7, new Tree<>(6), new Tree<>()), new Tree<>()));

        // boundary 1 plus 1 and minus one
        Tree<Integer> tree3exp = new Tree<Integer>(-5, new Tree<>(-3, new Tree<>(-1), new Tree<>(-4)),
                new Tree<>(-8, new Tree<>(-7, new Tree<>(1), new Tree<>()), new Tree<>()));

        // zero
        Tree<Integer> tree4exp = new Tree<Integer>(0, new Tree<>(-3, new Tree<>(-1), new Tree<>(-4)),
                new Tree<>(-8, new Tree<>(-7, new Tree<>(1), new Tree<>()), new Tree<>()));

        assertTrue(tree1exp.equals(tree1New));
        assertTrue(tree2exp.equals(tree2New));
        assertTrue(tree3exp.equals(tree3New));
        assertTrue(tree4exp.equals(tree4New));


    }

    @Test
    public void checkPositiveTest(){

        // all pos
        Tree<Integer> tree1 = new Tree<Integer>(5, new Tree<>(3, new Tree<>(1), new Tree<>(4)),
                new Tree<>(8, new Tree<>(7, new Tree<>(6), new Tree<>()), new Tree<>()));

        // all negative
        Tree<Integer> tree2 = new Tree<Integer>(-5, new Tree<>(-3, new Tree<>(-1), new Tree<>(-4)),
                new Tree<>(-8, new Tree<>(-7, new Tree<>(-6), new Tree<>()), new Tree<>()));

        // boundary 1 plus 1 and minus one
        Tree<Integer> tree3 = new Tree<Integer>(5, new Tree<>(3, new Tree<>(1), new Tree<>(4)),
                new Tree<>(8, new Tree<>(7, new Tree<>(-1), new Tree<>()), new Tree<>()));

        // zero
        Tree<Integer> tree4 = new Tree<Integer>(6, new Tree<>(3, new Tree<>(1), new Tree<>(0)),
                new Tree<>(8, new Tree<>(7, new Tree<>(1), new Tree<>()), new Tree<>()));


        assertTrue(Worksheet2.allPositive(tree1));
        assertFalse(Worksheet2.allPositive(tree2));
        assertFalse(Worksheet2.allPositive(tree3));
        assertFalse(Worksheet2.allPositive(tree4));


    }

    @Test
    public void levelTest(){

        Tree<Integer> tree1 = new Tree<Integer>(5, new Tree<>(3, new Tree<>(1), new Tree<>(4)),
                new Tree<>(8, new Tree<>(6, new Tree<>(), new Tree<>()), new Tree<>()));


        int x1 = 5;
        int expLevel1 = 1;

        int x2 = 3;
        int expLevel2 = 2;

        int x3 = 6;
        int expLevel3 = 3;

        int x4 = 2;
        int expLevel4 = 0;

        assertEquals(expLevel1, Worksheet2.level(tree1, x1));
        assertEquals(expLevel2, Worksheet2.level(tree1, x2));
        assertEquals(expLevel3, Worksheet2.level(tree1, x3));
        assertEquals(expLevel4, Worksheet2.level(tree1, x4));

    }

    @Test
    public void postOrderTest(){

        Tree<Integer> tree1 = new Tree<Integer>(5, new Tree<>(3, new Tree<>(1), new Tree<>(4)),
                new Tree<>(8, new Tree<>(6, new Tree<>(), new Tree<>()), new Tree<>()));


        Tree<Integer> tree2 = new Tree<Integer>(5, new Tree<>(3, new Tree<>(1), new Tree<>(4)),
                new Tree<>(8, new Tree<>(6, new Tree<>(), new Tree<>()), new Tree<>(9)));

        Tree<Integer> tree3 = new Tree<Integer>(5, new Tree<>(3, new Tree<>(1), new Tree<>()),
                new Tree<>(8, new Tree<>(6, new Tree<>(), new Tree<>()), new Tree<>()));

        Tree<Integer> tree4 = new Tree<>();



        List<Integer> expList1 = new List<Integer>(1, new List<>(4, new List<>(3, new List<>(6, new List<>(8, new List<>(5, new List<>()))))));
        List<Integer> expList2 = new List<Integer>(1, new List<>(4, new List<>(3, new List<>(6, new List<>(9, new List<>(8, new List<>(5, new List<>())))))));
        List<Integer> expList3 = new List<Integer>(1, new List<>( 3, new List<>(6, new List<>(8, new List<>(5, new List<>())))));


        //System.out.println(tree2);
        System.out.println(Worksheet2.postorder(tree1));
        System.out.println(Worksheet2.postorder(tree2));
        System.out.println(Worksheet2.postorder(tree3));
        System.out.println(Worksheet2.postorder(tree4));
        assertTrue(expList1.equals(Worksheet2.postorder(tree1)));
        assertTrue(expList2.equals(Worksheet2.postorder(tree2)));
        assertTrue(expList3.equals(Worksheet2.postorder(tree3)));
        assertTrue(new List<Integer>().equals(Worksheet2.postorder(tree4)));


    }

    @Test
    public void isSearchTree(){

        Tree<Integer>tree = new Tree<>(50, new Tree<>(20), new Tree<>(60, new Tree<>(45), new Tree<>(65)));

        System.out.println(Worksheet2.isSearchTree(tree));
        assertFalse(Worksheet2.isSearchTree(tree));

        Tree<Integer> bigBoyAVL1 = new Tree<>(200, new Tree<>(100, new Tree<>(50, new Tree<>(40, new Tree<>(30, new Tree<>(20), new Tree<>()), new Tree<>(45, new Tree<>(41, new Tree<>(), new Tree<>(42)), new Tree<>(48))), new Tree<>(60, new Tree<>(55, new Tree<>(), new Tree<>(59)), new Tree<>(70))), new Tree<>(150, new Tree<>(140, new Tree<>(139), new Tree<>(142, new Tree<>(), new Tree<>(143))), new Tree<>(160, new Tree<>(155), new Tree<>()))),
                new Tree<>(300, new Tree<>(250, new Tree<>(240), new Tree<>(260, new Tree<>(255), new Tree<>(270))), new Tree<>(400, new Tree<>(350, new Tree<>(340), new Tree<>(360, new Tree<>(355), new Tree<>())), new Tree<>(500, new Tree<>(450), new Tree<>()))));

        Tree<Integer> bigBoyAVL2 = new Tree<>(200, new Tree<>(100, new Tree<>(50, new Tree<>(40, new Tree<>(30, new Tree<>(20), new Tree<>()), new Tree<>(45, new Tree<>(41, new Tree<>(), new Tree<>(42)), new Tree<>(48))), new Tree<>(60, new Tree<>(55, new Tree<>(), new Tree<>(59)), new Tree<>(70))), new Tree<>(150, new Tree<>(140, new Tree<>(139), new Tree<>(142, new Tree<>(), new Tree<>(143))), new Tree<>(160, new Tree<>(155), new Tree<>()))),
                new Tree<>(300, new Tree<>(250, new Tree<>(240), new Tree<>(260, new Tree<>(255), new Tree<>(270))), new Tree<>(400, new Tree<>(350, new Tree<>(340), new Tree<>(360, new Tree<>(355), new Tree<>())), new Tree<>(500, new Tree<>(501), new Tree<>()))));

        Tree<Integer> bigBoyAVL3 = new Tree<>(200, new Tree<>(100, new Tree<>(50, new Tree<>(40, new Tree<>(30, new Tree<>(20), new Tree<>()), new Tree<>(45, new Tree<>(41, new Tree<>(), new Tree<>(41)), new Tree<>(48))), new Tree<>(60, new Tree<>(55, new Tree<>(), new Tree<>(59)), new Tree<>(70))), new Tree<>(150, new Tree<>(140, new Tree<>(139), new Tree<>(142, new Tree<>(), new Tree<>(143))), new Tree<>(160, new Tree<>(155), new Tree<>()))),
                new Tree<>(300, new Tree<>(250, new Tree<>(240), new Tree<>(260, new Tree<>(255), new Tree<>(270))), new Tree<>(400, new Tree<>(350, new Tree<>(340), new Tree<>(360, new Tree<>(355), new Tree<>())), new Tree<>(500, new Tree<>(450), new Tree<>()))));

        Tree<Integer> bigBoyAVL4 = new Tree<>(200, new Tree<>(100, new Tree<>(50, new Tree<>(40, new Tree<>(30, new Tree<>(20), new Tree<>()), new Tree<>(45, new Tree<>(41, new Tree<>(), new Tree<>(42)), new Tree<>(48))), new Tree<>(60, new Tree<>(55, new Tree<>(), new Tree<>(59)), new Tree<>(70))), new Tree<>(201, new Tree<>(140, new Tree<>(139), new Tree<>(142, new Tree<>(), new Tree<>(143))), new Tree<>(202, new Tree<>(155), new Tree<>()))),
                new Tree<>(300, new Tree<>(250, new Tree<>(240), new Tree<>(260, new Tree<>(255), new Tree<>(270))), new Tree<>(400, new Tree<>(350, new Tree<>(340), new Tree<>(360, new Tree<>(355), new Tree<>())), new Tree<>(500, new Tree<>(450), new Tree<>()))));

        System.out.println("======================================");
        assertTrue(Worksheet2.isSearchTree(bigBoyAVL1));

        System.out.println("======================================");
        assertFalse(Worksheet2.isSearchTree(bigBoyAVL2));

        System.out.println("======================================");
        assertFalse(Worksheet2.isSearchTree(bigBoyAVL3));

        System.out.println("======================================");
        assertFalse(Worksheet2.isSearchTree(bigBoyAVL4));


    }
    @Test(expected = IllegalArgumentException.class)
    public void maxTest(){

        Tree<Integer> tree1 = new Tree<Integer>(5, new Tree<>(3, new Tree<>(1), new Tree<>(4)),
                new Tree<>(8, new Tree<>(6, new Tree<>(), new Tree<>()), new Tree<>()));


        Tree<Integer> tree2 = new Tree<>(10, new Tree<>(5, new Tree<>(2, new Tree<>(), new Tree<>()), new Tree<>(7, new Tree<>(6), new Tree<>(8))), new Tree<>(15, new Tree<>(13), new Tree<>(20, new Tree<>(17, new Tree<>(16), new Tree<>(18)), new Tree<>(500, new Tree<>(), new Tree<>(754)))));


        System.out.println(tree2);

        int expMax1 = 8;
        int expMax2 = 754;

        assertEquals(expMax1, Worksheet2.max(tree1));
        assertEquals(expMax2, Worksheet2.max(tree2));
        Worksheet2.max(new Tree<>());

    }

    // Function does not call max if the node to be deleted only has one child.
    @Test
    public void DeleteTest(){

        Tree<Integer> tree1 = new Tree<Integer>(5, new Tree<>(3, new Tree<>(1), new Tree<>(4)),
                new Tree<>(8, new Tree<>(6, new Tree<>(), new Tree<>()), new Tree<>()));

        // delete leaf node = 1
        Tree<Integer> expTree1 = new Tree<Integer>(5, new Tree<>(3, new Tree<>(), new Tree<>(4)),
                new Tree<>(8, new Tree<>(6, new Tree<>(), new Tree<>()), new Tree<>()));

        // delete node with two children but not main root = 3
        Tree<Integer> expTree2 = new Tree<Integer>(5, new Tree<>(1, new Tree<>(), new Tree<>(4)),
                new Tree<>(8, new Tree<>(6, new Tree<>(), new Tree<>()), new Tree<>()));

        // delete node with one child = 8;
        Tree<Integer> expTree3 = new Tree<Integer>(5, new Tree<>(3, new Tree<>(1), new Tree<>(4)),
                new Tree<>(6, new Tree<>(), new Tree<>()));

        // delete main root node = 5
        Tree<Integer> expTree4 = new Tree<Integer>(4, new Tree<>(3, new Tree<>(1), new Tree<>()),
                new Tree<>(8, new Tree<>(6, new Tree<>(), new Tree<>()), new Tree<>()));


        assertTrue(expTree1.equals(Worksheet2.delete(tree1, 1)));
        assertTrue(expTree2.equals(Worksheet2.delete(tree1, 3)));
        assertTrue(expTree3.equals(Worksheet2.delete(tree1, 8)));
        assertTrue(expTree4.equals(Worksheet2.delete(tree1, 5)));

        // check case when value doesnt exist
        assertTrue(tree1.equals(Worksheet2.delete(tree1, 7)));

        // check passing in an empty tree
        assertTrue(new Tree<>().equals(Worksheet2.delete(new Tree<>(), 7)));

        // assess deleting a value from a tree with only one value in it
        assertTrue(new Tree<>().equals(Worksheet2.delete(new Tree<>(5), 5)));


    }

    @Test
    public void isAVLTest(){

        // Left Rotation
        Tree<Integer> L400 = new Tree<Integer>(100, new Tree<>(50),
                new Tree<>(200, new Tree<>(), new Tree<>(300, new Tree<>(), new Tree<>())));

        // Right rotation
        Tree<Integer> R100 = new Tree<>(400,
                new Tree<>(300, new Tree<>(200), new Tree<>()),
                new Tree<>(500));


        // Left Right Rotation
        Tree<Integer> LR270 = new Tree<>(200,
                new Tree<>(100),
                new Tree<>(300, new Tree<>(250), new Tree<>()));

        // right Left rotation
        Tree<Integer> RL120 = new Tree<>(200,
                new Tree<>(100, new Tree<>(), new Tree<>(150)),
                new Tree<>(300));


        // left Right rotation
        Tree<Integer> LR45 = new Tree<>(200,
                new Tree<>(100, new Tree<>(40, new Tree<>(), new Tree<>()), new Tree<>()),
                new Tree<>(300));

        // right left rotation
        Tree<Integer> RL350 = new Tree<>(200,
                new Tree<>(100, new Tree<>(), new Tree<>()),
                new Tree<>(300, new Tree<>(), new Tree<>(400, new Tree<>(), new Tree<>())));


        assertTrue(Worksheet2.isHeightBalanced(L400));
        assertTrue(Worksheet2.isHeightBalanced(R100));
        assertTrue(Worksheet2.isHeightBalanced(LR270));
        assertTrue(Worksheet2.isHeightBalanced(RL120));
        assertTrue(Worksheet2.isHeightBalanced(LR45));
        assertTrue(Worksheet2.isHeightBalanced(RL350));

        // Left Rotation
        Tree<Integer> FalseL400 = new Tree<Integer>(100, new Tree<>(50),
                new Tree<>(200, new Tree<>(), new Tree<>(300, new Tree<>(), new Tree<>(400))));

        // Right rotation
        Tree<Integer> FalseR100 = new Tree<>(400,
                new Tree<>(300, new Tree<>(200, new Tree<>(100), new Tree<>()), new Tree<>()),
                new Tree<>(500));


        // Left Right Rotation
        Tree<Integer> FalseLR270 = new Tree<>(200,
                new Tree<>(100),
                new Tree<>(300, new Tree<>(250, new Tree<>(), new Tree<>(270)), new Tree<>()));

        // right Left rotation
        Tree<Integer> FalseRL120 = new Tree<>(200,
                new Tree<>(100, new Tree<>(), new Tree<>(150, new Tree<>(120), new Tree<>())),
                new Tree<>(300));


        // left Right rotation
        Tree<Integer> FalseLR45 = new Tree<>(200,
                new Tree<>(100, new Tree<>(40, new Tree<>(), new Tree<>(45)), new Tree<>()),
                new Tree<>(300));

        // right left rotation
        Tree<Integer> FalseRL350 = new Tree<>(200,
                new Tree<>(100, new Tree<>(), new Tree<>()),
                new Tree<>(300, new Tree<>(), new Tree<>(400, new Tree<>(350), new Tree<>())));


        assertFalse(Worksheet2.isHeightBalanced(FalseL400));
        assertFalse(Worksheet2.isHeightBalanced(FalseR100));
        assertFalse(Worksheet2.isHeightBalanced(FalseLR270));
        assertFalse(Worksheet2.isHeightBalanced(FalseRL120));
        assertFalse(Worksheet2.isHeightBalanced(FalseLR45));
        assertFalse(Worksheet2.isHeightBalanced(FalseRL350));

        // Test function where the an unbalanced node exist only in one location on the right, full traversal required

        Tree<Integer> FalseOneUnbalanced = new Tree<>(200, new Tree<>(100, new Tree<>(50, new Tree<>(40, new Tree<>(30), new Tree<>(45)), new Tree<>(60)), new Tree<>(150, new Tree<>(140), new Tree<>())),
                new Tree<>(300, new Tree<>(250, new Tree<>(), new Tree<>(260)), new Tree<>(400, new Tree<>(350, new Tree<>(), new Tree<>(360)), new Tree<>())));


        assertFalse(Worksheet2.isHeightBalanced(FalseOneUnbalanced));

        // test function where the single unbalanced node is on deep on left side of the tree

        Tree<Integer> FalseOneUnbalanced2 = new Tree<>(200, new Tree<>(100, new Tree<>(50, new Tree<>(40, new Tree<>(30), new Tree<>(45)), new Tree<>(60)), new Tree<>(150, new Tree<>(140, new Tree<>(), new Tree<>(142, new Tree<>(141), new Tree<>())), new Tree<>(160))),
                new Tree<>(300, new Tree<>(250, new Tree<>(), new Tree<>(260)), new Tree<>(400, new Tree<>(350, new Tree<>(), new Tree<>()), new Tree<>())));

        assertFalse(Worksheet2.isHeightBalanced(FalseOneUnbalanced2));

        // Try is AVL with one node

        assertTrue(Worksheet2.isHeightBalanced(new Tree<>(50)));

        // try with empty tree
        assertTrue(Worksheet2.isHeightBalanced(new Tree<>()));


    }

    @Test
    public void insertionAVLTest(){

                // Left Rotation
        Tree<Integer> L400 = new Tree<Integer>(100, new Tree<>(50),
                new Tree<>(200, new Tree<>(), new Tree<>(300, new Tree<>(), new Tree<>())));

        // Right rotation
        Tree<Integer> R100 = new Tree<>(400,
                new Tree<>(300, new Tree<>(200), new Tree<>()),
                new Tree<>(500));


        // Left Right Rotation
        Tree<Integer> LR270 = new Tree<>(200,
                new Tree<>(100),
                new Tree<>(300, new Tree<>(250), new Tree<>()));

        // right Left rotation
        Tree<Integer> RL120 = new Tree<>(200,
                new Tree<>(100, new Tree<>(), new Tree<>(150)),
                new Tree<>(300));


        // left Right rotation
        Tree<Integer> LR45 = new Tree<>(200,
                new Tree<>(100, new Tree<>(40, new Tree<>(), new Tree<>()), new Tree<>()),
                new Tree<>(300));

        // right left rotation
        Tree<Integer> RL350 = new Tree<>(200,
                new Tree<>(100, new Tree<>(), new Tree<>()),
                new Tree<>(300, new Tree<>(), new Tree<>(400, new Tree<>(), new Tree<>())));


        // Left right Rotation
        Tree<Integer> LR360 = new Tree<>(200, new Tree<>(100, new Tree<>(50, new Tree<>(40, new Tree<>(30), new Tree<>(45)), new Tree<>(60)), new Tree<>(150, new Tree<>(140), new Tree<>())),
                new Tree<>(300, new Tree<>(250, new Tree<>(), new Tree<>(260)), new Tree<>(400, new Tree<>(350, new Tree<>(), new Tree<>()), new Tree<>())));

        // Left rotation
        Tree<Integer> L143 = new Tree<>(200, new Tree<>(100, new Tree<>(50, new Tree<>(40, new Tree<>(30), new Tree<>(45)), new Tree<>(60)), new Tree<>(150, new Tree<>(140, new Tree<>(), new Tree<>(142, new Tree<>(), new Tree<>())), new Tree<>(160))),
                new Tree<>(300, new Tree<>(250, new Tree<>(), new Tree<>(260)), new Tree<>(400, new Tree<>(350, new Tree<>(), new Tree<>()), new Tree<>())));

        // Right rotation
        Tree<Integer> R20 = new Tree<>(200, new Tree<>(100, new Tree<>(50, new Tree<>(40, new Tree<>(30, new Tree<>(), new Tree<>()), new Tree<>(45)), new Tree<>(60)), new Tree<>(150, new Tree<>(140, new Tree<>(), new Tree<>(142, new Tree<>(), new Tree<>())), new Tree<>(160))),
                new Tree<>(300, new Tree<>(250, new Tree<>(), new Tree<>(260)), new Tree<>(400, new Tree<>(350, new Tree<>(), new Tree<>()), new Tree<>())));


        // Right left Rotation
        Tree<Integer> RL55 = new Tree<>(200, new Tree<>(100, new Tree<>(50, new Tree<>(), new Tree<>(60, new Tree<>(), new Tree<>())), new Tree<>(150, new Tree<>(140), new Tree<>())),
                new Tree<>(300, new Tree<>(250, new Tree<>(), new Tree<>(260)), new Tree<>(400, new Tree<>(350, new Tree<>(), new Tree<>()), new Tree<>())));

        // FIRST - confirm that all above trees are AVL trees to before insertion

        // Small Trees
        assertTrue(Worksheet2.isHeightBalanced(L400));
        assertTrue(Worksheet2.isHeightBalanced(R100));
        assertTrue(Worksheet2.isHeightBalanced(LR270));
        assertTrue(Worksheet2.isHeightBalanced(RL120));
        assertTrue(Worksheet2.isHeightBalanced(LR45));
        assertTrue(Worksheet2.isHeightBalanced(RL350));

        // Larger trees
        assertTrue(Worksheet2.isHeightBalanced(LR360));
        assertTrue(Worksheet2.isHeightBalanced(L143));
        assertTrue(Worksheet2.isHeightBalanced(R20));
        assertTrue(Worksheet2.isHeightBalanced(RL55));

        // STEP 2 - Add insertion values and confirm that the trees are now unbalanced use insert test helper

        // smaller trees
        Tree<Integer> L400False = insertTestHelper(L400, 400);
        Tree<Integer> R100False = insertTestHelper(R100, 100);
        Tree<Integer> LR270False = insertTestHelper(LR270, 270);
        Tree<Integer> RL120False = insertTestHelper(RL120, 120);
        Tree<Integer> LR45False = insertTestHelper(LR45, 45);
        Tree<Integer> RL350False = insertTestHelper(RL350, 350);

        // Larger Unbalanced Trees
        Tree<Integer> LR360False = insertTestHelper(LR360, 360);
        Tree<Integer> L143False = insertTestHelper(L143, 143);
        Tree<Integer> R20False = insertTestHelper(R20, 20);
        Tree<Integer> RL55False = insertTestHelper(RL55, 55);

        // smaller trees
        assertFalse(Worksheet2.isHeightBalanced(L400False));
        assertFalse(Worksheet2.isHeightBalanced(R100False));
        assertFalse(Worksheet2.isHeightBalanced(LR270False));
        assertFalse(Worksheet2.isHeightBalanced(RL120False));
        assertFalse(Worksheet2.isHeightBalanced(LR45False));
        assertFalse(Worksheet2.isHeightBalanced(RL350False));

        // Larger Trees
        assertFalse(Worksheet2.isHeightBalanced(LR360False));
        assertFalse(Worksheet2.isHeightBalanced(L143False));
        assertFalse(Worksheet2.isHeightBalanced(R20False));
        assertFalse(Worksheet2.isHeightBalanced(RL55False));


        // Step 3 - use the insertionHB function and confirm that the insertion also balanced the trees

        // smaller trees
        Tree<Integer> L400New = Worksheet2.insertHB(L400, 400);
        Tree<Integer> R100New = Worksheet2.insertHB(R100, 100);
        Tree<Integer> LR270New = Worksheet2.insertHB(LR270, 270);
        Tree<Integer> RL120New = Worksheet2.insertHB(RL120, 120);
        Tree<Integer> LR45New = Worksheet2.insertHB(LR45, 45);
        Tree<Integer> RL350New = Worksheet2.insertHB(RL350, 350);

        // Larger Unbalanced Trees
        Tree<Integer> LR360New = Worksheet2.insertHB(LR360, 360);
        Tree<Integer> L143New = Worksheet2.insertHB(L143, 143);
        Tree<Integer> R20New = Worksheet2.insertHB(R20, 20);
        Tree<Integer> RL55New = Worksheet2.insertHB(RL55, 55);


        // smaller trees
        assertTrue(Worksheet2.isHeightBalanced(L400New));
        assertTrue(Worksheet2.isHeightBalanced(R100New));
        assertTrue(Worksheet2.isHeightBalanced(LR270New));
        assertTrue(Worksheet2.isHeightBalanced(RL120New));
        assertTrue(Worksheet2.isHeightBalanced(LR45New));
        assertTrue(Worksheet2.isHeightBalanced(RL350New));

        // Larger Trees
        assertTrue(Worksheet2.isHeightBalanced(LR360New));
        assertTrue(Worksheet2.isHeightBalanced(L143New));
        assertTrue(Worksheet2.isHeightBalanced(R20New));
        assertTrue(Worksheet2.isHeightBalanced(RL55New));



    }


    @Test
    public void deletionAvl(){

        // Step 1 - Create small tree cases

        // tree requires Right rotation
        Tree<Integer>delA15 = new Tree<>(10, new Tree<>(5, new Tree<>(1), new Tree<>()), new Tree<>(15));

        // tree requires a left roation
        Tree<Integer>delA5 = new Tree<>(10, new Tree<>(5), new Tree<>(15, new Tree<>(), new Tree<>(20)));


        // tree requires a left right rotation
        Tree<Integer>delB15 = new Tree<>(10, new Tree<>(5, new Tree<>(), new Tree<>(7)), new Tree<>(15));

        // tree requires a right left rotation
        Tree<Integer> delB5 = new Tree<>(10, new Tree<>(5), new Tree<>(15, new Tree<>(13), new Tree<>()));


        // Step 2 confirm that these tree are AVL trees to begin with

        assertTrue(Worksheet2.isHeightBalanced(delA15));
        assertTrue(Worksheet2.isHeightBalanced(delA5));
        assertTrue(Worksheet2.isHeightBalanced(delB15));
        assertTrue(Worksheet2.isHeightBalanced(delB5));


        // STep 2 - delete the target values and confirm that action creates an non-AVL tree
        // tree requires Right rotation
        Tree<Integer>delA15False = Worksheet2.delete(delA15, 15);

        // tree requires a left roation
        Tree<Integer>delA5False = Worksheet2.delete(delA5, 5);


        // tree requires a left right rotation
        Tree<Integer>delB15False = Worksheet2.delete(delB15, 15);

        // tree requires a right left rotation
        Tree<Integer> delB5False = Worksheet2.delete(delB5, 5);

        assertFalse(Worksheet2.isHeightBalanced(delA15False));
        assertFalse(Worksheet2.isHeightBalanced(delA5False));
        assertFalse(Worksheet2.isHeightBalanced(delB15False));
        assertFalse(Worksheet2.isHeightBalanced(delB5False));

        // Step 3 - form the same deletion using deleteHB and confirm the deletion produces a AVL tree

        Tree<Integer>delA15New = Worksheet2.deleteHB(delA15, 15);

        // tree requires a left roation
        Tree<Integer>delA5New = Worksheet2.deleteHB(delA5, 5);


        // tree requires a left right rotation
        Tree<Integer>delB15New = Worksheet2.deleteHB(delB15, 15);

        // tree requires a right left rotation
        Tree<Integer> delB5New = Worksheet2.deleteHB(delB5, 5);

        assertTrue(Worksheet2.isHeightBalanced(delA15New));
        assertTrue(Worksheet2.isHeightBalanced(delA5New));
        assertTrue(Worksheet2.isHeightBalanced(delB15New));
        assertTrue(Worksheet2.isHeightBalanced(delB5New));

        // Try some rotations and dletions on a huge tree ahahah...huh -_-

        Tree<Integer> bigBoyAVL = new Tree<>(200, new Tree<>(100, new Tree<>(50, new Tree<>(40, new Tree<>(30, new Tree<>(20), new Tree<>()), new Tree<>(45, new Tree<>(41, new Tree<>(), new Tree<>(42)), new Tree<>(48))), new Tree<>(60, new Tree<>(55, new Tree<>(), new Tree<>(59)), new Tree<>(70))), new Tree<>(150, new Tree<>(140, new Tree<>(139), new Tree<>(142, new Tree<>(), new Tree<>(143))), new Tree<>(160, new Tree<>(155), new Tree<>()))),
                new Tree<>(300, new Tree<>(250, new Tree<>(240), new Tree<>(260, new Tree<>(255), new Tree<>(270))), new Tree<>(400, new Tree<>(350, new Tree<>(340), new Tree<>(360, new Tree<>(355), new Tree<>())), new Tree<>(500, new Tree<>(450), new Tree<>()))));


        assertTrue(Worksheet2.isHeightBalanced(bigBoyAVL));

        Tree<Integer>unBalancedBigBoyAVL = Worksheet2.delete(bigBoyAVL, 355);

        assertFalse(Worksheet2.isHeightBalanced(unBalancedBigBoyAVL));

        Tree<Integer>bigBoyAVLNew = Worksheet2.deleteHB(bigBoyAVL, 355);

        assertTrue(Worksheet2.isHeightBalanced(bigBoyAVLNew));

        // empty root should be
        assertTrue(Worksheet2.isHeightBalanced(Worksheet2.deleteHB(new Tree<>(50), 50)));

        assertTrue(Worksheet2.isHeightBalanced(Worksheet2.deleteHB(new Tree<>(), 50)));



    }


    // INSERT
    static Tree<Integer> insertTestHelper(Tree<Integer> a, int x) {
        if (a.isEmpty())
            return new Tree<>(x);
        else if (x < a.getValue())
            return new Tree<>(a.getValue(), insertTestHelper(a.getLeft(), x), a.getRight());
        else
            return new Tree<>(a.getValue(), a.getLeft(), insertTestHelper(a.getRight(), x));

    }



}
