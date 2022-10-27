import java.util.Arrays;

public class IntMatrixMath {
    public static void main(String[] args) {
        // y = mx + b
        // Init the equation
        int[] y = new int[]{5, 13, 2};

        /*for(int i = 0; i < 3; i++){
            System.out.println(y[i]);
        }*/
        //          0         1         2
        // m = {{a1.b1,c1},{a2,b2,c2},{a3,b3,c3}}
        int[][] m = {{3,9,6},{2,3,1},{10,6,1}};
        int[] b = {4,4,1};

        // Init c
        int[] c = {8,10,1};

        // Going to solve this via Cramer's Rule

        // First we need to change the equation from y = mx + b to y = mx
        // Subtract b from the equation
        for(int i = 0; i < 3; i++){
            y[i] -= b[i];
        }
        /*for(int i = 0; i < 3; i++){
            System.out.println(y[i]);
        }*/

        // x1 = Dx/D where D is our m matrix
        // x2 = Dy/D
        // x3 = Dz/D

        // Solving Dx, Dy, Dz
        // this

        int[][] Dx = injectMatrixColumn(m,y,0);
        System.out.println("Dx:");
        printMatrix(Dx);


        int[][] Dy = injectMatrixColumn(m,y,1);
        System.out.println("Dy:");
        printMatrix(Dy);

        int[][] Dz = injectMatrixColumn(m,y,2);

        int x1 = reduceMatrix(Dx) / reduceMatrix(m);
        int x2 = reduceMatrix(Dy) / reduceMatrix(m);
        int x3 = reduceMatrix(Dz) / reduceMatrix(m);

        System.out.println(x1 + " " + x2 + " " + x3);

        //Now that we have the x values we can multiply it by our c values and add them together, getting the final results
        //Figure out the ranges that we can slide all the x's around

        int x1rev  = x1 *-1;
        int x2rev  = x2 *-1;
        int x3rev  = x3 *-1;

        int[] rng1 = new int[2];
        int[] rng2 = new int[2];
        int[] rng3 = new int[2];

        if(x1 > x1rev){
            rng1[0] = x1rev;
            rng1[1] = x1;
        }else{
            rng1[0] = x1;
            rng1[1] = x1rev;
        }
        if(x2 > x2rev){
            rng2[0] = x2rev;
            rng2[1] = x2;
        }else{
            rng2[0] = x2;
            rng2[1] = x2rev;
        }if(x3 > x3rev){
            rng3[0] = x3rev;
            rng3[1] = x3;
        }else{
            rng3[0] = x3;
            rng3[1] = x3rev;
        }
        int max = -10;

        for(int i = rng1[0]; i <= rng1[1]; i++){
            for(int j = rng2[0]; j <= rng2[1]; j++){
                for(int k = rng3[0]; k <= rng3[1]; k++){
                    int temp = i*c[0] + j*c[1] + k*c[2];
                    if(temp > max ){
                        max = temp;
                    }
                }
            }
            System.out.println(max);
        }
        System.out.println(max);

    }
    // used to inject the column of data into our M matrix
    public static int[][] injectMatrixColumn(int[][] m, int[] n, int cNum){
        int[][] temp = Arrays.stream(m).map(int[]::clone).toArray(int[][]::new);
        for(int i = 0; i < 3; i++){
            temp[i][cNum] = n[i];
        }
        return temp;
    }

    // Reduces a 3x3 matrix into a single digit
    public static int reduceMatrix(int[][] m){
        int a1 = m[0][0];
        int b1 = m[0][1];
        int c1 = m[0][2];
        // Set the 2x2 matrices
        int[][] m1 = {{m[1][1],m[1][2]},{m[2][1],m[2][2]}};
        int[][] m2 ={{m[1][0],m[1][2]},{m[2][0],m[2][2]}};
        int[][] m3 = {{m[1][0],m[1][1]},{m[2][0],m[2][1]}};

        // D = (a1 * m1) - (b1 * m2) + (c1 * m3)

        return calcChunk(m1, a1) - calcChunk(m2, b1) + calcChunk(m3, c1);
    }


    // Takes in a 2x2 matrix and a multiplier and calculates the chunk
    public static int calcChunk(int[][] sm, int n){
        return n * ((sm[0][0] * sm[1][1])-(sm[0][1] * sm[1][0]));
    }

    // A helper method so that we can see what is in our matrices
    public static void printMatrix(int[][] m){
        for(int i=0; i<m.length; i++) {
            // inner loop for column
            for(int j=0; j<m[0].length; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println(); // new line
        }
    }
}
