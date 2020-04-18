package org.examle;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Matrix {

    public static double[][] multiply(double[][] firstMatrix, double[][] secondMatrix) {
        if (firstMatrix[0].length != secondMatrix.length) {
            throw new IllegalArgumentException();
        }

        double[][] result = new double[firstMatrix.length][secondMatrix[0].length];

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = multiplyMatricesCell(firstMatrix, secondMatrix, row, col);
            }
        }
        return result;
    }

    private static double multiplyMatricesCell(double[][] firstMatrix, double[][] secondMatrix, int row, int col) {
        double cell = 0;
        for (int i = 0; i < secondMatrix.length; i++) {
            cell += firstMatrix[row][i] * secondMatrix[i][col];
        }
        return cell;
    }

    public static void print(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++)
                System.out.print(matrix[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    public static double[][] add(double[][] firstMatrix, double[][] secondMatrix) {

        boolean DifSize = IsDifSize(firstMatrix.length, secondMatrix.length, firstMatrix[0].length, secondMatrix[0].length);
        if (DifSize == true) {
            throw new IllegalArgumentException();
        }

        double[][] result = new double[firstMatrix.length][firstMatrix[0].length];

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = firstMatrix[row][col] + secondMatrix[row][col];
            }
        }
        return result;
    }

    public static double[][] subtract(double[][] firstMatrix, double[][] secondMatrix) {

        boolean DifSize = IsDifSize(firstMatrix.length, secondMatrix.length, firstMatrix[0].length, secondMatrix[0].length);
        if (DifSize == true) {
            throw new IllegalArgumentException();
        }
        double[][] result = new double[firstMatrix.length][firstMatrix[0].length];

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = firstMatrix[row][col] - secondMatrix[row][col];
            }
        }
        return result;
    }

    public static double[][] multiplyNumber(double[][] matrix, double number) {

        double[][] result = new double[matrix.length][matrix[0].length];

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = matrix[row][col] * number;
            }
        }
        return result;
    }

    public static boolean IsDifSize(int row1, int row2, int col1, int col2) {
        if ((row1 != row2) || (col1 != col2)) {
            return true;
        } else
            return false;
    }

    public static double[][] getMatrixFromFle(String file, int column, int rows) {

        try (InputStream inputStream = new FileInputStream(file)) {

            BufferedInputStream bufIn = new BufferedInputStream(inputStream, 100);
            int i;
            String matrix = "";
            while ((i = bufIn.read()) != -1) {
                matrix = matrix + ((char) i);
            }
            matrix += " ";

            return getMatrix(matrix, column, rows);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static double [][] getMatrix(String matrix, int column, int rows) {

        int n = 0, row = 0, col = 0;
        double [][] matrix1 = new double[rows][column];
        for (int j = 0; j < matrix.length(); j++) {
            if (matrix.charAt(j) == ' ' || matrix.charAt(j) == '\n') {
                char[] dst = new char[j-n];
                matrix.getChars(n, j, dst, 0);
                n = j + 1;
                matrix1[row][col] = Double.parseDouble(new String(dst));
                if ( matrix.charAt(j) == '\n') {
                    row++;
                    col = 0;
                }
                if ( matrix.charAt(j) != '\n') {
                    col++;
                }
            }
        }
        return matrix1;
    }
}
