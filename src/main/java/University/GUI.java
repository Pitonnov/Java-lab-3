package University;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GUI {
   private static BufferedReader bufferedReader;
   public  static void startDialog()
   {
       boolean end=false;
       while(!end)
       {
           System.out.println("Enter number of desired item\n" +
                              " 0 Load groups with students from file");
           bufferedReader= new BufferedReader(new InputStreamReader(System.in));
           int number;
           try
           {
               number = Integer.parseInt(bufferedReader.readLine());
           }
           catch (Exception ex)
           {
               System.out.println(ex.getMessage());
               continue;
           }
           switch (number)
           {
                   case 0:
                       System.out.println("Enter file location");
                       try
                       {
                           String fileLocation = bufferedReader.readLine();
                           Deanery.createGroupsWithStudentsFromFile(fileLocation);
                       }
                       catch (Exception ex)
                       {
                           System.out.println(ex.getMessage());
                           break;
                       }
                       end=true;
                   break;
           }
       }
       startWork();
   }
   public static void startWork()
   {
       boolean keepGoing=true;
       while(keepGoing)
       {
           System.out.println("Enter number of desired item\n" +
                   " 0 Show student | 1 Choose head of group | 2 Expel student | 3 Print deanery stats | 4 Add mark | 5 Write groups to file" +
                   " | 6 Exit");
           int number;
           try
           {
               number = Integer.parseInt(bufferedReader.readLine());
           }
           catch (Exception ex)
           {
               System.out.println(ex.getMessage());
               continue;
           }
           switch (number)
           {
               case 0:
                   while(true)
                   {
                       int studentId;
                       System.out.println("Enter student's id or -1 if you want all");
                       try
                       {
                           studentId = Integer.parseInt(bufferedReader.readLine());

                       }
                       catch (Exception ex)
                       {
                           System.out.println(ex.getMessage());
                           continue;
                       }
                       if(studentId>-1)
                       {
                           printStudent(Deanery.getStudentById(studentId));
                       }
                       else
                       {
                           for(Student student: Deanery.getAllStudents())
                               printStudent(student);
                       }
                       break;
                   }
                   break;
               case 1:
                   while(true)
                   {
                       System.out.println("Enter group's id or -1 if you want all");
                       int groupId;
                       try
                       {
                           groupId= Integer.parseInt(bufferedReader.readLine());
                       }
                       catch (Exception ex)
                       {
                           System.out.println(ex.getMessage());
                           continue;
                       }
                       if(groupId>-1)
                       {
                           Deanery.getGroupById(groupId).chooseHead();
                           printStudent(Deanery.getGroupById(groupId).getHead());
                       }
                       else
                       {
                           Deanery.chooseHeads();
                           for(Group group:Deanery.getGroups())
                               printStudent(group.getHead());
                       }
                       break;
                   }
                   break;
               case 2:
                   while(true)
                   {
                       System.out.println("Enter student's id or -1 if you want all");
                       int studentId;
                       try
                       {
                           studentId = Integer.parseInt(bufferedReader.readLine());
                       }
                       catch (Exception ex) {
                           System.out.println(ex.getMessage());
                           continue;
                       }
                       if(studentId>-1)
                       {
                           Deanery.expelStudent(Deanery.getStudentById(studentId));
                           break;
                       }
                       else
                       {
                           while(true)
                           {
                               System.out.println("Enter minimum mark");
                               float minimumMark;
                               try
                               {

                                   minimumMark = Float.parseFloat(bufferedReader.readLine());
                               }
                               catch (Exception ex)
                               {
                                   System.out.println(ex.getMessage());
                                   continue;
                               }
                               Deanery.expelStudentsForBadMarksInAllGroups(minimumMark);
                               break;
                           }
                           break;
                       }
                   }
                   break;
               case 3:
                   printDeaneryStats();
                   break;
               case 4:
                   while(true)
                   {
                       System.out.println("Enter student id");
                       int studentId;
                       try
                       {
                           studentId=Integer.parseInt(bufferedReader.readLine());
                       }
                       catch (Exception ex)
                       {
                           System.out.println(ex.getMessage());
                           continue;
                       }
                       while(true)
                       {
                           System.out.println("Enter mark");
                           float mark;
                           try
                           {
                               mark=Float.parseFloat(bufferedReader.readLine());
                           }
                           catch (Exception ex)
                           {
                               System.out.println(ex.getMessage());
                               continue;
                           }
                           Deanery.getStudentById(studentId).addMark(mark);
                           break;
                       }
                       break;
                   }
                   break;
               case 5:
                   while(true)
                   {
                       System.out.println("Enter file output name");
                       try
                       {
                           String outputFileName = bufferedReader.readLine();
                           Deanery.writeCurrentJsonGroupsWithStudents(outputFileName);
                       }
                       catch (Exception ex)
                       {
                           System.out.println(ex.getMessage());
                           continue;
                       }
                       break;
                   }
                   break;
               case 6:
                   keepGoing=false;
                   break;
           }

       }
   }
   public static void printStudent(Student student)
   {
       System.out.println("ID: "+student.getID()+" Group: "+student.getGroup().getTitle()+" Avg mark: "
               +student.getAverageMark()+" FIO: "+student.getFIO());
   }
   public static void printDeaneryStats()
   {
       System.out.println("Students number: "+Deanery.getTotalStudentsNumber());
   }
}
