package miniprojecthomework;


import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;




public class NewApplet extends Applet implements ActionListener,MouseMotionListener{

    
    Graphics g;
    Button ReadFileButton , DrawButton,drawLines; 
    TextField AddressText,OptionsText,inputPath,MousePointer;
    Label mouseLabel,errorLabel;
    int drawinput,drawAll,drawOp,drawOne,drawLinesFlag;//drawinput is a flag we used to draw the city names with the avg of rain in it
    //drawAll is a flag we used to draw the histogram of all the citys in file
    String [] options;
    int oneOption=0,drawLineshelper=1;
    final JFileChooser fc = new JFileChooser();
    Boolean allmouseflag,opmouseflag,onemouseflag;
  
    @Override
    public void init() throws NullPointerException {
        
    ReadFileButton = new Button ("Read File");  
    DrawButton=new Button("Draw");
    AddressText=new TextField("",20);
    AddressText.setEditable(false);
    OptionsText=new TextField("" ,20);
    inputPath = new TextField("",10);
    mouseLabel=new Label("");
    drawLines=new Button("draw Lines");
    errorLabel=new Label();
    
    ReadFileButton.addActionListener(this); //adding actionlistenr to the two bottons
    DrawButton.addActionListener(this);
    drawLines.addActionListener(this);
    this.addMouseMotionListener( this);
        
    //setting each item in the right place
    
    ReadFileButton.setBounds(10, 30, 200, 40);
    inputPath.setBounds(10,80,200,30);
    AddressText.setBounds(10, 120, 280, 30);
    OptionsText.setBounds(10, 600, 280, 30);
    mouseLabel.setBounds(10,680,280,30);
    DrawButton.setBounds(10, 740, 200, 40);
    drawLines.setBounds(1660, 100, 100, 50);
    errorLabel.setBounds(1580,200,280,30);
       
    //adding items to our Applet screen

    add(ReadFileButton);
    add(AddressText);
    add(OptionsText);
    add(DrawButton);
    add(mouseLabel);
    add(drawLines);
    add(errorLabel);
        
    inputPath.setVisible(false);
    mouseLabel.setVisible(false);
    errorLabel.setVisible(false);

    setBackground(Color.lightGray);//color of the backround

    mouseLabel.setBackground(Color.gray);

    setLayout(null);
    }

    @Override
    public void paint(Graphics g){
    try{
       
       g.drawString("input", 20, 17);//drawing input at first
       
       g.drawRect(1,1,310,910);//drawing the biggest rect in the left side
       
       g.setColor(Color.white);//making the place for the data white
       g.fillRect(10,160,290,250);//place for the data
       
       g.setColor(Color.gray);
       g.drawRect(5, 21, 300, 430);//making the input rect
       
       g.setColor(Color.black);
       g.drawString("Drawing", 20, 470);
       
       g.setColor(Color.gray);
       g.drawRect(5, 478, 300, 320);//making the drawing rect
       
       g.setColor(Color.black);
       g.drawString("Options", 20, 590);
       
       g.drawRect(320, 1, 1250, 910);//place for the histogram
       
       
       
       if(drawinput==1)//first part
       {
           
            int j=30;
            allmouseflag=false;opmouseflag=false;onemouseflag=false;
            g.setFont(new Font("Arial",Font.PLAIN,15));
            String path =inputPath.getText();
            String output="";
            ReadFiles r=new ReadFiles(path);
            AddressText.setText(r.GetAddress(path));//to show the Address of our Text File
            String[] citys=new String[r.GetNumberOfCities(path)];
            citys=r.GetCities(path);
            int[] avgs=new int[r.GetNumberOfCities(path)];
            avgs=r.GetAvgs(path);
            for(int i=0;i<r.GetNumberOfCities(path);i++)
            {
                output=(i+1 )+" "+citys[i]+" "+avgs[i];
                g.drawString(output,15,150+j);
                j+=20;
            }
       }
       
       if(drawAll==1)//second part
       {
            opmouseflag=false;onemouseflag=false;
            allmouseflag=true;//for the mouse listner to know we are in this case
          
            String path =inputPath.getText();
            ReadFiles r=new ReadFiles(path);
            int n=r.GetNumberOfCities(path);
            int[] avgs=r.GetAvgs(path);
            int factor=r.getFactorOfRates(path);
            int nums=360;
          
            if(drawLinesFlag==1)
                {
                    if(drawLineshelper==1)
                        {
                            Draw l=new Draw();
                            l.drawLines(g,factor);
                            drawLineshelper=0;
                        }
                    else
                        drawLineshelper=1;
            
                    drawLinesFlag=0;
                }
          
            for(int i=1;i<=n;i++)
            {
                String s=String.valueOf(i);
                g.setColor(Color.BLACK);
                g.drawString(s, nums, 930);
                g.setColor(new Color((int)(Math.random() * 0x1000000)));//get a random color
                g.fillRect(nums-30, 900-(avgs[i-1]/factor), 60, avgs[i-1]/factor);// (*)
                nums+=120;
              
                //  g.drawLine(360, 900, 420, 890); the start line of the first city we want to draw
                //  the way we reach (*) is well explained in the report.
            }
       }
       
       else if(drawOp==1)
       {    
            String path =inputPath.getText();
            ReadFiles r=new ReadFiles(path);
            int[] avgs=r.GetAvgs(path);
            int factor =r.getFactorOfRates(path);
            int nums=360;
            int cityindex;
              
            if(drawLinesFlag==1)
                {
                    if(drawLineshelper==1)
                    {
                        Draw l=new Draw();
                        l.drawLines(g,factor);
                        drawLineshelper=0;
                    }
                    else
                        drawLineshelper=1;
            
            drawLinesFlag=0;
                }
            
            //for the mouse listner to know we are in this case
            allmouseflag=false;onemouseflag=false;
            opmouseflag=true;
             
            for(String op:options)
            {
                cityindex=Integer.parseInt(op);
                if(cityindex<=10)
                {
                    g.setColor(Color.BLACK);
                    g.drawString(op, nums, 930);
                    g.setColor(new Color((int)(Math.random() * 0x1000000)));//get a random color
                    g.fillRect(nums-30, 900-(avgs[cityindex-1]/factor), 60, avgs[cityindex-1]/factor);// (*)
                    nums+=120;
              
                    //g.drawLine(360, 890, 420, 890); the start line of the first city we want to draw
                    //  the way we reach (*) is well explained in the report.
                }
                else
                    {
                        errorLabel.setVisible(true);
                        errorLabel.setFont(new Font("Arial",Font.PLAIN,18));
                        errorLabel.setBackground(Color.white);
                        errorLabel.setText("Worng options try agine");
                        drawOne=0;
                        drawAll=0;
                        drawOp=0;
                    }
            }
        }
           
       
        else if(drawOne==1)
        {     
            allmouseflag=false;opmouseflag=false;
            onemouseflag=true;
           
            String path =inputPath.getText();
            ReadFiles r=new ReadFiles(path);
            String[] cities=new String[r.GetNumberOfCities(path)];
            cities= r.GetCities(path);
            int [] years= r.getYearsOfcity(cities[oneOption-1], path);
            int [] Rates=r.getRatesOfcity(cities[oneOption-1], path);
            int factor=r.getFactorOfRates(path);
            
            if(drawLinesFlag==1)
            {
                if(drawLineshelper==1)
                {
                    Draw l=new Draw();
                    l.drawLines(g,factor);
                    drawLineshelper=0;
                }
                else
                    drawLineshelper=1;
            
                drawLinesFlag=0;
            } 
             
            if(years.length>11)
            {
                int len=years.length;
                int v=(1200/len)-5;
                //1200 in the maximun width
                String yr;
                int nums=330+(v/2),i=0;
                for(int year:years) 
                {
                    yr=Integer.toString(year);
                    g.setColor(Color.BLACK);
                    g.drawString(yr, nums-(v/2), 930);
                    g.setColor(new Color((int)(Math.random() * 0x1000000)));//get a random color
                    g.fillRect(nums-(v/2), 900-(Rates[i]/factor), (v/2), Rates[i]/factor);
                    nums+=v;
                    i++;
                    //g.drawLine(360, 890, 420, 890); the start line of the first city we want to draw
                    //  the way we reach (*) is well explained in the report.
                }
            
            }
            else
            {
                String yr;
                int nums=360,i=0;
                for(int year:years) 
                {
                    yr=Integer.toString(year);
                    g.setColor(Color.BLACK);
                    g.drawString(yr, nums-25, 930);
                    g.setColor(new Color((int)(Math.random() * 0x1000000)));//get a random color
                    g.fillRect(nums-30, 900-(Rates[i]/factor), 60, Rates[i]/factor);
                    nums+=120;
                    i++;
               //g.drawLine(360, 890, 420, 890); the start line of the first city we want to draw
               //  the way we reach (*) is well explained in the report.
                }
    
            }
             
        }
        
       
        }
        catch (Exception e)
        {
            errorLabel.setVisible(true);
            errorLabel.setFont(new Font("Arial",Font.PLAIN,18));
            errorLabel.setText("error in drawing the file");
            errorLabel.setBackground(Color.white);
            AddressText.setText("");
            drawAll=0;
            drawOne=0;
            drawOp=0;
            drawinput=0;
            repaint();
        }
    }
   
    @Override
    public void actionPerformed(ActionEvent e) {
      
        if(e.getSource()==ReadFileButton)//the button Read File is pressed
        {
            drawAll=0;drawOne=0;drawOp=0;
            errorLabel.setText("");
            errorLabel.setBackground(Color.lightGray);
                
            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) 
            {
                File file = fc.getSelectedFile();
                if(file.getAbsolutePath().contains(".txt"))
                {
                    inputPath.setText(file.getAbsolutePath());
                    drawinput=1;// used in paint () to Draw the First part by the repaint() in end of this function 
                }
                else
                {
                    errorLabel.setVisible(true);
                    errorLabel.setBackground(Color.white);
                    errorLabel.setFont(new Font("Arial",Font.PLAIN,18));
                    errorLabel.setText("Worng file"); 
                    AddressText.setText("");
                    drawAll=0;
                    drawOne=0;
                    drawOp=0;
                    drawinput=0;
                    repaint();
                }
            }
        }
        try
        {
            if(e.getSource()==DrawButton)//the Button Drawing is pressed
            {
                drawLineshelper=1;
                errorLabel.setText("");
                errorLabel.setBackground(Color.lightGray);
               
                if("all".equals(OptionsText.getText()) || "".equals(OptionsText.getText()))//input of the Options is either null or we have the string "all"
                {
                    drawAll=1; // used in paint () to Draw the second part by the repaint() in end of this function 
                    drawOne=0;drawOp=0;
                }
                else if(OptionsText.getText().length()==1 || OptionsText.getText().equals("10"))
                {
                    oneOption=Integer.parseInt(OptionsText.getText());
                    String path=inputPath.getText();
                    ReadFiles r=new ReadFiles(path);
                    int nc=r.GetNumberOfCities(path);
                   
                    if(oneOption<=nc)
                    {
                        drawOne=1;
                        drawAll=0;drawOp=0;}
                    else
                    {
                        errorLabel.setVisible(true);
                        errorLabel.setFont(new Font("Arial",Font.PLAIN,18));
                        errorLabel.setBackground(Color.white);
                        errorLabel.setText("Worng options try agine");
                        drawOne=0;
                        drawAll=0;
                        drawOp=0;
                        drawinput=1;
                        repaint(); 
                    }
                }
                else
                {
                    String o=OptionsText.getText();
                    options=o.split(",");
                    String path=inputPath.getText();
                    ReadFiles r=new ReadFiles(path);
                    int nc=r.GetNumberOfCities(path);
                    int drawf=0;
                    for(String op:options)
                    {
                        int a=Integer.parseInt(op);
                       
                        if(a>nc)
                        {
                            errorLabel.setVisible(true);
                            errorLabel.setFont(new Font("Arial",Font.PLAIN,18));
                            errorLabel.setBackground(Color.white);
                            errorLabel.setText("Worng options try agine");
                            drawOne=0;
                            drawAll=0;
                            drawOp=0;
                            drawinput=1;
                            repaint(); 
                            break;
                        }
                        else
                            drawf++;
                    }
                    
                if(drawf==options.length)
                {
                    drawOp=1;
                    drawAll=0;
                    drawOne=0;
                }
                else
                {
                    errorLabel.setVisible(true);
                    errorLabel.setFont(new Font("Arial",Font.PLAIN,18));
                    errorLabel.setBackground(Color.white);
                    errorLabel.setText("Worng options try agine");
                    drawOne=0;
                    drawAll=0;
                    drawOp=0;
                    drawinput=1;
                    repaint();
                }
                }
            }
            if(e.getSource()==drawLines)
                drawLinesFlag=1;
            
            repaint();  
        }
        catch(Exception v)
        {
            errorLabel.setVisible(true);
            errorLabel.setFont(new Font("Arial",Font.PLAIN,18));
            errorLabel.setBackground(Color.white);
            errorLabel.setText("Worng options try agine");
            drawAll=0;
            drawOne=0;
            drawOp=0;
            drawinput=1;
            repaint();
        }
    }
       

    @Override
    public void mouseDragged(MouseEvent e) {
     }

    @Override
    public void mouseMoved(MouseEvent e) {
        try{ 
            g = getGraphics();
            mouseLabel.setText("");
            if(allmouseflag)
            {
                String path =inputPath.getText();
                ReadFiles r=new ReadFiles(path);
                String[] cities=new String[r.GetNumberOfCities(path)];
                cities=r.GetCities(path);
                int n=r.GetNumberOfCities(path);
                int[] avgs=r.GetAvgs(path);
                int factor=r.getFactorOfRates(path);
                int nums=360;
                for(int i=0;i<n;i++)
                {
                    //g.fillRect(nums-30, 900-(avgs[i-1]/2), 60, avgs[i-1]/2); nums+=120;
                    if((e.getX()>nums-30)&&(e.getX()<nums+30)&&
                       (e.getY()<900)&&(e.getY()> 900-(avgs[i]/factor)))
                    {
                        mouseLabel.setText(cities[i] + "  " +avgs[i] );
                        mouseLabel.setVisible(true);
                    }            
                nums+=120;
                }
            }
        
            if(opmouseflag)
            {
                String path =inputPath.getText();
                ReadFiles r=new ReadFiles(path);
                String[] cities=new String[r.GetNumberOfCities(path)];
                cities=r.GetCities(path);
                int[] avgs=r.GetAvgs(path);
                int factor = r.getFactorOfRates(path);
                int nums=360;
              
                int cityindex;
                for(String op:options)
                {
                    cityindex=Integer.parseInt(op);

                    if((e.getX()>nums-30)&&(e.getX()<nums+30)&&
                        (e.getY()<900)&&(e.getY()> 900-(avgs[cityindex-1]/factor)))
                    {
                        mouseLabel.setText(cities[cityindex-1] + "  " +avgs[cityindex-1] );
                        mouseLabel.setVisible(true);
                    }
                    nums+=120;
              
                    //g.drawLine(360, 890, 420, 890); the start line of the first city we want to draw
                    //  the way we reach (*) is well explained in the report.
                }
            }
        
            if(onemouseflag)
            {
                String path =inputPath.getText();
                ReadFiles r=new ReadFiles(path);
                String[] cities=new String[r.GetNumberOfCities(path)];
                cities= r.GetCities(path);
                int [] years= r.getYearsOfcity(cities[oneOption-1], path);
                int [] Rates=r.getRatesOfcity(cities[oneOption-1], path);
                int n=r.GetNumberOfYears(path);
                int factor=r.getFactorOfRates(path);
                if(years.length>10)
                {
                    int len=years.length;
                    int v=(1200/len)-5;
                    //1200 in the maximun width
                    int nums=330+(v/2);
                    for(int i=0;i<n;i++) 
                    {
                        if((e.getX()>nums-(v/2))&&(e.getX()<nums)&&
                            (e.getY()<900)&&(e.getY()> 900-(Rates[i]/factor)))
                        {
                            mouseLabel.setText(years[i] + "  " +Rates[i] );
                            mouseLabel.setVisible(true);
                        }
                    nums+=v;
            
                   //g.drawLine(360, 890, 420, 890); the start line of the first city we want to draw
                   //  the way we reach (*) is well explained in the report.
                    }
                }

                else
                {
                    int nums=360;
                    for(int i=0;i<n;i++) 
                    {
                        if((e.getX()>nums-30)&&(e.getX()<nums+30)&&
                            (e.getY()<900)&&(e.getY()> 900-(Rates[i]/factor)))
                        {
                            mouseLabel.setText(years[i] + "  " +Rates[i] );
                            mouseLabel.setVisible(true); 
                        }

                    nums+=120;
                  
                   //g.drawLine(360, 890, 420, 890); the start line of the first city we want to draw
                   //  the way we reach (*) is well explained in the report.
                    }
                }
            }
            }
            catch(Exception v)
           {}
        
       
    }
}

