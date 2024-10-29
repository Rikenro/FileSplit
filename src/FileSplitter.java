import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class FileSplitter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan nama file: ");
        String fileName = scanner.nextLine();

        System.out.print("Masukkan jumlah baris per potongan: ");
        int linesPerPart = scanner.nextInt();

        Queue<String> lineQueue = new ArrayDeque<>();
        int partNumber = 1;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineCount = 0;

            while ((line = reader.readLine()) != null) {
                lineQueue.add(line);
                lineCount++;

                if (lineCount == linesPerPart) {
                    writePartToFile(lineQueue, partNumber++);
                    lineCount = 0;
                }
            }

            if (!lineQueue.isEmpty()) {
                writePartToFile(lineQueue, partNumber);
            }

        } catch (IOException e) {
            System.err.println("Terjadi kesalahan saat membaca file: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void writePartToFile(Queue<String> lineQueue, int partNumber) {
        String outputFileName = "part" + partNumber + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            while (!lineQueue.isEmpty()) {
                writer.write(lineQueue.poll());
                writer.newLine();
            }
            System.out.println("Bagian " + partNumber + " disimpan ke " + outputFileName);
        } catch (IOException e) {
            System.err.println("Terjadi kesalahan saat menulis ke file: " + e.getMessage());
        }
    }
}
