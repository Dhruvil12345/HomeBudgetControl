package Project;

import java.awt.Color;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.Connection;
import javax.swing.JButton;

public class Profile_View {
    private JFrame profile;
    private JTextField name,age,number,email,country,password;
    private JButton edit,save;
    private JButton back;
    Toolkit tk;
    
    public  Profile_View(String id,JFrame pframe) throws SQLException
    {
            Connection con=DriverManager.getConnection("jdbc:sqlite:signup.db");
            System.out.println("Successfully connected");
            Statement st=con.createStatement(); 
            ResultSet res;
        
        try
        {
            tk = Toolkit.getDefaultToolkit();
            int x = (int)tk.getScreenSize().getWidth();
            int y = (int)tk.getScreenSize().getHeight();
            int mx = x/2;
            int wx = mx-200;
            profile = new JFrame("Profile");
            profile.setLayout(null);
            Class.forName("org.sqlite.JDBC");
            res=st.executeQuery("SELECT * FROM SIGNUP WHERE ID='"+id+"'");
            back=new JButton("Back");
            back.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent ae) {
                    profile.setVisible(false);
                    pframe.setVisible(true);
                    try
                    {
                        res.close();
                        st.close();                       
                        con.close();
                    }
                    catch(SQLException e)
                    {
                        JOptionPane.showMessageDialog(profile, e.getMessage());
                    }
                }         
            });
            back.setBounds(10, 10, 80 , 35);
            profile.add(back);
            name = new JTextField(30);
            name.setBounds(wx, 200, 400, 30);
            name.setText(res.getString("NAME"));
            name.setEditable(false);
            profile.add(name);
            email = new JTextField(30);
            email.setBounds(wx,240,400,30);
            email.setText(res.getString("EMAIL"));
            email.setEditable(false);
            number = new JTextField(30);
            number.setBounds(wx,280,400,30);
            number.setText(res.getString("CNO"));
            number.setEditable(false);
            profile.add(number);
            int ww = mx-200;
            age = new JTextField(30);
            age.setBounds(ww,320,190,30);
            age.setText(res.getString("AGE"));
            age.setEditable(false);
            profile.add(age);
            int we = mx+10;
            country = new JTextField(30);
            country.setBounds(we,320,190,30);
            country.setText(res.getString("COUNTRY"));
            country.setEditable(false);
            profile.add(country);
            password = new JTextField(30);
            password.setBounds(wx,360,400,30);
            password.setText(res.getString("PASS"));
            password.setEditable(false);
            profile.add(password);
            int rr = mx+100;
            edit = new JButton("EDIT");
            edit.setBounds(ww,400,100,35);
            profile.add(edit);
            edit.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent ae) {
                    name.setEditable(true);
                    age.setEditable(true);
                    number.setEditable(true);
                    country.setEditable(true);
                    password.setEditable(true);
                }
            });
            save = new JButton("SAVE");
            save.setBounds(rr,400,100,35);
            save.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent ae)
                {
                    try {
                        String sql = "UPDATE signup SET NAME = '"+name.getText()+"',"
                                +"AGE="+Integer.parseInt(age.getText())+","+"EMAIL='"+email.getText()+"',"
                                +"CNO="+Long.parseLong(number.getText())+","+"COUNTRY='"+country.getText()+"',"
                                +"PASS='"+password.getText()+"'"+"WHERE ID="+Long.parseLong(id);
                        st.executeUpdate(sql);
                        JOptionPane.showMessageDialog(profile, "UPDATE SUCCESSFULLY");
                    } catch (SQLException ex) {
                       // JOptionPane.showMessageDialog(profile, "SOMETHING WENT GOING WRONG");
                       System.out.println(ex.getMessage());
                    }
                }
            });
            profile.add(save);
            profile.add(email);
            profile.getContentPane().setBackground(Color.cyan);
            profile.setExtendedState(Frame.MAXIMIZED_BOTH);
            profile.setDefaultCloseOperation(EXIT_ON_CLOSE);
           
            profile.setVisible(true);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(profile, e.getMessage());
            
        }
        finally
        {
          //st.close();
          //con.close();
        }
    }
    public JFrame getFrame()
    {
        return profile;
    }
   
}
