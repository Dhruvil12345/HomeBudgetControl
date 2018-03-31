package DataBaseManager;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AddItem {
    Connection con;
    Statement st;
    String sql;
    long id;
    JFrame frame;
    String iname,unit,dop,type;
    JTextField trys;
    double rate,quantity,total;
    public AddItem(JFrame j,long id1,String iname1,double rate1,String unit1,double quantity1,Date dop1,String type1)
    {
       id=id1;
       iname=iname1;
       rate=rate1;
       unit=unit1;
       quantity=quantity1;
       dop=new SimpleDateFormat("yyyy-MM-dd").format(dop1);
       total=rate*quantity;
       type=type1;
       try{
         Class.forName("org.sqlite.JDBC");
         con=DriverManager.getConnection("jdbc:sqlite:data.db");
         sql="INSERT INTO d"+id+"(iname,rate,unit,quantity,dop,total,type) VALUES('"+iname+"',"+rate+",'"+unit+"',"+quantity+",'"+dop+"',"+total+",'"+type+"');";
         st = con.createStatement();
         st.executeUpdate(sql);
         JOptionPane.showMessageDialog(j, "Successful");
         System.out.println("Stored");
        } catch (ClassNotFoundException ex1) {
            JOptionPane.showMessageDialog(j, ex1);
        } catch (SQLException ex) {
            /*JOptionPane.showMessageDialog(j, "Exception caught");
            System.out.println(ex.getMessage());*/
            if(ex.getMessage().equals("[SQLITE_ERROR] SQL error or missing database (no such table: d"+id+")"))
            {
                JOptionPane.showMessageDialog(null,"Table creation started");
                try{
                    st.executeUpdate("CREATE TABLE d"+id+"(iname TEXT,rate REAL,unit TEXT,quantity REAL,dop TEXT,total REAL,type TEXT);");
                    st.executeUpdate(sql);
                    JOptionPane.showMessageDialog(j, "Successful");
                    System.out.println("Stored");
                }
                catch(SQLException e1){
                    JOptionPane.showMessageDialog(j, e1.getMessage()+" failed");
                }
            }
        } 
      
    }
   
    
    
    
    
    
    
    /* public AddItem(String id1)
    {
      
      try
      {
            frame = new JFrame("testing");
            frame.setLayout(null);
            frame.setVisible(true);
            trys = new JTextField(30);
            trys.setBounds(100, 100, 100, 50);
            //id = Long.parseLong(id1);
            trys.setText(id1);
            trys.setEditable(false);
            frame.add(trys);
      }
      catch(NumberFormatException e)
      {
          JOptionPane.showMessageDialog(null, e.getMessage());
      }
      
    }*/
   
    
   
}
