package com.company;

import javax.xml.crypto.Data;
import java.io.*;
import java.sql.*;
import java.time.Duration;
import java.util.*;


public class Main
{

   public static void main(String[] args)
   {
      ArrayList<DataEntry> data = getEntriesFromFile("text.txt");
      //System.out.println(data);
      long days = 0;

      try (Connection con = DriverManager.getConnection("jdbc:sqlite:employee.db");
           Statement statement = con.createStatement();)
      {
         statement.execute("CREATE TABLE IF NOT EXISTS employee (EmpID INTEGER NOT NULL,ProjectID INTEGER NOT NULL,ProjectDays INTEGER NOT NULL)");

         statement.execute("DELETE FROM employee");

         String query = "INSERT INTO EMPLOYEE VALUES(?, ?, ?)";

         PreparedStatement pStatement = con.prepareStatement(query);

         for(int i = 0;i<data.size();i++)
         {
            days = Math.round((float) (data.get(i).getEndDate().getTimeInMillis() - data.get(i).getStartDate().getTimeInMillis()) / (24 * 60 * 60 * 1000));

           /* int startYear = data.get(i).getStartDate().get(Calendar.YEAR);
            int startMonth = data.get(i).getStartDate().get(Calendar.MONTH);
            int startDay = data.get(i).getStartDate().get(Calendar.DAY_OF_MONTH);

            int endYear = data.get(i).getEndDate().get(Calendar.YEAR);
            int endMonth = data.get(i).getEndDate().get(Calendar.MONTH);
            int endDay = data.get(i).getEndDate().get(Calendar.DAY_OF_MONTH);*/

            pStatement.setInt(1,data.get(i).getEmployeeId());
            pStatement.setInt(2,data.get(i).getProjectId());
            pStatement.setInt(3,(int)days);

            pStatement.executeUpdate();
         }

         // Да се напише приложение, което намира двойката служители, които най-дълго време са работили заедно по общи проекти.

         ResultSet results = statement.executeQuery("SELECT * " +
                 "FROM employee " +
                 "ORDER BY employee.ProjectDays DESC, employee.ProjectId");

         while(results.next())
         {
            System.out.println(results.getInt(1) + " " +
                    results.getInt(2) + " " +
                    results.getInt(3));
         }

      } catch (SQLException e)
      {
         System.err.printf("Something went wrong : %s", e.getMessage());
      }

   }

   public static ArrayList<DataEntry> getEntriesFromFile(String filePath)
   {
      ArrayList<DataEntry> results = new ArrayList<>();

      Scanner scanner = null;

      try
      {
         scanner = new Scanner(new File(filePath));
         while (scanner.hasNext())
         {
            String[] strEntries  = scanner.nextLine().split(",");
            results.add(new DataEntry(strEntries[0], Integer.parseInt(strEntries[1]), strEntries[2], strEntries[3]));
         }

      }
      catch (Exception e)
      {
         System.err.println("File error" + e.getMessage());
      }
      finally
      {
         scanner.close();
      }

      return results;
   }

}
