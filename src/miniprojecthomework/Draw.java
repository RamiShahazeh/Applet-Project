package miniprojecthomework;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;


public class Draw {
    
    public void drawLines (Graphics g,int factor)
    {
        int k=880*(factor);
        //to draw the black lines
        for(int i=100;i<880;i+=100)
            {
                g.setColor(Color.black);
                g.drawLine(330, i, 1500, i);
            }
        //to draw the dashed lines
        Graphics2D g2d = (Graphics2D) g;
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{3}, 0);
        g2d.setStroke(dashed);
               
        for(int j=20;j<=900;j+=20)
        {
            g.drawLine(330,j, 1500, j);
            g.setFont(new Font("Arial",Font.PLAIN,15));
            g.drawString(String.valueOf(k), 1505, j);
            k-=20*(factor);
        }
    }
}
