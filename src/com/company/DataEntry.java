package com.company;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataEntry
{
   private int employeeId;
   private int projectId;
   private Calendar startDate;
   private Calendar endDate;

   public DataEntry(String employeeId, int project, String startDate, String endDate)
   {
      setEmployeeId(employeeId);
      this.projectId = project;
      setStartDate(startDate);
      setEndDate(endDate);
   }

   public void setEmployeeId(String employeeId)
   {
      this.employeeId  = Integer.parseInt(employeeId);
   }

   public int getEmployeeId()
   {
      return employeeId;
   }

   public void setProjectId(int projectId)
   {
      this.projectId = projectId;
   }

   public int getProjectId()
   {
      return projectId;
   }

   public void setStartDate(String startDate)
   {
      this.startDate = Calendar.getInstance();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date date = null;

      try
      {
         date = sdf.parse(startDate);
      }
      catch (ParseException e)
      {
         e.printStackTrace();
      }
      this.startDate.setTime(date);
   }

   public Calendar getStartDate()
   {
      return startDate;
   }

   public Calendar getEndDate()
   {
      return endDate;
   }

   public void setEndDate(String endDate)
   {
      this.endDate = Calendar.getInstance();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date date = null;
      try
      {
         if (endDate.equals("NULL"))
         {
            date = sdf.parse(sdf.format(new Date()));
         }
         else
         {
            date = sdf.parse(endDate);
         }
      }
      catch (ParseException e)
      {
         e.printStackTrace();
      }
      this.endDate.setTime(date);
   }


   @Override
   public String toString()
   {
      return String.format("EmployeeId:%d, ProjectId:%d, StartDate:%s, EndDate:%s%n", getEmployeeId(), getProjectId(), getStartDate().getTimeInMillis(), getEndDate().getTimeInMillis());
   }
}
