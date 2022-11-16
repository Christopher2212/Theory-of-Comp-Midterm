public class Main {

    public static void main(String[] args) {
        // y = mx + b
        // Init the equation
        double[] y = new double[] {5.0, 12.9, 2.0};
//        double[] y = new double[] {5.0, 12.9, 2.0,2.4};

        //          0         1         2
        // m = {{a1,b1,c1},{a2,b2,c2},{a3,b3,c3}}
        // This can go by nxn matrix
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

        // Creates the temp Matrix
        double[][] tempM = getTempMatrixA(m, y);


        // Goes through the sequence of the Matrix
        seqMatRec(tempM, y, m, 0, 0);


        // Find value X in the matrix
        double[] valueX = getValueX(tempM, y);

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

    private static void seqMatRec(double[][] tempM, double[] y, double[][] m, int i, int j) {
        if(i == m.length) {
            return;
        }

        double downZ;
        double upZ;

        for (j = m.length - 1; j >= i; j--) {
            while (tempM[j][i] == 0) {
                j--;
            }

            if (j > i) {
                downZ = tempM[j - 1][i];
                upZ = tempM[j][i];
                double[] upMat = new double[m.length + 1];
                double[] downMat = new double[m.length + 1];
                if (tempM[j - 1][i] == 0) {
                    double temp;

                    for (int z = 0; z < m.length; z++) {
                        temp = tempM[j][z];
                        tempM[j][z] = tempM[j - 1][z];
                        tempM[j - 1][z] = temp;
                    }

                    temp = y[j - 1];
                    y[j - 1] = y[j];
                    y[j] = temp;
                } else {
                    for (int z = 0; z < m.length; z++) {
                        double temp1 = upZ * tempM[j - 1][z];
                        double temp2 = downZ * tempM[j][z];
                        upMat[z] = temp1;
                        downMat[z] = temp2;
                    }

                    upMat[m.length] = upZ * y[j - 1];
                    downMat[m.length] = downZ * y[j];

                    if (downMat[i] > 0 || upMat[i] == downMat[i]) {
                        for (int z = 0; z < m.length + 1; z++) {
                            downMat[z] = downMat[z] * -1;
                        }
                    }

                    for (int z = 0; z < m.length; z++) {
                        tempM[j][z] = upMat[z] + downMat[z];
                    }

                    y[j] = upMat[m.length] + downMat[m.length];
                }
            }
        }

        //if j is greater than or eqaul to i, increment j
        if( j >= i) {
            j--;
            // call self with j - 1
        }
        seqMatRec(tempM, y, m, i + 1, j);
    }

}
