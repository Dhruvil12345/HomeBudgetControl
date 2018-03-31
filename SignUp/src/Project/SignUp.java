package Project;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.sql.ResultSet;
class MyExp extends Exception
{
    MyExp(String s)
    {
        super(s);
    }
}
public class SignUp extends JFrame implements ActionListener {
    Long tid;
    Connection conn;
    Statement st;
    JTextField name;
    Toolkit tk;
    JTextField age;
    JTextField date;
    JTextField email;
    JTextField contact;
    JTextField country;
    JTextField uniqueId;
    JPasswordField pass;
    JPasswordField cpass;
    JButton signup;
    JFrame pg;
    String sname=null;
    String sage=null;
    String sdate=null;
    String semail=null;
    String scno=null;
    String scountry=null;
    String spass=null;
    String scpass=null;
    String driver=null;
    String url=null;
    long id=0;
    JButton back;
    public long randLong(long min, long max) {
    return (new java.util.Random().nextLong() % (max - min)) + min;
    }
    public void checkInt(String s,String w) throws NumberFormatException,MyExp
    {
        try
        {
           Long.parseLong(s);
           Long.parseLong(w);
        }
        catch(NumberFormatException e)
        {
            throw new NumberFormatException("Mobile number and Age must be number");
        }
        if(w.length()<9 || w.length()>10)
         {
            throw new MyExp("Please enter a valid mobile number(Mobile number should be between 9-10 digits)");
         }
    }
    SignUp(JFrame p)
    {
        tk = Toolkit.getDefaultToolkit();
        int x = (int)tk.getScreenSize().getWidth();
        int y = (int)tk.getScreenSize().getHeight();
        int mx = x/2;
        int wx = mx-155;
        JFrame self=this;
        back=new JButton("BACK");
        back.setBounds(10, 10, 80 , 35);
        self.add(back);
        back.addActionListener(new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent ae) {
              self.setVisible(false);
              p.setVisible(true);
          }         
      });
        pg=p;
        name=new JTextField(30);
        name.setText("Enter your full name");
        name.addMouseListener(new MouseAdapter(){
          public void mouseClicked(MouseEvent ae)
          {
              name.setText("");
          }
      });
        name.setToolTipText("Enter your full name here");
        age=new JTextField(30);
        age.setToolTipText("Enter your age");
        age.setText("Enter your Age");
        age.addMouseListener(new MouseAdapter(){
          public void mouseClicked(MouseEvent ae)
          {
              age.setText("");
          }
      });
        date=new JTextField(15);
        date.setToolTipText("Enter age in this format yyyy-mm-dd for example 1999-12-31");
        date.setText("Enter date of birth");
        date.addMouseListener(new MouseAdapter(){
          public void mouseClicked(MouseEvent ae)
          {
              date.setText("");
          }
      });
        email=new JTextField(30);
        email.setText("Enter your email id");
        email.addMouseListener(new MouseAdapter(){
          public void mouseClicked(MouseEvent ae)
          {
              email.setText("");
          }
      });
        email.setToolTipText("Email id here");
        pass=new JPasswordField(30);
        pass.setToolTipText("Enter your password here");
        pass.setText("Enter your password");
        pass.addMouseListener(new MouseAdapter(){
          public void mouseClicked(MouseEvent ae)
          {
              pass.setText("");
          }
      });
        cpass=new JPasswordField(30);
        cpass.setText("Re-Type your password");
        cpass.addMouseListener(new MouseAdapter(){
          public void mouseClicked(MouseEvent ae)
          {
              cpass.setText("");
          }
      });
        cpass.setToolTipText("Re-type your password");
        contact=new JTextField(13);
        contact.setText("Enter your contact number");
        contact.addMouseListener(new MouseAdapter(){
          public void mouseClicked(MouseEvent ae)
          {
              contact.setText("");
          }
      });
        contact.setToolTipText("Mobile number must be between 9-10 digits");
        country=new JTextField(15);
        country.setText("Enter your country name");
        country.addMouseListener(new MouseAdapter(){
          public void mouseClicked(MouseEvent ae)
          {
              country.setText("");
          }
      });
        country.setToolTipText("Enter your country name over here");
        signup=new JButton();
        signup.setText("SignUp");
        int w = mx+12;
        int vv = mx+19;
        name.setBounds(wx, 120, 310, 50);
        age.setBounds(wx,175,310,50);
        date.setBounds(wx,230,310,50);
        email.setBounds(wx,285,310,50);
        contact.setBounds(wx,340,152,50);
        country.setBounds(w,340,142,50);
        pass.setBounds(wx,395,310,50);
        cpass.setBounds(wx,450,310,50);      
        signup.setBounds(vv, 505, 135, 50);
        this.add(name);
        this.add(age);
        this.add(date);
        this.add(email);
        this.add(contact);
        this.add(country);
        this.add(pass);
        this.add(cpass);
        this.add(signup);
        signup.addActionListener(this);   
        this.setLayout(null);
        this.getContentPane().setBackground(Color.cyan);
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      
        this.setTitle("SIGNUP");
        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent ae)
    {
        try
        {
            sname=name.getText();
            sage=age.getText();
            sdate=date.getText();
            semail=email.getText();
            scno=contact.getText();
            scountry=country.getText();
            spass=pass.getText();
            scpass=cpass.getText();
            driver="org.sqlite.JDBC";
            url="jdbc:sqlite:signup.db";
            if(sname.isEmpty() || sage.isEmpty() || sdate.isEmpty() || semail.isEmpty() || scno.isEmpty() || scountry.isEmpty() || spass.isEmpty() || scpass.isEmpty())
            {
                JOptionPane.showMessageDialog(this,"All fields are required");
            }
            else
            {
                this.checkInt(sage,scno);
                if(spass.equals(scpass))
                {            
                    try
                    {
                        Class.forName(driver);
                        conn=DriverManager.getConnection(url);
                        System.out.println("Connection Successful");
                        st=conn.createStatement();
                        tid=this.randLong(1, 9999999999L);
                        
                        ResultSet rs=st.executeQuery("select * from signup where id="+tid);
                        while(rs.next())
                        {
                            tid=this.randLong(1, 9999999999L);
                            if(tid<0)
                            {
                                tid=tid*-1;
                            }
                            rs=st.executeQuery("select * from signup where id="+tid);
                        }
                        if(tid<0)
                            {
                                tid=tid*-1;
                            }
                        String sql="INSERT INTO signup(NAME,AGE,DOB,EMAIL,CNO,COUNTRY,PASS,ID) "+"VALUES('"+sname+"',"+Integer.parseInt(sage)+",'"+sdate+"','"+semail+"',"+Long.parseLong(scno)+",'"+scountry+"','"+spass+"',"+tid+")";
                        st.executeUpdate(sql);
                        JOptionPane.showMessageDialog(this, "SignUp successful");
                        this.setVisible(false);
                        pg.setVisible(true);
                    }
                    catch(SQLException sq)
                    {
                        if(sq.getMessage().equals("[SQLITE_CONSTRAINT_PRIMARYKEY]  A PRIMARY KEY constraint failed (UNIQUE constraint failed: signup.EMAIL)"))
                        {
                            JOptionPane.showMessageDialog(this,"Email has been used earlier try another one");
                        }
                        else if(sq.getMessage().equals("[SQLITE_ERROR] SQL error or missing database (no such table: signup)"))
                        {
                            tid=this.randLong(1, 9999999999L);
                            if(tid<0)
                            {
                                tid=tid*-1;
                            }
                            try
                            {
                               String sql="CREATE TABLE signup(NAME TEXT,AGE INT,DOB TEXT,EMAIL TEXT PRIMARY KEY,CNO INT,COUNTRY TEXT,PASS TEXT, ID INTEGER);";
                               st.executeUpdate(sql);                               
                               sql="INSERT INTO signup(NAME,AGE,DOB,EMAIL,CNO,COUNTRY,PASS,ID) "+"VALUES('"+sname+"',"+Integer.parseInt(sage)+",'"+sdate+"','"+semail+"',"+Long.parseLong(scno)+",'"+scountry+"','"+spass+"',"+tid+")";
                               st.executeUpdate(sql);
                               JOptionPane.showMessageDialog(this,"SignUp successful");
                               this.setVisible(false);
                                pg.setVisible(true);
                            }
                            catch(SQLException d)
                            {
                                JOptionPane.showMessageDialog(this,d.getMessage());
                            }                          
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(this,sq.getMessage()+"3");
                        }
                    }
                    catch(ClassNotFoundException e)
                    {
                        JOptionPane.showMessageDialog(this, e+"1");
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Password and Confirm Password must be same");
                }
            }
        }
        catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this, e.getMessage()+"2");
        }
        catch(MyExp h)
        {
            JOptionPane.showMessageDialog(this, h.getMessage()+"3");
        }
    } 
}