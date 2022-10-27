import java.util.Arrays;
public class Main{


    public static void main(String[] args) {

        // y = mx + b
        // Init the equation
        double[] y = new double[] {5.0, 12.9, 2.0};

        /*for(int i = 0; i < 3; i++){
            System.out.println(y[i]);
        }*/
        //          0         1         2
        // m = {{a1.b1,c1},{a2,b2,c2},{a3,b3,c3}}
        double[][] m = {{3.2,8.7,5.9},{2.4,3.1,1.1},{9.7,6.1,0.3}};
        double[] b = {3.7,3.8,1};

        // Init c
        double[] c = {8.2,9.7,1.1};

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

        double[][] Dx = injectMatrixColumn(m,y,0);
        System.out.println("Dx:");
        printMatrix(Dx);


        double[][] Dy = injectMatrixColumn(m,y,1);
        System.out.println("Dy:");
        printMatrix(Dy);

        double[][] Dz = injectMatrixColumn(m,y,2);

        double x1 = reduceMatrix(Dx) / reduceMatrix(m);
        double x2 = reduceMatrix(Dy) / reduceMatrix(m);
        double x3 = reduceMatrix(Dz) / reduceMatrix(m);

        System.out.println(x1 + " " + x2 + " " + x3);

        //Now that we have the x values we can multiply it by our c values and add them together, getting the final results
        System.out.println(x1*c[0] + x2*c[1] + x3*c[2]);
    }

    // used to inject the column of data into our M matrix
    public static double[][] injectMatrixColumn(double[][] m, double[] n, int cNum){
        double[][] temp = Arrays.stream(m).map(double[]::clone).toArray(double[][]::new);
        for(int i = 0; i < 3; i++){
            temp[i][cNum] = n[i];
        }
        return temp;
    }

    // Reduces a 3x3 matrix into a single digit
    public static double reduceMatrix(double[][] m){
        double a1 = m[0][0];
        double b1 = m[0][1];
        double c1 = m[0][2];
        // Set the 2x2 matrices
        double[][] m1 = {{m[1][1],m[1][2]},{m[2][1],m[2][2]}};
        double[][] m2 ={{m[1][0],m[1][2]},{m[2][0],m[2][2]}};
        double[][] m3 = {{m[1][0],m[1][1]},{m[2][0],m[2][1]}};

        // D = (a1 * m1) - (b1 * m2) + (c1 * m3)

        return calcChunk(m1, a1) - calcChunk(m2, b1) + calcChunk(m3, c1);
    }


    // Takes in a 2x2 matrix and a multiplier and calculates the chunk
    public static double calcChunk(double[][] sm, double n){
        return n * ((sm[0][0] * sm[1][1])-(sm[0][1] * sm[1][0]));
    }

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