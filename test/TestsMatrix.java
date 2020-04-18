import org.examle.Matrix;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TestsMatrix {

    private static double [][] matrixFirst;
    private static double [][] matrixSecond;
    private static double [][] matrixThird;
    private static double [][] result;
    private static double [][] multiplyMatrix;
    private static final double DELTA = 1e-15;

    @BeforeAll
    public static void setMatrix () {


        int size = 3;
        matrixFirst = new double[size][size];
        matrixSecond = new double[size][size];
        matrixThird = new double[size-1][size-1];
        result = new double[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                matrixFirst[row][col] = row + col;
                matrixSecond[row][col] = row + col + 5;
                if (row != size -1 && col != size-1)
                matrixThird[row][col] = row + col + 10;
            }
        }
        multiplyMatrix = new double[size][size];

        multiplyMatrix [0][0] = 20.0;
        multiplyMatrix [0][1] = 23.0;
        multiplyMatrix [0][2] = 26.0;
        multiplyMatrix [1][0] = 38.0;
        multiplyMatrix [1][1] = 44.0;
        multiplyMatrix [1][2] = 50.0;
        multiplyMatrix [2][0] = 56.0;
        multiplyMatrix [2][1] = 65.0;
        multiplyMatrix [2][2] = 74.0;
    }


    @Test
    public void addMatrix() {

        for (int row = 0; row < matrixFirst.length; row++) {
            for (int col = 0; col < matrixFirst[0].length; col++) {
                result[row][col] = matrixFirst[row][col] + matrixSecond[row][col];
            }
        }

        double [][] actual = Matrix.add(matrixFirst, matrixSecond);

        for (int row = 0; row < matrixFirst.length; row++) {
            Assert.assertArrayEquals(result[row], actual[row], DELTA);
        }
    }

    @Test
    public void cantAddMatrixDifferentSize() throws IllegalArgumentException {
        try {
            Matrix.add(matrixFirst, matrixThird);
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void subMatrix() {

        for (int row = 0; row < matrixFirst.length; row++) {
            for (int col = 0; col < matrixFirst[0].length; col++) {
                result[row][col] = matrixFirst[row][col] - matrixSecond[row][col];
            }
        }

        double [][] actual = Matrix.subtract(matrixFirst, matrixSecond);

        for (int row = 0; row < matrixFirst.length; row++) {
            Assert.assertArrayEquals(result[row], actual[row], DELTA);
        }
    }

    @Test
    public void cantSubMatrixDifferentSize() throws IllegalArgumentException {
        try {
            Matrix.subtract(matrixFirst, matrixThird);
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void multiplyNumberMatrix() {

        double [][] exp = new double [matrixFirst.length][matrixFirst[0].length];
        double Number = 5.7;

        for (int row = 0; row < matrixFirst.length; row++) {
            for (int col = 0; col < matrixFirst[0].length; col++) {
                exp[row][col] = (row + col) * Number;
            }
        }

        double [][] actual = Matrix.multiplyNumber(matrixFirst, Number);

        for (int row = 0; row < matrixFirst.length; row++) {
            Assert.assertArrayEquals(exp[row], actual[row], DELTA);
        }
    }

    @Test
    public void multiplyMatrix() {

        result = Matrix.multiply(matrixFirst, matrixSecond);

        for (int row = 0; row < matrixFirst.length; row++) {
            Assert.assertArrayEquals(result[row], multiplyMatrix[row], DELTA);
        }
    }

    @Test
    public void cantMultiplyMatrix() throws IllegalArgumentException {
        try {
            result = Matrix.multiply(matrixFirst, matrixThird);
        } catch (IllegalArgumentException e) {
        }
    }


    @ParameterizedTest(name = "{0} = {1} && {2} = {3}")
    @CsvSource({
            "1, 1, 2, 2",
            "5, 5, 3, 3",
            "3, 3, 3, 3"
    })
    public void difSizeParameterizedTest(int s1, int s2, int s3, int s4) {
        Assert.assertFalse(Matrix.IsDifSize(s1,s2,s3,s4));
    }

    @ParameterizedTest(name = "{0} != {1} || {2} != {3}")
    @CsvSource({
            "1, 2, 2, 2",
            "5, 5, 3, 1",
            "3, 1, 2, 3"
    })
    public void sameSizeParameterizedTest(int s1, int s2, int s3, int s4) {
        Assert.assertTrue(Matrix.IsDifSize(s1,s2,s3,s4));
    }

    @ParameterizedTest
    @CsvSource({
            "1",
            "2",
            "5"
    })
    public void multiplyNumberParameterizedTest(int number) {

        double [][] exp = new double [matrixFirst.length][matrixFirst[0].length];

        for (int row = 0; row < matrixFirst.length; row++) {
            for (int col = 0; col < matrixFirst[0].length; col++) {
                exp[row][col] = (row + col) * number;
            }
        }

        double [][] actual = Matrix.multiplyNumber(matrixFirst, number);
        for (int row = 0; row < matrixFirst.length; row++) {
            Assert.assertArrayEquals(exp[row], actual[row], DELTA);
        }
    }

    @Test
    void addMatrixFromFile() {
        double[][] matrix1 = Matrix.getMatrixFromFle("resources/matrix1.txt", 3, 3);
        double[][] matrix2 = Matrix.getMatrixFromFle("resources/matrix2.txt", 3, 3);
        double[][] sumMatrix = Matrix.getMatrixFromFle("resources/sumMatrix.txt", 3, 3);
        double [][] actual = Matrix.add(matrix1, matrix2);

        for (int row = 0; row < matrixFirst.length; row++) {
            Assert.assertArrayEquals(sumMatrix[row],
                    actual[row],
                    DELTA);
        }
    }

    @Test
    void subMatrixFromFile() {
        double[][] matrix1 = Matrix.getMatrixFromFle("resources/matrix1.txt", 3, 3);
        double[][] matrix2 = Matrix.getMatrixFromFle("resources/matrix2.txt", 3, 3);
        double[][] subMatrix = Matrix.getMatrixFromFle("resources/subMatrix.txt", 3, 3);
        double [][] actual = Matrix.subtract(matrix1, matrix2);

        for (int row = 0; row < matrixFirst.length; row++) {
            Assert.assertArrayEquals(subMatrix[row], actual[row], DELTA);
        }
    }

    @Test
    void multiplyMatrixFromFile() {
        double[][] matrix1 = Matrix.getMatrixFromFle("resources/matrix1.txt", 3, 3);
        double[][] matrix2 = Matrix.getMatrixFromFle("resources/matrix2.txt", 3, 3);
        double[][] mulMatrix = Matrix.getMatrixFromFle("resources/multiplyMatrix.txt", 3, 3);
        double[][] actual = Matrix.multiply(matrix1, matrix2);

        for (int row = 0; row < matrixFirst.length; row++) {
            Assert.assertArrayEquals(mulMatrix[row], actual[row], DELTA);
        }
    }

    @Test
    void multiplyNumberMatrixFromFile() {

        double[][] matrix2 = Matrix.getMatrixFromFle("resources/matrix2.txt", 3, 3);
        double[][] multNumbMatrix = Matrix.getMatrixFromFle("resources/multiplyNumberMatrix.txt", 3, 3);
        double[][] actual = Matrix.multiplyNumber(matrix2, 3);

        for (int row = 0; row < matrixFirst.length; row++) {
            Assert.assertArrayEquals(multNumbMatrix[row], actual[row], DELTA);
        }
    }

}
