/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis;
import com.sun.javafx.font.Disposer;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
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
public class GraphForFilterBy
{
    static String sdate="",date1="",date2="",sql="",id="",gsql="";
    public static void setOp(String choice,String dl, String fd, String td,String id) {
        sdate=dl;date1=fd;date2=td;GraphForFilterBy.id=id;gsql=choice;
        System.out.println(gsql+" show graph");
        System.out.println("ID : "+id);
        System.out.println("SDATE : "+sdate);
    }
    static public void prepareGraph()
    {
        PrepareGraph c=new PrepareGraph("Product purchase statistics","");
        RefineryUtilities.centerFrameOnScreen(c);
        c.setVisible(true);
        c.pack();                    
                    
    }
}
class PrepareGraph extends JFrame{
    Connection conn;Statement st;ResultSet rs;
    
    String type[] ={"Petrol","Clothes","Vegetables","Fast Food","Daily Needed","Diesel","Electricity Bills"
            ,"Shopping","Household Items","Home Maintainence Expenses","Electrical Appliances","Mobile Accessories"
            ,"Electrical Accessories","Accessories Maintainence Expenses","School Expenses",
            "Books","College Expenses","Trip Expenses","Others"};
    public PrepareGraph(String apptitle,String ctitle) {
        super(apptitle);
        setLocationRelativeTo(null);
        JFreeChart bChart=ChartFactory.createBarChart(ctitle, "Type of product", "Cost", createDataset(),PlotOrientation.VERTICAL,true,true,false);
        ChartPanel cpanel=new ChartPanel(bChart);
        setContentPane(cpanel);
    }

    private CategoryDataset createDataset() {
        final DefaultCategoryDataset dset;
        String sql="";
        dset=new DefaultCategoryDataset();
        try
        {
            Class.forName("org.sqlite.JDBC");
            conn=DriverManager.getConnection("jdbc:sqlite:data.db");
            st=conn.createStatement();
            System.out.println("statement done");
            for(String s:type)
            {
                if(GraphForFilterBy.gsql.equals("between dates"))
                {
                    System.out.println("1111");
                    sql="SELECT sum(total) as t from "+GraphForFilterBy.id+" where type like '"+s+"' and date(dop) between date('"+GraphForFilterBy.date1+"') and date('"+GraphForFilterBy.date2+"') order by date(dop);";                    
                }
                else if(GraphForFilterBy.gsql.equals("before date"))
                {
                     sql="SELECT sum(total) as t from "+GraphForFilterBy.id+" where type like '"+s+"' and date(dop) < date('"+GraphForFilterBy.sdate+"') order by date(dop);";                   
                     System.out.println("2222");
                }
                else
                {
                    System.out.println("3333");
                    sql="SELECT sum(total) as t from "+GraphForFilterBy.id+" where type like '"+s+"' and date(dop) > date('"+GraphForFilterBy.sdate+"') order by date(dop);";                    
                }
                rs=st.executeQuery(sql);
                System.out.println("Query successful");
                String c=null;
                if(rs.next())
                {
                    c=rs.getString("t");
                    try
                    {
                        if(c==null)
                        {
                            System.out.println(s+" : 0");
                            dset.addValue(0, s, "");
                        }
                        else
                        {
                            System.out.println(s+" : "+c);
                            dset.addValue(Double.parseDouble(c), s, "");
                        }
                    }
                    catch(Exception e)
                    {
                        System.out.println("0");
                    }
                }
            }
        }
        catch(ClassNotFoundException e)
        {
            System.out.println(e.getMessage()+"           ex1");
        }
        catch(SQLException e)
        {
            System.out.println(e+"           ex2");
        }
        catch(Exception e)
        {
            System.out.println(e+"           ex3");
        }
        return dset;
    }

    
    
}
