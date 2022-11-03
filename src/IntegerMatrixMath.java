public class IntegerMatrixMath {
    public static void main(String[] args) {
        // y = mx + b
        // Init the equation
        double[] y = new double[] {5.0, 12.0, 2.0,2.0};

        //          0         1         2
        // m = {{a1.b1,c1},{a2,b2,c2},{a3,b3,c3}}
        double[][] m = {{3.0,8.0,5.0,2.0},{2.0,3.0,1.0,2.0},{2.0,3.0,1.0,4.0},{9.0,6.0,1,7.0}};
        double[] b = {3.0,3.0,1,2.0};

        // Init c
        double[] c = {8.0,9.0,1.0,1.0};

        // First we need to change the equation from y = mx + b to y = mx
        // Subtract b from the equation
        for(int i = 0; i < y.length; i++){
            y[i] -= b[i];
        }

        // We now need to calculate all any type of nxn matrix

        double[][] inverseMatrix = getInverseMatrix(m);
        System.out.println("The inverse Matrix:");
        printMatrix(inverseMatrix);
        // Collapse the nxn matrix w/ the nx1 matrix

        double[] xMatrix = collapseMatrix(inverseMatrix,y);

        System.out.println("X values:");
        for(int i = 0; i < xMatrix.length; i++){
            System.out.println(xMatrix[i]);
        }

        // With our x's we can go through their combinations of roof and floor
        double[][] combinations = new double[(int)Math.pow(2,xMatrix.length)][];

        //getCombinations(xMatrix, combinations,0, xMatrix.length);

        printMatrix(combinations);
    }

    //The inverse of a matrix is the inverse of the determinate multiplied by the adjugate
    public static double[][] getInverseMatrix(double[][] A){
        //getting the determinate of the matrix
        double determinate  = getDeterminate(A, A.length);
        System.out.println("The determinate: " + determinate );


        if(determinate == 0){
            try {
                throw new Exception("Determinate cannot be zero!");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        //Get the identity of the matrix
        double[][] adjugate = getAdjugateMatrix(A);

        //printMatrix(adjugate);
        //get the transpose of the adjugate
        adjugate = transposeMatrix(adjugate);

        //printMatrix(adjugate);
        // multiply the adjugate with the inverse of the determinate
        for(int i = 0; i < adjugate.length; i++){
            for(int j = 0; j < adjugate.length; j++){
                adjugate[i][j] *= 1/determinate;
            }
        }
        // returns the inverse
        return adjugate;
    }


    // Getting the determinate of a matrix calls for a recursive function
    public static double getDeterminate(double[][] A, int size){
        double res;
        if(size == 1){ // if matrix is 1 return that cell
            res = A[0][0];
        }else if(size == 2){ //if matrix is 2 do computation
            res = A[0][0]*A[1][1] - A[1][0]*A[0][1];
        }else{ // if not 1x1 or 2x2 you must recursively boil down to that
            res = 0;
            for(int i = 0; i < size; i++){
                double[][]m = getSubmatrix (A, size, i); // getting sub-matrix of A by excluding cols and rows
                res += Math.pow(-1.0, 2.0+i) * A[0][i] * getDeterminate(m, size-1);
            }
        }
        return res;
    }

    //Getting sub-matrix given an 2D array and the inclusive rows and exclusive cols
    //Has a fatal flaw, basically it works if we are descaling the matrix size via the rows at 0
    //Good for the init determinate but is poor for the children's determinates
    //Also a helper method for getDeterminate
    public static double[][] getSubmatrix(double[][] A, int rowInc, int colExc){
        double[][] result = new double[A.length-1][];
        for(int k = 0; k < (rowInc -1); k++){
            result[k] = new double[rowInc-1];
        }
        for(int i = 1; i < rowInc; i++){
            int j2 = 0;
            for(int j = 0; j < rowInc; j++){
                if(j == colExc){
                    continue;
                }
                result[i-1][j2] = A[i][j];
                j2++;
            }
        }
        return result;
    }

    // This is good for getting the children determinates when getting the identity matrix
    public static double[][] getSubmatrixRevamp(double[][] A, int rowExc, int colExc){

        double[][] result = new double[A.length-1][];

        for(int k = 0; k < (A.length-1); k++){
            result[k] = new double[A.length-1];
        }

        int i2 = 0;

        for(int i = 0; i < A.length; i++){
            int j2 = 0;
            if(i == rowExc){
                continue;
            }
            for(int j = 0; j < A.length; j++){
                if(j == colExc){
                    continue;
                }
                result[i2][j2] = A[i][j];
                j2++;
            }
            i2++;
        }
        return result;
    }

    // Creates the identity matrix that we will use for inverting a matrix
    // every cell is the determinate of the sub-matrix that is created through excluding the cols and rows-
    // of where that cell is...
    public static double[][] getAdjugateMatrix(double[][] A){
        double[][] res = new double[A.length][];

        for(int k = 0; k < (A.length); k++){
            res[k] = new double[A.length];
        }

        for(int i =0; i < A.length; i++){
            for(int j =0; j < A.length; j++){
                // inverting -1 and 1 multiplied with the determinate of the sub-matrix
                double[][] subMatrix = getSubmatrixRevamp(A,i,j);
                res[i][j] = Math.pow(-1.0, i+j) * getDeterminate(subMatrix,subMatrix.length);
            }
        }
        return res;
    }

    // This reverses the cols of a matrix and places them as rows
    // This flips everything around the diagonal
    public static double[][] transposeMatrix(double[][] A){
        double res[][] = new double[A.length][];

        for(int k = 0; k < (A.length); k++){
            res[k] = new double[A.length];
        }

        for(int i = 0; i < A.length; i++){
            for(int j = 0; j < A.length; j++){
                res[j][i] = A[i][j];
            }
        }
        return res;
    }

    public static double[] collapseMatrix(double[][] A, double[] y){

        double[] res = new double[A.length];
        for(int i = 0; i < A.length; i++){
            double coll = 0;
            for(int j = 0; j < A.length; j++){
                coll += y[j] * A[i][j];
            }
            res[i] = coll;
        }
        return res;
    }

    /*public static void getCombinations(double[] x, double[][] A, int i, int n){
        // this code is disgusting and I hate it BUT it works so bite me
        // there is a bug where 0 is overwritten
        if(i == n){
            if(A.length == 0){
                A[0] = x;
            }else{
                A[A.length] = x;
            }
        }else{
            int t = i+1;
            // get the combinations where the ith item in x is floored
            x[i] = Math.floor(x[i]);
            getCombinations(x,A,t,n);

            //get the combinations where the ith item in x is roofed
            x[i] = Math.ceil(x[i]);
            getCombinations(x,A,t,n);
        }
    }*/

    // A helper method so that we can see what is in our matrices
    public static void printMatrix(double[][] m){

        for(int i=0; i<m.length; i++) {
            // inner loop for column
            for(int j=0; j<m[0].length; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println(); // new line
        }
    }
}
