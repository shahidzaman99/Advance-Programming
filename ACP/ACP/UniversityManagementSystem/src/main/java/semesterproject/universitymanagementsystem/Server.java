package semesterproject.universitymanagementsystem;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    private static final String STUDENT_FILE = "student_data.txt";
    private static final String UNIVERSITY_FILE = "university_data.txt";

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(5000)) {
            System.out.println("Server running on port 5000...");

            while (true) {
                Socket socket = server.accept();
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

                String request = br.readLine();

                if (request == null) continue;

                if (request.equals("LOAD_STUDENT")) {
                    loadData(STUDENT_FILE, pw);
                } else if (request.equals("LOAD_UNIVERSITY")) {
                    loadData(UNIVERSITY_FILE, pw);
                } else if (request.startsWith("SAVE_STUDENT")) {
                    saveData(STUDENT_FILE, request.substring(12));
                    pw.println("SAVED");
                } else if (request.startsWith("SAVE_UNIVERSITY")) {
                    saveData(UNIVERSITY_FILE, request.substring(16));
                    pw.println("SAVED");
                } else if (request.startsWith("DELETE_STUDENT")) {
                    String roll = request.substring(15).trim();
                    if (deleteRecord(STUDENT_FILE, roll)) {
                        pw.println("DELETED");
                    } else {
                        pw.println("NOT_FOUND");
                    }
                } else if (request.startsWith("DELETE_UNIVERSITY")) {
                    String id = request.substring(17).trim();
                    if (deleteRecord(UNIVERSITY_FILE, id)) {
                        pw.println("DELETED");
                    } else {
                        pw.println("NOT_FOUND");
                    }
                } else if (request.startsWith("EDIT_STUDENT")) {
                    String data = request.substring(13).trim();
                    if (editRecord(STUDENT_FILE, data)) {
                        pw.println("EDITED");
                    } else {
                        pw.println("NOT_FOUND");
                    }
                } else if (request.startsWith("EDIT_UNIVERSITY")) {
                    String data = request.substring(17).trim();
                    if (editRecord(UNIVERSITY_FILE, data)) {
                        pw.println("EDITED");
                    } else {
                        pw.println("NOT_FOUND");
                    }
                } else {
                    pw.println("INVALID_REQUEST");
                }

                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadData(String fileName, PrintWriter pw) {
        File file = new File(fileName);
        if (!file.exists() || file.length() == 0) {
            pw.println("NO_DATA");
            pw.println("END");
            return;
        }

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                pw.println(sc.nextLine());
            }
        } catch (Exception e) {
            pw.println("ERROR_READING_FILE");
        }

        pw.println("END");
    }

    private static void saveData(String fileName, String data) {
        try (FileWriter fw = new FileWriter(fileName, true)) {
            fw.write(data + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean deleteRecord(String fileName, String key) {
        File file = new File(fileName);
        if (!file.exists()) return false;

        boolean found = false;
        List<String> tempList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String firstColumn = line.split(",")[0].trim();
                if (firstColumn.equals(key.trim())) {
                    found = true;
                    continue;
                }
                tempList.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (String s : tempList) pw.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return found;
    }

    private static boolean editRecord(String fileName, String newData) {
        String key = newData.split(",")[0].trim();
        File file = new File(fileName);
        if (!file.exists()) return false;

        boolean updated = false;
        List<String> updatedList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String firstColumn = line.split(",")[0].trim();
                if (firstColumn.equals(key)) {
                    updatedList.add(newData);
                    updated = true;
                } else {
                    updatedList.add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (String s : updatedList) pw.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return updated;
    }
}
