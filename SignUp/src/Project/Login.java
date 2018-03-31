
package Project;

import Project.Frame_4;
import Project.Profile_View;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
public class Login {
    private JFrame frame;
    private JPanel panel;
    private JTextField text1;
    private JPasswordField text2;
    private JButton login;
    private JButton back;
    public Login(JFrame pframe)
    {
      Toolkit tk;
      frame = new JFrame("LOGIN");
      back = new JButton("Back");
      back.setBounds(10, 10, 80 , 35);
      back.addActionListener(new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent ae) {
              frame.setVisible(false);
              pframe.setVisible(true);
          }         
      });
      tk = Toolkit.getDefaultToolkit();
      int x = (int)tk.getScreenSize().getWidth();
      int y = (int)tk.getScreenSize().getHeight();
      int mx = x/2;
      int wx = mx-250;
      int wx1 = mx-200;
      frame.setLayout(null);
      text1 = new JTextField(40);
      text1.setText("USERNAME");
      text1.addMouseListener(new MouseAdapter(){
          public void mouseClicked(MouseEvent ae)
          {
              text1.setText("");
          }
      });
      text1.setToolTipText("Type your username here");
      text2 = new JPasswordField(40);
      text2.setText("PASSWORD");
      text2.addMouseListener(new MouseAdapter(){
          public void mouseClicked(MouseEvent ae)
          {
              text2.setText("");
          }
      });
      text2.setToolTipText("Type your password here");
      login = new JButton("LOGIN");
      text1.setBounds(wx, 190, 500, 30);
      text2.setBounds(wx,250,500,30);
      frame.getContentPane().setBackground(Color.cyan);
      login.setBounds(wx1,330,400,40);
      login.addActionListener(new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent ae) {
              String value1 = text1.getText();
              String value2 = text2.getText();
              if(value1.isEmpty() || value2.isEmpty())   
              {
                    JOptionPane.showMessageDialog(frame,"All fields are required");       
              }
              else
              {
                        Connection con = null;
              String driver="org.sqlite.JDBC";
              String url="jdbc:sqlite:signup.db";
              String user1="";
              String pass1="";
              String id="";
              String age = "";
              String cno = "";
              String username = "";
              String country = "";
              try{
                  Class.forName(driver);
                  con=DriverManager.getConnection(url);
                  System.out.println("Successfully connected");
                  Statement st=con.createStatement();
                  ResultSet res=st.executeQuery("select * from signup where EMAIL='"+value1+"' and PASS='"+value2+"';");
                  while(res.next())
                {
                    user1=res.getString("EMAIL");
                    pass1=res.getString("PASS");
                    id=res.getString("ID");                  
                }
                if(value1.equals(user1) && value2.equals(pass1))
                {   
                    JOptionPane.showMessageDialog(frame,"Login Successful");
                    con.close();
                    frame.setVisible(false);
                    Frame_4 f=new Frame_4(id,pframe);
                    JFrame j=f.getFrame();
                    j.setVisible(true);                                                    
                }
                else
                {
                    JOptionPane.showMessageDialog(frame, "Incorrect login or pasword","Error",JOptionPane.ERROR_MESSAGE);
                }
                con.commit();
                con.close();
                st.close();
            }
              catch (ClassNotFoundException ex) {
                System.out.println(ex);   
                } 
                catch (SQLException ex) {
                  if(ex.getMessage().equals("[SQLITE_ERROR] SQL error or missing database (no such table: signup)"))
                  {
                      JOptionPane.showMessageDialog(frame, "Please create a user first!!!");
                  }
                }
            }
        }
          
      });
      frame.add(text1);
      frame.add(text2);
      frame.add(login);
      frame.add(back);
      frame.setVisible(true);
      frame.setExtendedState(Frame.MAXIMIZED_BOTH);
      frame.setDefaultCloseOperation(EXIT_ON_CLOSE);    
      
    }
    
}
