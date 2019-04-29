/**
 * 
 *This class contains the solution for Worksheet2
 */

public class Worksheet2 implements Worksheet2Interface {


    // Exercise 1

    /**
     * Function flips the signs of all integers in a tree. Positives become negative vice versa
     * @param t The tree object with elements to be negated
     * @return the negated tree
     */
	static Tree<Integer> negateAll(Tree<Integer> t) {

	    if (t.isEmpty()){
	        return new Tree<Integer>();
        }else{
	        return new Tree<Integer>(t.getValue() * -1, negateAll(t.getLeft()), negateAll(t.getRight()));
        }
	}

	// Exercise 2

    /**
     *
     * @param a a tree comprised of integer nodes
     * @return true = all nodes are positive and false if not all nodes are positive.
     */
	static boolean allPositive(Tree<Integer> a) {
		// check if root is positive if so -  check its left child
		// if you reach the end

        if(a.isEmpty()){
            return true;
        }
        if(a.getValue() > 0){
            return allPositive(a.getLeft()) && allPositive(a.getRight());
        }else{
            return false;
        }
	}

	// Exercise 3

    /**
     * This function uses local variable that does not change within its scope
     * This allows the recursive process for left and right branches to be re-used
     * rather than re-computed
     * @param a
     * @param x the element to find
     * @return the level of x if it is found and 0 otherwise.
     */
	static int level(Tree<Integer> a, int x) {

		if(a.isEmpty()){
			return 0;

		} else if(x == a.getValue()) {
			return 1;

		}

		// check the left tree
		int levelLeft = levelHelper(level(a.getLeft(), x));

		// if x has been found return its level
		if(levelLeft > 0){
		    return levelLeft;
		}

		// if canot find in left then search the right side
		int levelRight = levelHelper(level(a.getRight(), x));

		// if x has been found the return its level
        if(levelRight > 0){
            return levelRight;
        }

        // else nothing has been found and thus return false
        return 0;

	}

	static int levelHelper(int level){

        if(level == 0){
            return 0;
        }else {
            return level + 1;
        }
    }

	// Exercise 4

    /**
     *
     * @param a a generic Tree nodes to be traversed in post Order
     * @param <E> Generic type parameter
     * @return a Generic list containing the pre-ordered nodes of the input tree
     */
	static <E> List<E> postorder(Tree<E> a) {

       if (a.isEmpty()){
           return new List<>();
       }

           return append(postorder(a.getLeft()), addToEnd(postorder(a.getRight()), a.getValue()));


    }

	// Exercise 5

    /**
     * Initially one may use max function to find the max of the left branch and min function to find the
     * the min of the right branch of a tree. If the max of the left is smaller than current root node and the
     * min of the right branch is greater than the root, then this current node is valid as binary tree. The program would
     * then move on to check at most all nodes in the tree to confirm that all nodes and their children
     * conform to the binary search configuration. This method will work but it can use an excessive amount of recursive
     * calls to calculate max and min for every node.
     *
     * Instead the method below breaks the comparison down to 3s,- a root , its left and right child been compared at
     * a one given time. The method first begins by assessing the main/initial root node against its left and right.getvalues.
     *if the left.getvalue is smaller than the root and right.getvalue is more than the root then the initial assessment
     * is a binary tree. The program then splits the tree into its right branch and left branch. RightRules function
     * takes the x = the main root node to begin with. if the program traverses left then the assessment of whether
     * the a.getleft.getvalue is smaller than root also has the added comparison of if the left child is greater than x.
     * If this is true then then the left to root link is valid. Once the program traverses right, x is changed to
     * the value of the previous root, now any left comparisons with the new current root must be greater than the new x.
     *
     * The opposite logic applies to leftRules - any comparions to the right must be smaller than x to be valid.
     * If the program traverse to the left , x will change to the value of the previous root node.
     *
     * Likewise the program does not recurse down to leaf nodes, recursion terminates when a nodes childrens-children
     * are both empty, saving the need for uneeded calls.
     *
     * Overall this function all tho long winded reduces the number of required recursive calls needed to check the whole tree
     * in some cases it can reduce the number of recursive calls by a half as compared to the min max approach
     *
     *
     * @param a the tree to be traversed
     * @return true if the tree nodes are organised as a binary search tree and false if not.
     */
    static boolean isSearchTree(Tree<Integer> a){

	    if(a.isEmpty()){
	        return true;
        }

        // if node is a leaf its all good
        if (a.getRight().isEmpty() && a.getLeft().isEmpty()) {
            // System.out.println("Recursive call");
            return true;
        }

        // if node has to children then compare it - This is where x needs to be compared against.
        if (!a.getRight().isEmpty() && !a.getLeft().isEmpty()) {


            System.out.println("Initial check: Vals - " + a.getValue() + " " + a.getLeft().getValue() + " " + a.getRight().getValue());
            if (a.getRight().getValue() <= a.getValue() || a.getLeft().getValue() >= a.getValue()){
                return false;
            }else if(a.getLeft().getLeft().isEmpty() && a.getLeft().getRight().isEmpty() && a.getRight().getRight().isEmpty() && a.getRight().getLeft().isEmpty()){
                return true;
            }

        } else {

            // node has does not have two children.

            // left is full right is empty
            if (!a.getLeft().isEmpty() && a.getRight().isEmpty()) {
                System.out.println("Initial check: Vals - " + a.getValue() + " " + a.getLeft().getValue() + " ");

                if (a.getLeft().getValue() >= a.getValue()) {
                    return false;
                }else if(a.getLeft().getLeft().isEmpty()){
                    return true;
                }
            }

            //

            // right is full and left is empty
            if (!a.getRight().isEmpty() && a.getLeft().isEmpty()) {

                System.out.println("Initial check: Vals - " + a.getValue() + " " + a.getRight().getValue());
                if (a.getRight().getValue() <= a.getValue()) {
                    return false;
                }else if(a.getRight().getRight().isEmpty()){
                    return true;
                }
            }
        }

        return leftRules(a.getLeft(), a.getValue()) && rightRules(a.getRight(), a.getValue());

    }

    /**
     *
     * @param a the right main branch of tree
     * @param x the previous value of the root
     * @return if the current root and children are a binary search tree
     */
    static boolean rightRules(Tree<Integer> a, int x){

        if (a.isEmpty()) {
            //System.out.println("Recursive call");
            return true;
        }

        // if node is a leaf its all good
        if (a.getRight().isEmpty() && a.getLeft().isEmpty()) {
            // System.out.println("Recursive call");
            return true;
        }

        // if node has to children then compare it - This is where x needs to be compared against.
        if (!a.getRight().isEmpty() && !a.getLeft().isEmpty()) {


            System.out.println("RulesRight check: Vals - " + a.getValue() + " " + a.getLeft().getValue() + " " + a.getRight().getValue() + "  x = " + x);
            if (a.getRight().getValue() <= a.getValue() || a.getLeft().getValue() >= a.getValue() || a.getLeft().getValue() <= x ){
                return false;
            }else if(a.getLeft().getLeft().isEmpty() && a.getLeft().getRight().isEmpty() && a.getRight().getRight().isEmpty() && a.getRight().getLeft().isEmpty()){
                return true;
            }

        } else {

            // node has does not have two children.

            // left is full right is empty
            if (!a.getLeft().isEmpty() && a.getRight().isEmpty()) {

                System.out.println("RulesRight check: Vals - " + a.getValue() + " " + a.getLeft().getValue() + "  x = " + x);

                if (a.getLeft().getValue() >= a.getValue() || a.getLeft().getValue() <= x) {
                    return false;
                }else if(a.getLeft().getLeft().isEmpty()){
                    return true;
                }
            }

            //

            // right is full and left is empty
            if (!a.getRight().isEmpty() && a.getLeft().isEmpty()) {

                System.out.println("RulesRight check: Vals - " + a.getValue() + " " + a.getRight().getValue());

                if (a.getRight().getValue() <= a.getValue()) {
                    return false;
                }else if(a.getRight().getRight().isEmpty()){
                    return true;
                }
            }
        }

        return rightRules(a.getLeft(), x) && rightRules(a.getRight(), a.getValue());


    }


    /**
     *
     * @param a the right main branch of tree
     * @param x the previous value of the root
     * @return if the current root and children are a binary search tree
     */
    static boolean leftRules(Tree<Integer> a, int x){

        if (a.isEmpty()) {
            //System.out.println("Recursive call");
            return true;
        }

        // if node is a leaf its all good
        if (a.getRight().isEmpty() && a.getLeft().isEmpty()) {
            // System.out.println("Recursive call");
            return true;
        }

        // if node has to children then compare it - This is where x needs to be compared against.
        if (!a.getRight().isEmpty() && !a.getLeft().isEmpty()) {


            System.out.println("RulesLeft check: Vals - " + a.getValue() + " " + a.getLeft().getValue() + " " + a.getRight().getValue() + "  x = " + x);

            if (a.getRight().getValue() <= a.getValue() || a.getLeft().getValue() >= a.getValue() || a.getRight().getValue() >= x){
                return false;
            }else if(a.getLeft().getLeft().isEmpty() && a.getLeft().getRight().isEmpty() && a.getRight().getRight().isEmpty() && a.getRight().getLeft().isEmpty()){
                return true;
            }

        } else {

            // node has does not have two children.

            // left is full right is empty
            if (!a.getLeft().isEmpty() && a.getRight().isEmpty()) {
                System.out.println("RulesLeft check: Vals - " + a.getValue() + " " + a.getLeft().getValue());


                if (a.getLeft().getValue() >= a.getValue()) {
                    return false;
                }else if(a.getLeft().getLeft().isEmpty()){
                    return true;
                }
            }

            //

            // right is full and left is empty
            if (!a.getRight().isEmpty() && a.getLeft().isEmpty()) {

                System.out.println("RulesLeft check: Vals - " + a.getValue() + " " + a.getRight().getValue() + "  x = " + x);

                if (a.getRight().getValue() <= a.getValue() || a.getRight().getValue() >= x) {
                    return false;
                }else if(a.getRight().getRight().isEmpty()){
                    return true;
                }
            }
        }

        return leftRules(a.getLeft(), a.getValue()) && leftRules(a.getRight(), x);

    }

    // Exercise 6

    /**
     *
     * @param a the tree to be printed in Pre-order format
     */
	static void printDescending(Tree<Integer> a) {

	    if (a.isEmpty()){
	        return;
        }else {

	        printDescending(a.getRight());
            System.out.print(a.getValue() + " ");
	        printDescending(a.getLeft());

        }
	}

	// Exercise 7

    /**
     *
     * @param a tree of integer nodes
     * @return the maximum value in a tree
     */
	static int max(Tree<Integer> a) {

	    if(a.isEmpty()){
	        throw new IllegalArgumentException("The max value of an empty tree can not be computed");
        }
        System.out.println("max called");
	    if(a.getRight().isEmpty()){
	        return a.getValue();
        }else {
	        return max(a.getRight());
	    }
	}

    /**
     *
     * @param a tree of integer nodes
     * @return the smallest value in a tree
     */
    static int min(Tree<Integer> a){
        System.out.println("min called");
        if(a.getLeft().isEmpty()){
            return a.getValue();
        }else {
            return min(a.getLeft());
        }
    }

	// Exercise 8

    /**
     *
     * @param a A tree of integer nodes
     * @param x the node to be deleted
     * @return a new Tree with if found the node to be deleted x removed from the tree.
     */
	static Tree<Integer> delete(Tree<Integer> a, int x) {

	    if(a.isEmpty()){
	      // Cant find the item to be deleted
            return new Tree<>();


        } else if( x < a.getValue()){
	        // x is smaller a then go left
            return new Tree<>(a.getValue(), delete(a.getLeft(), x), a.getRight());


        }else if(x > a.getValue()){
            // x is larger than a then go right

            return new Tree<>(a.getValue(), a.getLeft(), delete(a.getRight(), x));
        }else {

	        if (a.getLeft().isEmpty() && !a.getRight().isEmpty()){

	            return new Tree<>(a.getRight().getValue(), a.getRight().getLeft(), a.getRight().getRight());

            }else if(a.getRight().isEmpty() && !a.getLeft().isEmpty()){

	            return new Tree<>(a.getLeft().getValue(), a.getLeft().getLeft(), a.getLeft().getRight());

            }else if(a.getLeft().isEmpty() && a.getRight().isEmpty()){

	            return new Tree<>();
            } else {

	            int max = max(a.getLeft());

                return new Tree<>(max, delete(a.getLeft(), max), a.getRight());
            }


        }

	}

	// Exercise 9

    /**
     *
     * @param a The input tree
     * @param <E> Generic type parameter
     * @return true if each node is balanced between -1,0,+1 with respect to the heights of there left and right branches
     */
	static <E> boolean isHeightBalanced(Tree<E> a) {

	    if (a.isEmpty()){
	        return true;
        }else {
            if (Math.abs(a.getRight().getHeight() - a.getLeft().getHeight()) > 1) {
                return false;
            }
        }

        return isHeightBalanced(a.getLeft()) && isHeightBalanced(a.getRight());

	}


	// Exercise 10

    /**
     *
     * @param a input tree
     * @param x int value to be inserted into the tree a
     * @return a new binary tree with value to be inserted added in the appropriate position. The
     * Tree will also ensure that all nodes have are balanced between -1, 0, 1.
     */
    static Tree<Integer> insertHB(Tree<Integer> a, int x) {
        return insert(x, a);
    }

    /**
     *
     * @param a input tree
     * @param x int value to be deleted into the tree a
     * @return a new binary tree with value to be deleted removed in the appropriate position. The
     * Tree will also ensure that all nodes have are balanced between -1, 0, 1.
     */
    static Tree<Integer> deleteHB(Tree<Integer> a, int x) {
        return delete2(a, x);
    }

    /**
     *
     * @param x int to be inserted into the binary tree
     * @param a The input binary tree
     * @return a new tree with x added to its appropriate place within the binary tree
     */
    static Tree<Integer> insert(int x, Tree<Integer> a) {
        if (a.isEmpty())
            return new Tree<>(x);
        else if (x < a.getValue())
            return avlGenerator(new Tree<>(a.getValue(), insert(x, a.getLeft()), a.getRight()));
        else
            return avlGenerator(new Tree<>(a.getValue(), a.getLeft(), insert(x, a.getRight())));

    }

    /**
     * Copy of the deleteFunction to allow for separate testing - this will balance trees to produce avl trees
     * @param a A tree of integer nodes
     * @param x the node to be deleted
     * @return a new Tree which if found the node to be deleted x removed from the tree.
     */
    static Tree<Integer> delete2(Tree<Integer> a, int x) {

        if (a.isEmpty()) {
            // Cant find the item to be deleted
            return avlGenerator(new Tree<>());


        } else if (x < a.getValue()) {
            // x is smaller a then go left
            return avlGenerator(new Tree<>(a.getValue(), delete2(a.getLeft(), x), a.getRight()));


        } else if (x > a.getValue()) {
            // x is larger than a then go right

            return avlGenerator(new Tree<>(a.getValue(), a.getLeft(), delete2(a.getRight(), x)));
        } else {

            if (a.getLeft().isEmpty() && !a.getRight().isEmpty()) {

                return avlGenerator(new Tree<>(a.getRight().getValue(), a.getRight().getLeft(), a.getRight().getRight()));

            } else if (a.getRight().isEmpty() && !a.getLeft().isEmpty()) {

                return avlGenerator(new Tree<>(a.getLeft().getValue(), a.getLeft().getLeft(), a.getLeft().getRight()));

            } else if (a.getLeft().isEmpty() && a.getRight().isEmpty()) {

                return avlGenerator(new Tree<>());
            } else {

                int max = max(a.getLeft());
                return avlGenerator(new Tree<>(max, delete2(a.getLeft(), max), a.getRight()));
            }


        }
    }

    static int getBalance(Tree<Integer> a) {
        if (a.isEmpty() || (a.getLeft().isEmpty() && a.getRight().isEmpty())) {
            return 0;
        } else {
            return a.getRight().getHeight() - a.getLeft().getHeight();
        }
    }

    static Tree<Integer> leftRotation(Tree<Integer> a) {
        return new Tree<>(a.getRight().getValue(), new Tree<>(a.getValue(), a.getLeft(), a.getRight().getLeft()),
                a.getRight().getRight());
    }

    static Tree<Integer> rightRotation(Tree<Integer> a) {
        return new Tree<>(a.getLeft().getValue(), a.getLeft().getLeft(),
                new Tree<>(a.getValue(), a.getLeft().getRight(), a.getRight()));
    }

    /**
     * Determines what kind of rotaions are required to produce and AVL tree.
     * @param a input  tree
     * @return new AVL balanced binary tree
     */
    static Tree<Integer> avlGenerator(Tree<Integer> a) {
        if (getBalance(a) > 1) { // current node is right heavy
            if (getBalance(a.getRight()) < 0)  { // if the right node is left heavy
                //
                return leftRotation(new Tree<>(a.getValue(), a.getLeft(), rightRotation(a.getRight())));
            } else {
                // Both nodes are right heavy and ths require a single left rotation
                return leftRotation((new Tree<>(a.getValue(), a.getLeft(), a.getRight())));
            }
        }else if (getBalance(a) < -1) { // if the current node/root is left heavy
            if (getBalance(a.getLeft()) > 0) { // if the left of the root node if right heavy
                //
                return rightRotation(new Tree<>(a.getValue(), leftRotation(a.getLeft()), a.getRight()));
            } else {

                return rightRotation((new Tree<>(a.getValue(), a.getLeft(), a.getRight())));
            }
        }else {
            return a;
        }
    }

	// ========================== HELPER FUNCTIONS ================================
	static <E> List<E> append(List<E> listA, List<E>listB){

		if (listA.isEmpty()){
			return listB;
		}else {
			return new List<>(listA.getHead(), append(listA.getTail(), listB));
		}
	}

	static <E> List<E> addToEnd(List<E> list, E itemToAdd){

	    return append(list, new List<>(itemToAdd, new List<>()));
    }


}

