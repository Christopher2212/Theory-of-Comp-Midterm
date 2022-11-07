//References:
// https://semath.info/src/inverse-cofactor-ex4.html
// https://www.youtube.com/watch?v=h4Za9W2H6fk&ab_channel=ThinkwellVids
// https://math.libretexts.org/Bookshelves/Algebra/Map%3A_College_Algebra_(OpenStax)/07%3A_Systems_of_Equations_and_Inequalities/708%3A_Solving_Systems_with_Inverses


public class Main {

    public static void main(String[] args) {
        // y = mx + b
        // Init the equation
        double[] y = new double[] {5.0, 12.9, 2.0};
//        double[] y = new double[] {5.0, 12.9, 2.0,2.4};

        //          0         1         2
        // m = {{a1.b1,c1},{a2,b2,c2},{a3,b3,c3}}
        double[][] m = {{3.2,8.7,5.9},
                {2.4,3.1,1.1},
                {9.7, 6.1, 0.3}};
//        double[][] m = {{-1.0,2.0,-1.0},
//                {3.0,-7.0,-2.0},
//                {2.0,2.0,1.0}};
//        double[][] m = {{3.2,8.7,5.9,2.2},
//                {2.4,3.1,1.1,2.3},
//                {2.4,3.1,1.1,4.4},
//                {9.7,6.1,0.3,7.6}};
        double[] b = {3.7,3.8,1};
//        double[] b = {3.7,3.8,1,2.7};

        // Init c
        double[] c = {8.2,9.7,1.1};
//        double[] c = {8.2,9.7,1.1,1.4};

        // First we need to change the equation from y = mx + b to y = mx
        // Subtract b from the equation
        for(int i = 0; i < y.length; i++){
            y[i] -= b[i];
        }

        // We now need to calculate all any type of nxn matrix

//        y = new double[]{9.0, -20.0, 2.0, 3.0};

        // Creates the temp Matrix
        double[][] tempM = getTempMatrixA(m, y);

        // Goes through the sequence of the Matrix
        seqMatix(tempM, y, m);

        // Find value X in the matrix
        double[] valueX = getValueX(tempM, y);


        System.out.println();
        double[] cTx = new double[c.length];
        System.out.println("X values:");
        for(int i = 0; i < valueX.length; i++){
            System.out.println(valueX[i]);
            cTx[i] = c[i] * valueX[i];
        }

        System.out.println();
        System.out.println("cTx values:");
        for(int i = 0; i < valueX.length; i++){
            System.out.println(cTx[i]);
        }
    }

    public static double[][] getTempMatrixA(double[][] m, double[] y){
        double[][] tempM = new double[m[0].length][m.length];
        for(int i = 0; i < m.length; i++){

            for(int j = 0; j < m.length; j++){
                if(i == 0){
                    tempM[i][j] = m[i][j];
                } else{
                    tempM[i][j] = m[i][j];
                    double temp = m[i][0];
                    y[i] = (y[0] * m[i][0]) + y[i];
                    for(int z = 0; z < m.length; z++){
                        tempM[i][z] = (tempM[0][z] * temp) + m[i][z];
                    }
                    j = m.length;
                }
            }
        }
        return tempM;
    }

    private static void seqMatix(double[][] tempM, double[] y, double[][] m){
        for(int i = 0; i < m.length; i++){
            double downZ;
            double upZ;
            for(int j = m.length - 1; j >= i; j--){
                while(tempM[j][i] == 0){
                    j--;
                }
                if(j > i) {
                    downZ = tempM[j - 1][i];
                    upZ = tempM[j][i];
                    double[] upMatrix = new double[m.length + 1];
                    double[] downMatrix = new double[m.length + 1];

                    if(tempM[j - 1][i] == 0.0){
                        double temp;
                        for(int z = 0; z < m.length; z++) {
                            temp = tempM[j][z];
                            tempM[j][z] = tempM[j - 1][z];
                            tempM[j - 1][z] = temp;
                        }
                        temp = y[j - 1];
                        y[j - 1] = y[j];
                        y[j] = temp;

                    }
                    else {
                        for (int z = 0; z < m.length; z++) {
                            double temp1 = upZ * tempM[j - 1][z];
                            double temp2 = downZ * tempM[j][z];
                            upMatrix[z] = temp1;
                            downMatrix[z] = temp2;
                        }
                        upMatrix[m.length] = upZ * y[j - 1];
                        downMatrix[m.length] = downZ * y[j];

                        if (downMatrix[i] > 0 || upMatrix[i] == downMatrix[i]) {
                            for (int z = 0; z < m.length + 1; z++) {
                                downMatrix[z] = downMatrix[z] * -1;
                            }
                        }

                        for (int z = 0; z < m.length; z++) {
                            tempM[j][z] = upMatrix[z] + downMatrix[z];
                        }
                        y[j] = upMatrix[m.length] + downMatrix[m.length];
                    }

                }
            }
        }
    }

    public static double[] getValueX(double[][] tempM, double[] y){
        double[] valueX = new double[tempM.length];
        for(int i = tempM.length - 1; i >= 0; i--){
            double[] equa = new double[tempM.length + 1];
            // Copies the array from the tempM
            System.arraycopy(tempM[i], 0, equa, 0, tempM.length);
            equa[tempM.length] = y[i];
            double total = 0;
            for(int j = i + 1; j < tempM.length; j++){
                if(valueX[j] != 0.0) {
                    equa[j] = equa[j] * valueX[j];
                }
                total = total + equa[j];
            }
            equa[tempM.length] = (equa[tempM.length] - total)/equa[i];

            valueX[i] = equa[tempM.length];
        }
        return valueX;
    }

}
