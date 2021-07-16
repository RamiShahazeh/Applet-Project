package miniprojecthomework;

import java.io.*;

public class ReadFiles {
    
   ReadFiles(String path){
    }
    
    public String GetAddress (String path) throws FileNotFoundException, IOException{
        //Get the address of the text file (the first line)
       
        BufferedReader br = new BufferedReader(new FileReader(path));
        String address=br.readLine();
        br.close();
        return address;
        }
        
    public int GetNumberOfYears(String path) throws FileNotFoundException, IOException
     {  
            //gets the number of the years which is constant in all the citys 
         
            BufferedReader br = new BufferedReader(new FileReader(path));
            int n=0;
            br.readLine();//the address
            br.readLine();//the first city
            while(br.readLine().charAt(4)==',')//for example "2004, 256": ',' is the forth character
                {
                    n++; 
                }
           br.close();
           return n;
      }
    
    public int GetNumberOfCities(String path) throws IOException,FileNotFoundException
    {  
        //gets the number of citys (maximum 10)
        BufferedReader br = new BufferedReader(new FileReader(path)); 
        int n=0;
        br.readLine();//the address
        String cl = br.readLine();//the first city
        while(cl!=null)
        {
            if (cl.charAt(0) >=65) //the first character is in the alphabet (A=65 in ASCII)
            {
            cl = br.readLine();//we read the city
            n++;//increase number of cities by one
            }
            else
            cl = br.readLine();  //we move on because this line is a year with the rain rate of this year
        }
       br.close();
       return n;
    }
    
   public String [] GetCities(String path) throws FileNotFoundException, IOException
    {   
        //gets the names of the cities
        int cityNum  =0;
        cityNum=GetNumberOfCities(path);//number of cities
        String[] c=new String[cityNum];//allocate an array of Strings
        BufferedReader br = new BufferedReader(new FileReader(path));//open the file 
        br.readLine();//read the address
        int j=0;
        String cl = br.readLine();//the first city
        while(cl!=null)
        {
            if (cl.charAt(0)>=65)
            {
            c[j]=cl;//save the city in the index j
            cl=br.readLine();//move on
            j++;
            }
            else
            cl = br.readLine();  //we move on because this line is a year with the rain rate of this year
        }
        br.close();
        return c;
    }
    
      public int [] GetAvgs(String path) throws FileNotFoundException, IOException,NullPointerException
    {
        //gets the avarege of each city
       BufferedReader br = new BufferedReader(new FileReader(path));//open the file
       int nc=GetNumberOfCities(path);//number of citys
       int ny=GetNumberOfYears(path);//number of years
       int[] citiesAvg = new int[nc];//allocate an array of the all city's avarege
       String cl=br.readLine();//the address
       int sum=0;//the sum of the rain rate in the years in a city
       for(int k=0;k<nc;k++)
        {
            sum=0;
            cl=br.readLine();//the first city
            {
              for(int i=0;i<ny;i++)
              { 
                  cl=br.readLine();
                 sum+=Integer.parseInt(cl.substring(6)); //we take the avarege the ith year and parse it to integar
                 //for example "2004, 456" the element in 6 is 4 and we take the substring from 6 to the end of this String
                 //and parse it to integar for sum it with the other years
              }
              citiesAvg[k]=(sum/ny);//we calculate the avarege of all the years of this city
            }
            //move to the next city if we didn't reach all cities
         }
       br.close();
       return citiesAvg;
    }
      
     public int [] getYearsOfcity (String city , String path) throws IOException
    {
        
         BufferedReader br = new BufferedReader(new FileReader(path));
         br.readLine();//title
         int ny=GetNumberOfYears(path);
         int [] years =new int[ny];
         String c=br.readLine();
         while( c!=null){
             
            if(c.equals(city)) //we reach the city we want
                {
                   int index=0;
                   while(index!=ny)//put all the years in an array and returns it
                   {
                     String y=br.readLine();
                     y = y.substring(0, 4);
                     int year=Integer.parseInt(y);
                     years[index]=year;
                     index++;
                   }
                   break;
                }
            else c=br.readLine();
         }
         
         return years;
    }
     
     
     public int [] getRatesOfcity (String city , String path) throws IOException
    {
     BufferedReader br = new BufferedReader(new FileReader(path));
     String cl;
     cl=br.readLine();//title
     int ny=GetNumberOfYears(path);
     int [] Rates =new int[ny];
     cl=br.readLine();
     while(cl!=null){
             
        if(cl.equals(city)) //we reach the city we want
            {
               int index=0;
               while(index!=ny)//put all the rain Rates in an array and returns it
               {
                 String y=br.readLine();
                 y = y.substring(6);
                 int year=Integer.parseInt(y);
                 Rates[index]=year;
                 index++;
               }
               break;
            }
        else cl=br.readLine();
     }
     return Rates;
    }
     
    public int getFactorOfRates(String path) throws IOException
    {
         int[] Rates;
         int factor=1;
         String [] cities =GetCities(path);
         for(String city:cities)
         {
             Rates=getRatesOfcity(city,path);
              for(int l:Rates)
              {
                 while((l/factor)>900)
                  factor++;
              }
         }
      return factor;   
    }
       
}
    
    
    



  

