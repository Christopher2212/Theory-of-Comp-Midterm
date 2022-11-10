public class IntMatrixMath {
    public static void main(String[] args) {
        // y = mx + b
        // Init the equation
        double[] y = new double[] {5.0, 12.9, 2.0};
//        double[] y = new double[] {5.0, 12.9, 2.0,2.4};

        //          0         1         2
        // m = {{a1,b1,c1},{a2,b2,c2},{a3,b3,c3}}
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
        seqMatix(tempM, y, m);


        // Find value X in the matrix
        double[] valueX = getValueX(tempM, y);


        System.out.println();
        System.out.println("X values:");
        for (double value : valueX) {
            System.out.println(value);
        }


        int[][] values = new int[(int)Math.pow(2, valueX.length)][valueX.length];
        double squareLength = Math.pow(2, valueX.length);
        int vent = (int)squareLength/2;
        int vent2 = 1;
        for(int v = 0; v < valueX.length; v++){
            int valueV = 0;
            for(int tempY = 0; tempY < vent2; tempY++){
                for(int x = 0; x < vent; x++){
                    values[valueV + x][v] = (int) valueX[v];
                }
                valueV = getValueV((int) squareLength, vent2, v, valueV, tempY);
            }

            valueV = 0;
            for(int tempY = 0; tempY < vent2; tempY++){
                for(int x = 0; x < vent; x++){
                    if(valueX[v] < 0){
                        values[x + vent + valueV][v] = (int) valueX[v] - 1;
                    } else {
                        values[x + vent + valueV][v] = (int) valueX[v] + 1;
                    }
                }
                valueV = getValueV((int) squareLength, vent2, v, valueV, tempY);
            }
            vent2 = (int)squareLength/vent;
            vent = vent/2;
        }


        double[][] C_holder =  new double[values.length][values[0].length];
        for(int i = 0; i < values.length; i++){
            for(int j = 0; j < c.length; j++){
                C_holder[i][j] = (c[j] * values[i][j]);
            }
        }

        double[] maxArray = C_holder[0];
        double[] minArray = C_holder[0];
        for (double[] doubles : C_holder) {
            for (int j = 0; j < C_holder[0].length; j++) {
                if (doubles[j] > maxArray[j]) {
                    maxArray = doubles;
                }
                if (doubles[j] < minArray[j]) {
                    minArray = doubles;
                }
            }

        }

        System.out.println();

        System.out.println();
        System.out.println("cTx values of maximize:");
        for(int i = 0; i < minArray.length; i++){
            System.out.println(maxArray[i]);
        }


    }

    private static int getValueV(int squareLength, int vent2, int v, int valueV, int tempY) {
        if(v > 0){
            if(vent2 == 1){
                valueV = (squareLength /2);
            } else{
                valueV = (squareLength /vent2);
            }

            if(tempY > 0){
                valueV = valueV * (tempY + 1);
            }
        }
        return valueV;
    }
//
//
//
//
//    // used to inject the column of data into our M matrix
//    public static double[][] injectMatrixColumn(double[][] m, double[] n, int cNum){
//        double[][] temp = Arrays.stream(m).map(double[]::clone).toArray(double[][]::new);
//        for(int i = 0; i < 3; i++){
//            temp[i][cNum] = n[i];
//        }
//        return temp;
//    }
//
//    // Reduces a 3x3 matrix into a single digit
//    public static double reduceMatrix(double[][] m){
//        double a1 = m[0][0];
//        double b1 = m[0][1];
//        double c1 = m[0][2];
//        // Set the 2x2 matrices
//        double[][] m1 = {{m[1][1],m[1][2]},{m[2][1],m[2][2]}};
//        double[][] m2 ={{m[1][0],m[1][2]},{m[2][0],m[2][2]}};
//        double[][] m3 = {{m[1][0],m[1][1]},{m[2][0],m[2][1]}};
//
//        // D = (a1 * m1) - (b1 * m2) + (c1 * m3)
//
//        return calcChunk(m1, a1) - calcChunk(m2, b1) + calcChunk(m3, c1);
//    }
//
//
//    // Takes in a 2x2 matrix and a multiplier and calculates the chunk
//    public static double calcChunk(double[][] sm, double n){
//        return n * ((sm[0][0] * sm[1][1])-(sm[0][1] * sm[1][0]));
//    }
//
//    // A helper method so that we can see what is in our matrices
//    public static void printMatrix(double[][] m){
//        for(int i=0; i<m.length; i++) {
//            // inner loop for column
//            for(int j=0; j<m[0].length; j++) {
//                System.out.print(m[i][j] + " ");
//            }
//            System.out.println(); // new line
//        }
//    }






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
