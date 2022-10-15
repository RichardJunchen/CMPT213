package ca.sfu.cmpt213.Assignment2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


import com.google.gson.*;

public class TokimonProcessor {

    /**
     * @ check the main function parameter
     * @ par of main function
     */
    public static void check_arguments(String[] argc) {
        if (argc.length > 2 || argc.length<2) {
            System.out.println(" There are too many parameters we just want \" .csv \" file " +
                    "and \" .json \" file, please check it again ! ");
            System.exit(-1);
        }
        if (argc.length == 0) {
            System.out.println(" No parameter !! ");
            System.exit(-1);
        }
//        if (argc.length < 2) {
//            if (argc[0].endsWith("json"))
//                System.out.println(" We still need \" .csv \" file  to store our result ! Please check it again ");
//            else if (argc[0].endsWith("csv"))
//                System.out.println(" We still need \" .json \" file  to get our data ! Please check it again ");
//            System.exit(1);
//        }

        File temp1 = new File(argc[0]);
        File temp2 = new File(argc[1]);

        if (!temp1.exists()) {
            System.out.println(" System can not find " + temp1.getName() + " file !! ");
            System.exit(-1);
        } else if (!temp2.exists()) {
            System.out.println(" System can not find " + temp2.getName() + " file !! ");
            System.exit(-1);
        }

        if (argc[0].endsWith(".json") && argc[1].endsWith(".json")) {
            System.out.println(" We need a \" .csv \" file !");
            System.exit(-1);
        } else if (argc[0].endsWith(".csv") && argc[1].endsWith(".csv")) {
            System.out.println(" We need a \" .json \" file !");
            System.exit(-1);
        }
    }
    public static class TxtFilter implements FileFilter{
        public boolean accept (File pathname){
            return pathname.getName().contains(".json");
        }
    }
    public static class write_Filter implements FileFilter{
        public boolean accept (File pathname){
            return pathname.getName().contains(".csv");
        }
    }

//    public static void test (){
//        File file = new File("/Assignment2/src");
//        System.out.println(file.getAbsolutePath());
//    }

    /**
     * @ read all data from the json file
     * @ par path to csv, filter,List of Tokimons database, List of Tokimons team base,size of each group
     */
    public  static void input_json(File f, FileFilter  filter,List<Tokimons> Tokimons_database,List<Tokimons.Team> Tokimons_team,List<Integer> sizeofSub){
        File[] fileList= f.listFiles(filter);

        int count_for_sub_member = 0;


        if(fileList == null)
            System.exit(-1);
        for (File subFile: fileList){
            File input = new File (subFile.getAbsolutePath());
            try{
                JsonElement fileElement = JsonParser.parseReader(new FileReader(input));
                JsonObject fileObject = fileElement.getAsJsonObject();

                String extra_comments = fileObject.get("extra_comments").getAsString();
                JsonArray jsonArrayOfToki = fileObject.get("team").getAsJsonArray();

                for (JsonElement eachToki : jsonArrayOfToki) {
                    // get the Json object:
                    JsonObject Tokimon_object = eachToki.getAsJsonObject();

                    // extract the data
                    String name = Tokimon_object.get("name").getAsString();
                    String id = Tokimon_object.get("id").getAsString();

                    JsonObject compatibility = Tokimon_object.get("compatibility").getAsJsonObject();
                    double score = compatibility.get("score").getAsDouble();
                    if (score < 0 )
                    {
                        System.out.println(" WRONG!! Input score invalid ");
                        System.out.println(" File is " + subFile.getName());
                        System.out.println(" Path of " + subFile.getName() + "is : " + input);
                        System.exit(-1);
                    }
                    String comment = compatibility.get("comment").getAsString();

                    Tokimons.Compatibility compa = new Tokimons.Compatibility(score,comment);

                    Tokimons.Team team = new Tokimons.Team(name, id, compa);
                    Tokimons_team.add(team);
                    count_for_sub_member++;
                }
                Tokimons tokimons = new Tokimons(extra_comments, Tokimons_team);
                Tokimons_database.add(tokimons);
                sizeofSub.add(count_for_sub_member);
                count_for_sub_member  =  0;

            }catch (FileNotFoundException e) {
                System.out.println(" Error input file : " + subFile.getName() + " not found ! ");
                System.out.println(" Path of " + subFile.getName() + "is : " + input);
                System.exit(-1);
            }catch (Exception e){
                System.out.println(" Error processing input file --> ! " + subFile.getName());
                System.out.println(" Path of " + subFile.getName() + "is : " + input);
                System.exit(-1);
            }
        }
    }
    /**
     * @ recursivly to find a jason file and output it
     * @ par path to csv, filter,List of Tokimons database, List of Tokimons team base,size of each group
     */
    public static void getAllFile( File filePath,FileFilter filter,List<Tokimons> Tokimons_database,List<Tokimons.Team> Tokimons_team,List<Integer> sizeofSub){
        File[] files = filePath.listFiles();
        if(files == null){
            System.out.println(" The file " + filePath.getName() + " can not found ");
            System.out.println(" The path of " + filePath.getName() + " is : " + filePath.getAbsolutePath());
            System.exit(-1);
        }
        for (File f:files) {
            if (f.isDirectory()) {
                input_json(f, filter,Tokimons_database,Tokimons_team,sizeofSub);
                getAllFile(f, filter,Tokimons_database,Tokimons_team,sizeofSub);
            }
        }
    }
    /**
     * @ for the different group of data
     * @ par path to csv, filter,List of Tokimons database, List of Tokimons team base,size of each group
     */
    public static void second_case (File write_path, FileFilter filter2,List<Tokimons> temp_store,List<Tokimons.Team>Tokimons_team,List<Integer>sizeofSub,int count) throws IOException {
        File[] fileList2 = write_path.listFiles(filter2);
        // for storing the different group
        String group_id = null;

        for (File tempfile : fileList2) {
            File output = new File(tempfile.getAbsolutePath());
            FileWriter fw = new FileWriter(output, true);

            PrintWriter outputFile = new PrintWriter(fw);
            String title = "Team#,From Toki,To Toki,Score,Comment,,Extra";
            outputFile.println(title);
            int i = 0;
            int j = 0;
            while (!temp_store.isEmpty()) {
                outputFile.println("Team " + count + ",,,,,,,");
                if (i == 0) {
                    outputFile.print("," + Tokimons_team.get(j).getId()
                            + ",-," + Tokimons_team.get(j).getCompatibility().getScore() +
                            "," + Tokimons_team.get(j).getCompatibility().getComment()
                            + ",," + temp_store.get(j).getExtra_comments() + "\n");
                    group_id = Tokimons_team.get(j).getId();
                    temp_store.remove(temp_store.get(j));
                    i = 1;
                } else {
                    int should_size = sizeofSub.get(0);
                    for (int k = 0; k < should_size - 1; k++) {
                        outputFile.print(group_id + "," + Tokimons_team.get(j).getId() +
                                "," + Tokimons_team.get(j).getCompatibility().getScore() +
                                "," + Tokimons_team.get(j).getCompatibility().getComment()
                                + ",,\n");
                        Tokimons_team.remove(Tokimons_team.get(j));
                    }
                    sizeofSub.remove(j);
                    i = 0;
                }

            }
            outputFile.close();
        }
    }
    /**
     * @ output the file details into the csv file
     * @ par path to csv, filter,List of Tokimons database, List of Tokimons team base,size of each group
     */
    public static void output_json(File write_path, FileFilter filter2,List<Tokimons> Tokimons_database,List<Tokimons.Team> Tokimons_team,List<Integer> sizeofSub) throws IOException {
        File[] fileList2= write_path.listFiles(filter2);
        // for storing the different group
        List<Tokimons> temp_store = new ArrayList<>();
        String group_id = null;

        int count = 0;
        for (File tempfile : fileList2) {
            File output = new File(tempfile.getAbsolutePath());
            FileWriter fw = new FileWriter(output, true);

            PrintWriter outputFile = new PrintWriter(fw);
            String title = "Team#,From Toki,To Toki,Score,Comment,,Extra";
            String team_num = "";
            outputFile.println(title);
            int i = 0;
            int j = 0;
            while(!Tokimons_team.isEmpty()){
                String context = Tokimons_team.get(i).getId();
                // search if they are in the same group
                List<String> temp = new ArrayList<>();
                for(String each:context.replaceAll("[^0-9]", ",").split(",")){
                    if (each.length()>0)
                        temp.add(each);
                }
                String team_num_temp = temp.get(0);

                // first element in the first new team
                if (team_num.equals("") && !team_num_temp.equals(team_num)){
                    count++;
                    team_num = team_num_temp;
                    outputFile.println("Team " + count + ",,,,,,,");
                }
                if (team_num.equals(team_num_temp)){
                    if (i == 0) {
                        outputFile.print("," + Tokimons_team.get(j).getId()
                                + ",-," + Tokimons_team.get(j).getCompatibility().getScore() +
                                "," + Tokimons_team.get(j).getCompatibility().getComment()
                                + ",," + Tokimons_database.get(j).getExtra_comments() +"\n");
                        group_id = Tokimons_team.get(j).getId();
                        Tokimons_team.remove(Tokimons_team.get(j));
                        Tokimons_database.remove(Tokimons_database.get(j));
                        i = 1;
                    }
                    else {
                        int should_size = sizeofSub.get(0);
                        for (int k = 0; k < should_size-1; k++) {
                            outputFile.print( group_id + "," + Tokimons_team.get(j).getId() +
                                    "," + Tokimons_team.get(j).getCompatibility().getScore() +
                                    "," + Tokimons_team.get(j).getCompatibility().getComment()
                                    + ",,\n");
                            Tokimons_team.remove(Tokimons_team.get(j));
                        }
                        sizeofSub.remove(j);
                        i = 0;
                    }

                }
                else {
                    temp_store.add(Tokimons_database.get(j));
                }
            }
            outputFile.close();
        }
        if (!temp_store.isEmpty())
            second_case (write_path,filter2,temp_store,Tokimons_team,sizeofSub,count);

    }
    /**
     * @ recursivly to find a csv file and output it
     * @ par path to csv, filter,List of Tokimons database, List of Tokimons team base,size of each group
     */
    public static void find_csv (File write_path, FileFilter filter2,List<Tokimons> Tokimons_database,List<Tokimons.Team> Tokimons_team,List<Integer> sizeofSub) throws IOException {
        File[] files2 = write_path.listFiles();
        if(files2 == null){
            System.out.println(" The file " + write_path.getName() + " can not found ");
            System.out.println(" The path of " + write_path.getName() + " is : " + write_path.getAbsolutePath());
            System.exit(-1);
        }
        for (File temp :files2) {
            if (temp.isDirectory()) {
                output_json(temp, filter2,Tokimons_database,Tokimons_team,sizeofSub);
                find_csv(temp, filter2,Tokimons_database,Tokimons_team,sizeofSub);
            }
        }
    }

    public static void main(String[] argc) throws IOException {
        System.out.println( " Checking arguments in the main function... ");
        check_arguments(argc);
        System.out.println( " Arguments checked ! ");
        // input the json file
        List<Tokimons> Tokimons_database = new ArrayList<>();
        List<Tokimons.Team> Tokimons_team = new ArrayList<>();
        List<Integer> sizeofSub = new ArrayList<>();
        FileFilter filter = new TxtFilter();
        File folder = new File(argc[0]);
        System.out.println(" Collecting data...");
        getAllFile(folder,filter,Tokimons_database,Tokimons_team,sizeofSub);
        System.out.println(" ---> Data collected ! <---");

        // output the csv file
        File write_folder = new File(argc[1]);
        System.out.println(" Ready to output the information... " );
        FileFilter filter2 = new write_Filter();
        find_csv(write_folder,filter2,Tokimons_database,Tokimons_team,sizeofSub);
        System.out.println();
        System.out.println(" ---> Data outputting finished <---");

    }
}