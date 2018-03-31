/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author Arun
 */
public class GraphForMainFrame {
    static String id;
    public GraphForMainFrame(String id)
    {
        GraphForMainFrame.id=id;
        BarChartDB c=new BarChartDB("Product purchase statistics","");
        c.pack();
        RefineryUtilities.centerFrameOnScreen(c);
        c.setVisible(true);
        c.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
class BarChartDB extends JFrame{
    Connection conn;Statement st;ResultSet rs;

    public BarChartDB(String apptitle,String ctitle) {
        super(apptitle);
        JFreeChart bchart=ChartFactory.createBarChart(ctitle, "Type of product", "Cost", createDataset(),PlotOrientation.VERTICAL,true,true,false);
        ChartPanel cpanel=new ChartPanel(bchart);
        setContentPane(cpanel);
    }
    private CategoryDataset createDataset()
    {
        final DefaultCategoryDataset dset;
        dset=new DefaultCategoryDataset();
        try
        {
            Class.forName("org.sqlite.JDBC");
            conn=DriverManager.getConnection("jdbc:sqlite:data.db");
            st=conn.createStatement();
            print("Connection Successful"); 
            String type[] ={"Petrol","Clothes","Vegetables","Fast Food","Daily Needed","Diesel","Electricity Bills"
            ,"Shopping","Household Items","Home Maintainence Expenses","Electrical Appliances","Mobile Accessories"
            ,"Electrical Accessories","Accessories Maintainence Expenses","School Expenses",
            "Books","College Expenses","Trip Expenses","Others"};
            for(String s:type)
            {
                //print(s);
                String sql="select sum(total) as t from "+GraphForMainFrame.id+" where type like '"+s+"';";
                rs=st.executeQuery(sql);
                String c=null;
                if(rs.next())
                {
                    c=rs.getString("t");
                    try
                    {
                        if(c==null)
                        {
                            //print(s+" : 0");
                            dset.addValue(0, s, "");
                        }
                        else
                        {
                            //print(s+" : "+c);
                            dset.addValue(Double.parseDouble(c), s, "");
                        }
                    }
                    catch(Exception e)
                    {
                        print("0");
                    }
                }
            }
        }
        catch(ClassNotFoundException e)
        {
            print(e.getMessage());
        }
        catch(SQLException e)
        {
            print(e.getMessage());
        }
        return dset;
    }

    private void print(String s) {
        System.out.println(s);
    }
    public static void main(String[] args)
    {
        BarChartDB c=new BarChartDB("Product purchase statistics","");
        c.pack();
        //c.se
        RefineryUtilities.centerFrameOnScreen(c);
        c.setVisible(true);
    }
}